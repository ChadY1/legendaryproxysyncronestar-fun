package gg.starfun.proxy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import gg.starfun.proxy.security.CryptoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

@Plugin(
        id = "starfuncore",
        name = "StarfunCore",
        version = "1.0.0",
        description = "Noyau proxy Starfun (Velocity)",
        authors = {"Starfun"}
)
public class StarfunCoreVelocity {

    public static final MinecraftChannelIdentifier CHANNEL =
            MinecraftChannelIdentifier.create("starfun", "core");

    private final ProxyServer proxy;
    private final Logger logger;
    private final Gson gson = new GsonBuilder().create();
    private final Path dataDirectory;

    private CryptoUtil cryptoUtil;

    @Inject
    public StarfunCoreVelocity(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        proxy.getChannelRegistrar().register(CHANNEL);
        logger.info("[StarfunCore] Channel plugin messaging enregistre: {}", CHANNEL.getId());

        String base64Key = loadBase64Key();
        if (base64Key == null || base64Key.isBlank()) {
            logger.warn("[StarfunCore] Aucune cle AES definie (config/starfuncore.properties -> encryptionKey). Mode degrade non chiffre.");
            cryptoUtil = null;
            return;
        }

        try {
            cryptoUtil = new CryptoUtil(base64Key);
            logger.info("[StarfunCore] CryptoUtil initialise (AES-GCM).");
        } catch (Exception e) {
            logger.error("[StarfunCore] Echec initialisation CryptoUtil, chiffrement desactive.", e);
            cryptoUtil = null;
        }
    }

    private String loadBase64Key() {
        Properties properties = new Properties();
        Path configPath = dataDirectory.resolve("starfuncore.properties");

        try {
            if (Files.notExists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            if (Files.notExists(configPath)) {
                try (InputStream defaultConfig = getClass().getClassLoader().getResourceAsStream("starfuncore.properties")) {
                    if (defaultConfig != null) {
                        Files.copy(defaultConfig, configPath, StandardCopyOption.REPLACE_EXISTING);
                        logger.info("[StarfunCore] Configuration par defaut generee a {}", configPath.toAbsolutePath());
                    }
                }
            }

            if (Files.exists(configPath)) {
                try (InputStream in = Files.newInputStream(configPath)) {
                    properties.load(in);
                }
            }
        } catch (IOException e) {
            logger.warn("[StarfunCore] Impossible de lire la configuration: {}", e.getMessage());
        }

        return properties.getProperty("encryptionKey", "");
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!CHANNEL.equals(event.getIdentifier())) {
            return;
        }

        event.setResult(PluginMessageEvent.ForwardResult.handled());

        if (!(event.getSource() instanceof ServerConnection backend)) {
            return;
        }

        byte[] data = event.getData();
        ByteArrayDataInput in = ByteStreams.newDataInput(data);

        try {
            String sub = in.readUTF();
            if (!"SECURE".equals(sub)) {
                logger.debug("[StarfunCore] Sous-channel inconnu: {}", sub);
                return;
            }

            String cipherBase64 = in.readUTF();
            String json = cryptoUtil != null ? cryptoUtil.decrypt(cipherBase64) : cipherBase64;

            JsonObject obj = gson.fromJson(json, JsonObject.class);
            if (obj == null || !obj.has("type")) {
                return;
            }

            String type = obj.get("type").getAsString();
            JsonObject payload = obj.has("payload") && obj.get("payload").isJsonObject()
                    ? obj.getAsJsonObject("payload")
                    : null;

            handleSecureMessageFromBackend(backend, type, payload, obj);

        } catch (Exception e) {
            logger.error("[StarfunCore] Erreur lecture message plugin securise: {}", e.getMessage(), e);
        }
    }

    private void handleSecureMessageFromBackend(ServerConnection backend,
                                                String type,
                                                JsonObject payload,
                                                JsonObject fullEnvelope) {

        switch (type) {
            case "GLOBAL_PING" -> handleGlobalPing(backend, payload, fullEnvelope);
            case "BAN_BROADCAST" -> handleBanBroadcast(payload);
            case "PROFILE_REQUEST" -> handleProfileRequest(backend, payload);
            case "GLOBAL_PONG" -> logger.debug("[StarfunCore] GLOBAL_PONG recu de {}", backend.getServerInfo().getName());
            default -> logger.debug("[StarfunCore] Type inconnu: {}", type);
        }
    }

    private void handleGlobalPing(ServerConnection backend,
                                  JsonObject payload,
                                  JsonObject envelope) {
        String serverName = backend.getServerInfo().getName();
        logger.debug("[StarfunCore] GLOBAL_PING de {}", serverName);

        JsonObject pongPayload = new JsonObject();
        pongPayload.addProperty("status", "OK");
        pongPayload.addProperty("proxyTime", System.currentTimeMillis());
        pongPayload.addProperty("proxyServer", "velocity-main");

        JsonObject pongEnvelope = new JsonObject();
        pongEnvelope.addProperty("type", "GLOBAL_PONG");
        pongEnvelope.addProperty("version", envelope.has("version") ? envelope.get("version").getAsInt() : 1);
        pongEnvelope.addProperty("origin", "proxy");
        pongEnvelope.addProperty("timestamp", System.currentTimeMillis());
        pongEnvelope.add("payload", pongPayload);

        sendSecureToBackend(backend, pongEnvelope);
    }

    private void handleBanBroadcast(JsonObject payload) {
        if (payload == null) {
            return;
        }

        String uuidStr = payload.has("uuid") ? payload.get("uuid").getAsString() : null;
        String reason = payload.has("reason") ? payload.get("reason").getAsString() : "Banni par le reseau Starfun";
        long until = payload.has("until") ? payload.get("until").getAsLong() : 0L;

        logger.info("[StarfunCore] BAN_BROADCAST uuid={} reason={} until={}", uuidStr, reason, until);

        if (uuidStr != null) {
            UUID targetUuid = UUID.fromString(uuidStr);
            for (Player player : proxy.getAllPlayers()) {
                if (player.getUniqueId().equals(targetUuid)) {
                    player.disconnect(Component.text(reason));
                }
            }
        }

        JsonObject envelope = new JsonObject();
        envelope.addProperty("type", "BAN_BROADCAST");
        envelope.addProperty("version", 1);
        envelope.addProperty("origin", "proxy");
        envelope.addProperty("timestamp", System.currentTimeMillis());
        envelope.add("payload", payload.deepCopy());

        for (RegisteredServer server : proxy.getAllServers()) {
            sendSecureToServer(server, envelope);
        }
    }

    private void handleProfileRequest(ServerConnection backend, JsonObject payload) {
        if (payload == null || !payload.has("uuid")) {
            return;
        }

        String uuidStr = payload.get("uuid").getAsString();
        logger.debug("[StarfunCore] PROFILE_REQUEST uuid={}", uuidStr);

        JsonObject profilePayload = new JsonObject();
        profilePayload.addProperty("uuid", uuidStr);
        profilePayload.addProperty("found", false);

        JsonObject envelope = new JsonObject();
        envelope.addProperty("type", "PROFILE_RESPONSE");
        envelope.addProperty("version", 1);
        envelope.addProperty("origin", "proxy");
        envelope.addProperty("timestamp", System.currentTimeMillis());
        envelope.add("payload", profilePayload);

        sendSecureToBackend(backend, envelope);
    }

    public void sendSecureToServer(RegisteredServer server, JsonObject envelope) {
        try {
            String json = gson.toJson(envelope);
            String payload = cryptoUtil != null ? cryptoUtil.encrypt(json) : json;

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("SECURE");
            out.writeUTF(payload);

            boolean ok = server.sendPluginMessage(CHANNEL, out.toByteArray());
            if (!ok) {
                logger.warn("[StarfunCore] Echec envoi plugin message a {}", server.getServerInfo().getName());
            }
        } catch (Exception e) {
            logger.error("[StarfunCore] Erreur envoi message securise au serveur {}: {}",
                    server.getServerInfo().getName(), e.getMessage(), e);
        }
    }

    public void sendSecureToBackend(ServerConnection backend, JsonObject envelope) {
        try {
            String json = gson.toJson(envelope);
            String payload = cryptoUtil != null ? cryptoUtil.encrypt(json) : json;

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("SECURE");
            out.writeUTF(payload);

            boolean ok = backend.sendPluginMessage(CHANNEL, out.toByteArray());
            if (!ok) {
                logger.warn("[StarfunCore] Echec envoi plugin message via connexion backend {}",
                        backend.getServerInfo().getName());
            }
        } catch (Exception e) {
            logger.error("[StarfunCore] Erreur envoi message securise (backend): {}", e.getMessage(), e);
        }
    }
}
