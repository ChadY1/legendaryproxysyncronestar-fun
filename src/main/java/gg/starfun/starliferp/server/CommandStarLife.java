package gg.starfun.starliferp.server;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.Collections;
import java.util.List;

/**
 * Server-side /slr dispatcher using the vanilla CommandBase to match the
 * Forge 1.7.10 ICommand binary signature (avoids AbstractMethodError when
 * Mohist/Forge compares raw Object in compareTo).
 */
public class CommandStarLife extends CommandBase {
    @Override
    public String getCommandName() {
        return "slr";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/slr <menu|atm|id|jobs|police|medic|military>";
    }

    @Override
    public List getCommandAliases() {
        return Collections.singletonList("starliferp");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("Commande en jeu uniquement."));
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        int id = GuiHandler.GUI_MAIN_MENU;
        if (args.length > 0) {
            String sub = args[0].toLowerCase();
            if (sub.equals("atm")) id = GuiHandler.GUI_ATM;
            else if (sub.equals("id")) id = GuiHandler.GUI_PASSPORT;
            else if (sub.equals("jobs")) id = GuiHandler.GUI_JOBS;
            else if (sub.equals("police")) id = GuiHandler.GUI_POLICE;
            else if (sub.equals("medic")) id = GuiHandler.GUI_MEDIC;
            else if (sub.equals("military")) id = GuiHandler.GUI_MILITARY;
        }

        FMLNetworkHandler.openGui(player, null, id, player.worldObj, 0, 0, 0);
        player.addChatMessage(new ChatComponentText("§b[StarLifeRP] Menu: " + id));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        // StarLifeRP commands are intentionally open to all players for RP usage.
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

}
