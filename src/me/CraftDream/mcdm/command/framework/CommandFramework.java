package me.CraftDream.mcdm.command.framework;

import cn.nukkit.command.*;
import cn.nukkit.plugin.*;
import cn.nukkit.utils.*;

public abstract class CommandFramework implements CommandExecutor {
	
	private String label;
	public abstract void execute(CommandSender commandSender, String str, String[] strArr);
	
    public CommandFramework(String label) {
        this.label = label;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            execute(sender, label, args);
            return true;
        } catch (CommandException ex) {
            if (ex.getMessage() == null || ex.getMessage().isEmpty()) {
                return true;
            }
            sender.sendMessage(TextFormat.RED + ex.getMessage());
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
            return true;
        }
	}
	
    public static boolean register(Plugin plugin, CommandFramework command) {
        PluginCommand pluginCommand = (PluginCommand) plugin.getServer().getPluginCommand(command.label);
        if (pluginCommand == null) {
            return false;
        }
        pluginCommand.setExecutor(command);
        return true;
    }

}
