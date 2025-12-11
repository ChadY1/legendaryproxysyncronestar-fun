package gg.starfun.starliferp.server;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gg.starfun.starliferp.client.gui.GuiATM;
import gg.starfun.starliferp.client.gui.GuiJobs;
import gg.starfun.starliferp.client.gui.GuiMainMenu;
import gg.starfun.starliferp.client.gui.GuiMedic;
import gg.starfun.starliferp.client.gui.GuiMilitary;
import gg.starfun.starliferp.client.gui.GuiPassport;
import gg.starfun.starliferp.client.gui.GuiPolice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public static final int GUI_MAIN_MENU = 0;
    public static final int GUI_ATM = 1;
    public static final int GUI_PASSPORT = 2;
    public static final int GUI_JOBS = 3;
    public static final int GUI_POLICE = 4;
    public static final int GUI_MEDIC = 5;
    public static final int GUI_MILITARY = 6;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null; // client-only visual menus
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case GUI_ATM:
                return new GuiATM();
            case GUI_PASSPORT:
                return new GuiPassport();
            case GUI_JOBS:
                return new GuiJobs();
            case GUI_POLICE:
                return new GuiPolice();
            case GUI_MEDIC:
                return new GuiMedic();
            case GUI_MILITARY:
                return new GuiMilitary();
            default:
                return new GuiMainMenu();
        }
    }
}
