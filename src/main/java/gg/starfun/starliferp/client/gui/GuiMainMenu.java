package gg.starfun.starliferp.client.gui;

import gg.starfun.starliferp.server.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiMainMenu extends AbstractStarLifeGui {
    private static final ResourceLocation BG = new ResourceLocation("starliferp", "textures/gui/background.png");

    @Override
    public void initGui() {
        buttonList.clear();
        int centerX = width / 2;
        int startY = height / 2 - 60;
        buttonList.add(new GuiButton(0, centerX - 75, startY, 150, 20, I18n.format("gui.starlife.atm")));
        buttonList.add(new GuiButton(1, centerX - 75, startY + 24, 150, 20, I18n.format("gui.starlife.identity")));
        buttonList.add(new GuiButton(2, centerX - 75, startY + 48, 150, 20, I18n.format("gui.starlife.jobs")));
        buttonList.add(new GuiButton(3, centerX - 75, startY + 72, 150, 20, I18n.format("gui.starlife.police")));
        buttonList.add(new GuiButton(4, centerX - 75, startY + 96, 150, 20, I18n.format("gui.starlife.medic")));
        buttonList.add(new GuiButton(5, centerX - 75, startY + 120, 150, 20, I18n.format("gui.starlife.military")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawTiledBackground(BG, 0, 0, width, height);
        drawCenteredString(fontRendererObj, "StarLifeRP", width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        Minecraft mc = Minecraft.getMinecraft();
        switch (button.id) {
            case 0:
                mc.thePlayer.openGui(null, GuiHandler.GUI_ATM, mc.theWorld, 0, 0, 0);
                break;
            case 1:
                mc.thePlayer.openGui(null, GuiHandler.GUI_PASSPORT, mc.theWorld, 0, 0, 0);
                break;
            case 2:
                mc.thePlayer.openGui(null, GuiHandler.GUI_JOBS, mc.theWorld, 0, 0, 0);
                break;
            case 3:
                mc.thePlayer.openGui(null, GuiHandler.GUI_POLICE, mc.theWorld, 0, 0, 0);
                break;
            case 4:
                mc.thePlayer.openGui(null, GuiHandler.GUI_MEDIC, mc.theWorld, 0, 0, 0);
                break;
            case 5:
                mc.thePlayer.openGui(null, GuiHandler.GUI_MILITARY, mc.theWorld, 0, 0, 0);
                break;
            default:
                break;
        }
    }
}
