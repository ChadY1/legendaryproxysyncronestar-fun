package net.minecraft.client;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.world.World;

public class Minecraft {
    public EntityClientPlayerMP thePlayer = new EntityClientPlayerMP();
    public World theWorld = new World();

    private static final Minecraft INSTANCE = new Minecraft();

    public static Minecraft getMinecraft() {
        return INSTANCE;
    }

    public net.minecraft.client.renderer.texture.TextureManager getTextureManager() {
        return new net.minecraft.client.renderer.texture.TextureManager();
    }
}
