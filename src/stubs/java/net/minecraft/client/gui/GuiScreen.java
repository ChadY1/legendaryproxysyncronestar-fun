package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiScreen {
    public int width;
    public int height;
    public List<GuiButton> buttonList = new ArrayList<>();
    public Object fontRendererObj = new Object();

    public void drawCenteredString(Object font, String text, int x, int y, int color) {}
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
    public void initGui() {}
    public void actionPerformed(GuiButton button) {}
    public void drawTexturedModalRect(int x, int y, int u, int v, int w, int h) {}
}
