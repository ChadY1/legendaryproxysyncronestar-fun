package net.minecraft.util;

public class ChatComponentText {
    private final String text;
    public ChatComponentText(String text) { this.text = text; }
    @Override public String toString() { return text; }
}
