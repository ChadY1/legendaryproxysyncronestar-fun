package gg.starfun.starliferp.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gg.starfun.starliferp.StarLifeRPMod;
import gg.starfun.starliferp.server.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ClientKeyHandler {
    public static final KeyBinding OPEN_MENU = new KeyBinding("Open StarLifeRP", Keyboard.KEY_M, "key.categories.gameplay");

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (OPEN_MENU.isPressed()) {
            Minecraft.getMinecraft().thePlayer.openGui(StarLifeRPMod.INSTANCE, GuiHandler.GUI_MAIN_MENU, Minecraft.getMinecraft().theWorld, 0, 0, 0);
        }
    }
}
