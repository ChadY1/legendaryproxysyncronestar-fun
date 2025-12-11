package gg.starfun.starliferp.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiMilitary extends AbstractStarLifeGui {
    private static final ResourceLocation BG = new ResourceLocation("starliferp", "textures/gui/military.png");

    @Override
    public void initGui() {
        buttonList.clear();
        int centerX = width / 2;
        int startY = height / 2 - 40;
        buttonList.add(new GuiButton(0, centerX - 75, startY, 150, 20, I18n.format("gui.starlife.military.briefing")));
        buttonList.add(new GuiButton(1, centerX - 75, startY + 24, 150, 20, I18n.format("gui.starlife.military.support")));
        buttonList.add(new GuiButton(2, centerX - 75, startY + 48, 150, 20, I18n.format("gui.starlife.military.unlocks")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawTiledBackground(BG, 0, 0, width, height);
        drawCenteredString(fontRendererObj, I18n.format("gui.starlife.military.title"), width / 2, 20, 0xCCCC00);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        sendAction("military_action", button.id);
    }
}
