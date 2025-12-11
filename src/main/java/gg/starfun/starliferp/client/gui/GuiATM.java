package gg.starfun.starliferp.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiATM extends AbstractStarLifeGui {
    private static final ResourceLocation BG = new ResourceLocation("starliferp", "textures/gui/atm.png");
    private int previewBalance = 1250;

    @Override
    public void initGui() {
        buttonList.clear();
        int centerX = width / 2;
        int startY = height / 2 - 40;
        buttonList.add(new GuiButton(0, centerX - 75, startY, 70, 20, I18n.format("gui.starlife.deposit")));
        buttonList.add(new GuiButton(1, centerX + 5, startY, 70, 20, I18n.format("gui.starlife.withdraw")));
        buttonList.add(new GuiButton(2, centerX - 75, startY + 24, 150, 20, I18n.format("gui.starlife.transfer")));
        buttonList.add(new GuiButton(3, centerX - 75, startY + 48, 150, 20, I18n.format("gui.starlife.hacking")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawTiledBackground(BG, 0, 0, width, height);
        drawCenteredString(fontRendererObj, I18n.format("gui.starlife.atm.title"), width / 2, 20, 0x00FFAA);
        drawCenteredString(fontRendererObj, I18n.format("gui.starlife.balance", previewBalance), width / 2, 40, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                sendAction("atm_deposit", 100);
                previewBalance += 100;
                break;
            case 1:
                sendAction("atm_withdraw", 100);
                previewBalance = Math.max(0, previewBalance - 100);
                break;
            case 2:
                sendAction("atm_transfer", 250);
                break;
            case 3:
                sendAction("atm_hack", 0);
                break;
            default:
                break;
        }
    }
}
