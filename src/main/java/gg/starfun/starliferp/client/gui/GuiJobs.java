package gg.starfun.starliferp.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiJobs extends AbstractStarLifeGui {
    private static final ResourceLocation BG = new ResourceLocation("starliferp", "textures/gui/jobs.png");

    @Override
    public void initGui() {
        buttonList.clear();
        int centerX = width / 2;
        int startY = height / 2 - 30;
        buttonList.add(new GuiButton(0, centerX - 75, startY, 150, 20, I18n.format("gui.starlife.job.miner")));
        buttonList.add(new GuiButton(1, centerX - 75, startY + 24, 150, 20, I18n.format("gui.starlife.job.farmer")));
        buttonList.add(new GuiButton(2, centerX - 75, startY + 48, 150, 20, I18n.format("gui.starlife.job.mechanic")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawTiledBackground(BG, 0, 0, width, height);
        drawCenteredString(fontRendererObj, I18n.format("gui.starlife.jobs.title"), width / 2, 20, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        sendAction("job_select", button.id);
    }
}
