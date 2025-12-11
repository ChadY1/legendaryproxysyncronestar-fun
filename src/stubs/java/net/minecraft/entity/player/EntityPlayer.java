package net.minecraft.entity.player;

import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EntityPlayer {
    public World worldObj = new World();
    public void addChatMessage(ChatComponentText text) {}
    public String getCommandSenderName() { return "player"; }
    public void openGui(Object mod, int id, World world, int x, int y, int z) {}
}
