package gg.starfun.starliferp.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiPolice extends AbstractStarLifeGui {
    private static final ResourceLocation BG = new ResourceLocation("starliferp", "textures/gui/police.png");

    @Override
    public void initGui() {
        buttonList.clear();
        int centerX = width / 2;
        int startY = height / 2 - 40;
        buttonList.add(new GuiButton(0, centerX - 75, startY, 150, 20, I18n.format("gui.starlife.police.fine")));
        buttonList.add(new GuiButton(1, centerX - 75, startY + 24, 150, 20, I18n.format("gui.starlife.police.bol")));
        buttonList.add(new GuiButton(2, centerX - 75, startY + 48, 150, 20, I18n.format("gui.starlife.police.backup")));
        buttonList.add(new GuiButton(3, centerX - 75, startY + 72, 150, 20, I18n.format("gui.starlife.police.search")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawTiledBackground(BG, 0, 0, width, height);
        drawCenteredString(fontRendererObj, I18n.format("gui.starlife.police.title"), width / 2, 20, 0x66CCFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        sendAction("police_action", button.id);
    }
}
