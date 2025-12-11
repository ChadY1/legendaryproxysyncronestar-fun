package gg.starfun.starliferp.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class GuiActionMessage implements IMessage {
    private String action;
    private int value;

    public GuiActionMessage() {
    }

    public GuiActionMessage(String action, int value) {
        this.action = action;
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int len = buf.readInt();
        byte[] data = new byte[len];
        buf.readBytes(data);
        this.action = new String(data);
        this.value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] data = action.getBytes();
        buf.writeInt(data.length);
        buf.writeBytes(data);
        buf.writeInt(value);
    }
}
