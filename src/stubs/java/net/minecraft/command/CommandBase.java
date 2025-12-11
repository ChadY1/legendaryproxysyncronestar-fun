package net.minecraft.command;

public abstract class CommandBase implements ICommand {
    public abstract String getCommandName();
    public abstract String getCommandUsage(ICommandSender sender);
    public abstract void processCommand(ICommandSender sender, String[] args);
}
