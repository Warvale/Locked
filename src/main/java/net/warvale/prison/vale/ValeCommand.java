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


        Player target = Bukkit.getServer().getPlayer(args[1]);
        switch (args[0]) {


            case "set":
                if (Prison.get().getVale().setVale(target, Integer.parseInt( args[2]))) {
                    sender.sendMessage("Set player " + target.getName() + "/UUID="+target.getUniqueId().toString()+" 's vale amount to " + args[1]);
                } else {
                    sender.sendMessage("Something went wrong while doing that!");
                }
                break;
            case "get":

                if (target != null) {
                    try {
                        Prison.get().getVale().getVale(target);
                    } catch (Exception ex) {
                        Prison.get().getLogger().log(Level.WARNING, "Cannot get vales for player: " + target.getName());
                    }
                } else sender.sendMessage("Could not find that player!");

                break;
            case "take":
                break;
            case "give":
                break;

            default: break;
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
