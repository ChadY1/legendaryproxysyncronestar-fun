package net.minecraft.client.gui;

public class GuiButton {
    public int id;
    public String displayString;

    public GuiButton(int id, int x, int y, int width, int height, String text) {
        this.id = id;
        this.displayString = text;
    }
}
