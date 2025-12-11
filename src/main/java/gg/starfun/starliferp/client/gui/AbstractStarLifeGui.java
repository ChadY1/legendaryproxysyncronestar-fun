package gg.starfun.starliferp.client.gui;

import gg.starfun.starliferp.StarLifeRPMod;
import gg.starfun.starliferp.network.GuiActionMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class AbstractStarLifeGui extends GuiScreen {
    protected net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
    protected void sendAction(String action, int value) {
        StarLifeRPMod.NETWORK.sendToServer(new GuiActionMessage(action, value));
    }

    protected void bindTexture(ResourceLocation texture) {
        mc.getTextureManager().bindTexture(texture);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        // subclasses handle
    }

    protected void drawTiledBackground(ResourceLocation texture, int x, int y, int width, int height) {
        bindTexture(texture);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        int tile = 32;
        for (int xx = 0; xx < width; xx += tile) {
            for (int yy = 0; yy < height; yy += tile) {
                drawTexturedModalRect(x + xx, y + yy, 0, 0, Math.min(tile, width - xx), Math.min(tile, height - yy));
            }
        }
    }
}
