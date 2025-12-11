package net.minecraft.client.settings;

public class KeyBinding {
    private final String desc;
    private final int keyCode;
    private final String category;

    public KeyBinding(String desc, int keyCode, String category) {
        this.desc = desc;
        this.keyCode = keyCode;
        this.category = category;
    }

    public boolean isPressed() { return false; }
}
