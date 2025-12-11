package gg.starfun.starliferp.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gg.starfun.starliferp.client.gui.ClientKeyHandler;
import gg.starfun.starliferp.common.CommonProxy;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void registerClient() {
        FMLCommonHandler.instance().bus().register(new ClientKeyHandler());
        ClientRegistry.registerKeyBinding(ClientKeyHandler.OPEN_MENU);
    }
}
