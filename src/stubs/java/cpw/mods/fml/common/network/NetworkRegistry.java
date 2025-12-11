package cpw.mods.fml.common.network;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkRegistry {
    public static final NetworkRegistry INSTANCE = new NetworkRegistry();

    public SimpleNetworkWrapper newSimpleChannel(String channel) {
        return new SimpleNetworkWrapper(channel);
    }

    public void registerGuiHandler(Object mod, IGuiHandler handler) {}
}
