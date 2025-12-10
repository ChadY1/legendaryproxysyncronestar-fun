# StarfunCore Velocity

Plugin Velocity fournissant le noyau StarfunCore (messagerie sécurisée entre le proxy Velocity et les hubs Spigot/Folia) via le canal plugin messaging `starfun:core`.

Ce dépôt inclut désormais un dossier `proxy/` prêt à être automatisé pour un réseau ProxyForge/Velocity en frontal d'un serveur Mohist 1.7.10 (RolePlay + Moddé + Plugins). Les templates livrés permettent de sécuriser le proxy, préparer les mods et offrir un script d'« autofix » pour générer l'arborescence par défaut.

## Configuration

1. Génère une clé AES partagée (128/192/256 bits) en Base64, par exemple :
   ```bash
   openssl rand -base64 32
   ```
2. Renseigne cette clé dans `plugins/starfuncore/starfuncore.properties` (créé automatiquement au premier démarrage) avec la propriété `encryptionKey`.
   ```properties
   encryptionKey=VOTRE_CLE_BASE64_ICI
   ```
3. Déploie le JAR sur ton proxy Velocity. Le canal est enregistré automatiquement et les messages sont chiffrés avec la clé fournie.

## Build

Le projet utilise Gradle + Shadow pour produire un JAR prêt à l'emploi. Le wrapper pointe sur Gradle 8.14.3 et télécharge automatiquement `gradle-wrapper.jar` depuis la distribution officielle si le binaire n'est pas présent (binaire non versionné pour garder le dépôt 100% sans fichiers lourds). Le fichier `settings.gradle` applique le plugin `org.gradle.toolchains.foojay-resolver-convention` qui télécharge automatiquement un JDK 17 (Adoptium) si aucun JDK 17 n'est détecté (utile sur Windows CI/bureaux). Assure-toi simplement d'autoriser Gradle à accéder à Internet ou fournis un JDK 17 local via `JAVA_HOME`/`org.gradle.java.home`.

```bash
./gradlew build
```

`gradlew` (Unix) et `gradlew.bat` (Windows) gèrent le téléchargement/extraction automatique du wrapper JAR et de la distribution Gradle. Pas besoin d'ajouter le binaire au dépôt : un accès HTTP/HTTPS suffit. Le JAR final est généré dans `build/libs/StarfunCoreVelocity-1.0.0.jar`.

## Plan Proxy/Mohist (1.7.10 RolePlay + Moddé)

Le dossier `proxy/` fournit des fichiers prêts à automatiser pour un réseau ProxyForge/Velocity + Mohist 1.7.10 :

- `proxy/autofix.sh` : crée l'arborescence (mods, plugins, config), copie les templates et peut télécharger les mods listés dans `proxy/mods.list` via la variable d'environnement `MOD_MIRROR_BASE`.
- `proxy/templates/mohist-server.properties` : base sécurisée pour Mohist (motd, whitelist, etc.).
- `proxy/config/security.md` et `proxy/config/checksums.sha256` : à compléter dans votre CI avec les secrets et empreintes.
- `proxy/mods.list` : liste des mods recommandés (ArmaMania, Decocraft, Dynamic Surroundings, Flan's Mod + packs, ArchitectureCraft, Carpenter's Blocks, TubeTransports...).
- Les téléchargements (archives et JAR) sont mis en cache dans `proxy/downloads/` puis recopiés dans `mods/`, `plugins/`, `maps/`. Ces dossiers sont ignorés par Git pour éviter l'erreur « Les fichiers binaires ne sont pas pris en charge » et garder le dépôt léger.

### Usage rapide

```bash
./proxy/autofix.sh
# Puis pousser les mods/plugins/maps depuis votre miroir privé et compléter les checksums
```

- Un workflow GitHub Actions (`.github/workflows/proxy-autostart.yml`) est fourni pour simuler le bootstrap CI : il lance `autofix.sh` et télécharge automatiquement les mods/plugins/maps si les variables `MOD_MIRROR_BASE`, `PLUGIN_MIRROR_BASE` et `MAP_MIRROR_BASE` sont fournies en secrets.
- Assure-toi que la clé AES `starfun:core` et le `forwarding-secret` ProxyForge/Velocity sont renseignés avant de déployer.
