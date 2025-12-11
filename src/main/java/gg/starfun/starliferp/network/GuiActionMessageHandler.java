package gg.starfun.starliferp.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class GuiActionMessageHandler implements IMessageHandler<GuiActionMessage, IMessage> {
    @Override
    public IMessage onMessage(final GuiActionMessage message, final MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        String text = String.format("[StarLifeRP] %s (%d)", message.getAction(), message.getValue());
        player.addChatMessage(new ChatComponentText(text));
        MinecraftServer.getServer().addChatMessage(new ChatComponentText(player.getCommandSenderName() + " -> " + text));
        return null;
    }
}
