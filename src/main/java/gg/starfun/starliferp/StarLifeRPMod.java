package gg.starfun.starliferp;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import gg.starfun.starliferp.network.GuiActionMessage;
import gg.starfun.starliferp.network.GuiActionMessageHandler;
import gg.starfun.starliferp.server.CommandStarLife;
import gg.starfun.starliferp.server.GuiHandler;

@Mod(modid = StarLifeRPMod.MOD_ID, name = "StarLife RP GUI Pack", version = StarLifeRPMod.VERSION)
public class StarLifeRPMod {
    public static final String MOD_ID = "starliferp";
    public static final String VERSION = "1.0.0";
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    @SidedProxy(clientSide = "gg.starfun.starliferp.client.ClientProxy", serverSide = "gg.starfun.starliferp.server.ServerProxy")
    public static gg.starfun.starliferp.common.CommonProxy proxy;

    @Mod.Instance
    public static StarLifeRPMod INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NETWORK.registerMessage(new GuiActionMessageHandler(), GuiActionMessage.class, 0, cpw.mods.fml.relauncher.Side.SERVER);
        proxy.registerClient();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandStarLife());
    }
}
