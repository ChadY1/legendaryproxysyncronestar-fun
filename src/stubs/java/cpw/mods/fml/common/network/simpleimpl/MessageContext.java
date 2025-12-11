package cpw.mods.fml.common.network.simpleimpl;

import net.minecraft.entity.player.EntityPlayerMP;

public class MessageContext {
    public NetHandlerPlayServer getServerHandler() { return new NetHandlerPlayServer(); }

    public static class NetHandlerPlayServer {
        public EntityPlayerMP playerEntity = new EntityPlayerMP();
    }
}
