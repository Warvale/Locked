package net.warvale.prison.commands.admin;

import net.warvale.prison.Prison;
import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.npcs.merchants.MerchantManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NPCCommand extends AbstractCommand {
    public NPCCommand(){
        super("npc", "<kill|spawn> [name]");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;
        switch (args.length){
            case 1:
                switch (args[0]) {
                    case "kill":
                        Prison.killAllNPCs();
                        player.sendMessage(ChatColor.GREEN + "Killed all npcs!");
                        break;
                    case "spawn":
                        Prison.resetupNPCs();
                        player.sendMessage(ChatColor.GREEN + "Re-spawned all npcs!");
                        break;
                    default:
                        return false;
                }
                break;
            case 2:
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "Could not find an npc by this name!");
                    return true;
                }
                switch (args[0]) {
                    case "kill":
                        Prison.killNPC(args[1]);
                        player.sendMessage(ChatColor.GREEN + "Killed " + args[1] + "!");
                        break;
                    case "spawn":
                        Prison.resetupNPC(args[1]);
                        player.sendMessage(ChatColor.GREEN + "Re-spawned " + args[1] + "!");
                        break;
                    default:
                        return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return MerchantManager.getAllNPCs();
    }
}
