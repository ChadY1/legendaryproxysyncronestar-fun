package cpw.mods.fml.common.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.command.ICommand;

public class FMLServerStartingEvent extends Event {
    public void registerServerCommand(ICommand command) {}
}
