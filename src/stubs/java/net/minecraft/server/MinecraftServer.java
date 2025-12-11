package net.minecraft.server;

import net.minecraft.util.ChatComponentText;

public class MinecraftServer {
    private static final MinecraftServer INSTANCE = new MinecraftServer();
    public static MinecraftServer getServer() { return INSTANCE; }
    public void addChatMessage(ChatComponentText text) {}
}
