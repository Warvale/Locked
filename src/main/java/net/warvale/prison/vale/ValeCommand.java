package net.warvale.prison.vale;

import net.warvale.prison.Prison;
import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ValeCommand extends AbstractCommand {
    private final Prison plugin;

    public ValeCommand(Prison plugin) {
        super("vale","<set/get> <player> [amount (only if its set)]");
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
    if (args.length != 2 || args.length != 3) {
        return false;
    }
        switch (args[0]) {
            case "get":
                try {
                sender.sendMessage(Integer.toString( plugin.getVale().getVale(args[0])));}catch(Exception e){sender.sendMessage("Whoopsy!, something went wrong.");
                plugin.getLogger().log(Level.WARNING, e.toString());
                }
                return true;
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
