package net.warvale.prison.npcs.merchants;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.commands.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MerchantCommand extends AbstractCommand {
    public MerchantCommand(){
        super("shopnpc","");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can execute this command!");
        }
        Player player = (Player) sender;
        if(args.length==0){
            sendUsageMessage(player);
            return true;
        }
        switch (args[0]){
            case "create":
                break;
            case "delete":
                break;
            case "setprice":
                break;
            case "setitem":
                break;
            case "setdenialmessage":
                break;
            case "setsuccessmessage":
                break;
            case "movehere":
                break;
            case "tp":
                break;
            case "setnamecolor":
                break;
            case "rename":
                break;
            case "list":
                break;
            default:
                sendUsageMessage(player);
                break;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return CommandUtils.generateTabCompleteArgs("create", "delete", "setprice", "setitem", "setdenialmessage", "setsuccessmessage", "movehere", "tp", "rename", "setnamecolor", "list");
    }

    private void sendUsageMessage(Player p){
        p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "(NPC names are case-sensitive)");
        p.sendMessage(ChatColor.GOLD + "/shopnpc create <name>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc delete <name>" + ChatColor.RED + " CAN NOT BE UNDONE!");
        p.sendMessage(ChatColor.GOLD + "/shopnpc rename <oldName> <newName>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setprice <npc> <price>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setitem <npc>" + ChatColor.GREEN + " Sets sold item to itemstack in your hand.");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setdenialmessage <npc> [denialMessage]");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setsuccessmessage <npc> [successMessage]");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setnamecolor <name> <colorcode>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc tp <npc>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc movehere <npc>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc list");
    }
}
