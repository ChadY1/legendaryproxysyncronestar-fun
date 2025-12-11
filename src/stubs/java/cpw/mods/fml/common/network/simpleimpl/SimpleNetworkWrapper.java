package cpw.mods.fml.common.network.simpleimpl;

import cpw.mods.fml.relauncher.Side;

public class SimpleNetworkWrapper {
    public SimpleNetworkWrapper(String channel) {}

    public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(IMessageHandler<REQ, REPLY> handler, Class<REQ> messageType, int id, Side side) {}

    public void sendToServer(IMessage message) {}
}
