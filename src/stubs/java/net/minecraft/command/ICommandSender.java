package net.minecraft.command;

import net.minecraft.util.ChatComponentText;

public interface ICommandSender {
    void addChatMessage(ChatComponentText text);
}
