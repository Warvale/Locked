package net.warvale.prison.ranks;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankCommand extends AbstractCommand {

    public RankCommand(){
        super("rank","");
    }

    private static void sendUsageMessage(Player player){
        player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "(Rank names are case-sensitive)");
        player.sendMessage(ChatColor.GOLD + "/rank create <name>");
        player.sendMessage(ChatColor.GOLD + "/rank delete <rank>" + ChatColor.RED + " CAN NOT BE UNDONE!");
        player.sendMessage(ChatColor.GOLD + "/rank rename <oldName> <newName>");
        player.sendMessage(ChatColor.GOLD + "/rank setprefix <rank> [prefix]");
        player.sendMessage(ChatColor.GOLD + "/rank setsuffix <rank> [suffix]");
        player.sendMessage(ChatColor.GOLD + "/rank setnamecolor <rank> <colorcode>");
        player.sendMessage(ChatColor.GOLD + "/rank addpermission <rank> <permission>");
        player.sendMessage(ChatColor.GOLD + "/rank removepermission <rank> <permission>");
        player.sendMessage(ChatColor.GOLD + "/rank getpermissions <rank>");
        player.sendMessage(ChatColor.GOLD + "/rank set <player> <rank>");
        player.sendMessage(ChatColor.GOLD + "/rank list");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;
        if(args.length == 0){
            sendUsageMessage(player);
            return true;
        }
        switch (args[0]){
            case "create":
                if(args.length != 2){
                    player.sendMessage(ChatColor.RED + "Usage: /rank create <name>");
                    return true;
                }
                if(!StringUtils.isAlpha(args[1])){
                    player.sendMessage(ChatColor.RED + "Rank names can only contain letters!");
                    return true;
                }
                try {
                    if(RankManager.doesRankExist(args[1])){
                        player.sendMessage(ChatColor.RED + "There is already a rank by this name!");
                        return true;
                    }
                    RankManager.createRank(args[1]);
                    player.sendMessage(ChatColor.GREEN + "Successfully created the rank '"+args[1]+"'!");
                    player.sendMessage(ChatColor.GREEN + "Set the prefix by doing "+ChatColor.GOLD + "/rank setprefix "+args[1]+" <prefix>");
                    player.sendMessage(ChatColor.GREEN + "Set the suffix by doing "+ChatColor.GOLD + "/rank setsuffix "+args[1]+" <suffix>");
                    player.sendMessage(ChatColor.GREEN + "Set the name color by doing "+ChatColor.GOLD + "/rank setnamecolor "+args[1]+" <colorcode>");
                    player.sendMessage(ChatColor.GREEN + "Set permissions by doing "+ChatColor.GOLD + "/rank addpermission "+args[1]+" <permission>");
                    player.sendMessage(ChatColor.GREEN + "Remove permissions by doing "+ChatColor.GOLD + "/rank removepermission "+args[1]+" <permission>");
                    player.sendMessage(ChatColor.GREEN + "Set a player to this rank by doing "+ChatColor.GOLD + "/rank set <user> "+args[1]);
                    player.sendMessage(ChatColor.GREEN + "Delete this rank by doing "+ChatColor.GOLD + "/rank delete " + args[1]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                if(args.length != 2){
                    player.sendMessage(ChatColor.RED + "Usage: /rank delete <name>");
                    return true;
                }
                try {
                    if(RankManager.doesRankExist(args[1])){
                        RankManager.deleteRank(args[1]);
                        player.sendMessage(ChatColor.GREEN + "Successfully deleted '"+args[1]+"'!");
                    } else {
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                    break;
            case "setprefix":
                try {
                    if(args.length != 3){
                        if(args.length != 2){
                            player.sendMessage(ChatColor.RED + "Usage: /rank setprefix <rank> [prefix]");
                            return true;
                        } else {
                            if (RankManager.doesRankExist(args[1])){
                                RankManager.setPrefix(RankManager.getRankId(args[1]), " ");
                                player.sendMessage(ChatColor.GREEN + "Successfully removed the prefix from the rank " + args[1] + "!");
                                break;
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                                return true;
                            }
                        }
                    }
                    if(RankManager.doesRankExist(args[1])){
                        if (args[2].length() <= 12){
                            RankManager.setPrefix(RankManager.getRankId(args[1]), args[2]);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',ChatColor.GREEN + "Successfully set the prefix of the rank '"+args[1]+"' to '"+args[2]+ChatColor.GREEN +"'!"));
                        } else {
                            player.sendMessage(ChatColor.RED + "Rank prefixes can not exceed 12 letters!");
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "setsuffix":
                try {
                    if(args.length != 3){
                        if(args.length != 2){
                            player.sendMessage(ChatColor.RED + "Usage: /rank setsuffix <rank> [suffix]");
                            return true;
                        } else {
                            if (RankManager.doesRankExist(args[1])){
                                RankManager.setSuffix(RankManager.getRankId(args[1]), " ");
                                player.sendMessage(ChatColor.GREEN + "Successfully removed the suffix from the rank " + args[1] + "!");
                                break;
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                                return true;
                            }
                        }
                    }
                    if(RankManager.doesRankExist(args[1])){
                        if (args[2].length() <= 12){
                            RankManager.setSuffix(RankManager.getRankId(args[1]), args[2]);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',ChatColor.GREEN + "Successfully set the suffix of the rank '"+args[1]+"' to '"+args[2]+ChatColor.GREEN +"'!"));
                        } else {
                            player.sendMessage(ChatColor.RED + "Rank suffix can not exceed 12 letters!");
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "set":
                if(args.length != 3){
                    player.sendMessage(ChatColor.RED + "Usage: /rank set <player> <rank>");
                    return true;
                }
                if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))){
                    player.sendMessage(ChatColor.RED + "The specified player could not be found!");
                    return true;
                }
                Player p = Bukkit.getPlayer(args[1]);
                try {
                    if(!RankManager.doesRankExist(args[2])){
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                    if(RankManager.getRankId(p) == RankManager.getRankId(args[2])){
                        player.sendMessage(ChatColor.RED + "This player is already has this rank!");
                        return true;
                    }
                    RankManager.setPlayerRank(p, args[2]);
                    player.sendMessage(ChatColor.GREEN + "Successfully set the rank of "+ p.getName()+ " to " +args[2]);
                    p.sendMessage(ChatColor.GREEN + player.getName() + " has set your rank to " + args[2]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "addpermission":
                if(args.length != 3){
                    player.sendMessage(ChatColor.RED + "Usage: /rank addpermission <rank> <permission>");
                    return true;
                }
                try {
                    if(!RankManager.doesRankExist(args[1])){
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                    if(RankManager.getPermissions(RankManager.getRankId(args[1])).contains(args[2])){
                        player.sendMessage(ChatColor.RED + "This rank already has access to this permission!");
                        return true;
                    }
                    RankManager.addPermission(RankManager.getRankId(args[1]), args[2]);
                    player.sendMessage(ChatColor.GREEN + "Successfully added " + args[2] + " to " + args[1] +"'s permissions.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "removepermission":
                if(args.length != 3){
                    player.sendMessage(ChatColor.RED + "Usage: /rank removepermission <rank> <permission>");
                    return true;
                }
                try {
                    if(!RankManager.doesRankExist(args[1])){
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                    if(!RankManager.getPermissions(RankManager.getRankId(args[1])).contains(args[2])){
                        player.sendMessage(ChatColor.RED + "This rank already doesn't have access to this permission!");
                        return true;
                    }
                    RankManager.removePermission(RankManager.getRankId(args[1]), args[2]);
                    player.sendMessage(ChatColor.GREEN + "Successfully removed " + args[2] + " from " + args[1] +"'s permissions.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "getpermissions":
                if(args.length != 2){
                    player.sendMessage(ChatColor.RED + "Usage: /rank getpermissions <rank>");
                    return true;
                }
                try {
                    if (!RankManager.doesRankExist(args[1])) {
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                    player.sendMessage(ChatColor.GREEN + args[1] + " has access to: ");
                    for(String s : RankManager.getPermissions(RankManager.getRankId(args[1]))){
                        player.sendMessage(ChatColor.GOLD + s);
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "list":
                player.sendMessage(ChatColor.RED + "Ranks: ");
                try {
                    for (String s : RankManager.getAllRanks()){
                        player.sendMessage(ChatColor.GOLD + s);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "setnamecolor":
                if(args.length != 3){
                    player.sendMessage(ChatColor.RED + "Usage: /rank setnamecolor <rank> <color>");
                    return true;
                }
                try {
                    if(RankManager.doesRankExist(args[1])){
                        if(args[2].contains("&")){
                            RankManager.setNameColor(RankManager.getRankId(args[1]), args[2]);
                            player.sendMessage(ChatColor.GREEN + "Successfully set the name color of the rank '"+args[1]+"' to '"+args[2]+"'!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Color codes must contain '&'!");
                            return true;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "There is no rank by this name!");
                        return true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "rename":
                if(args.length != 3){
                    player.sendMessage(ChatColor.RED + "Usage: /rank rename <oldName> <newName>");
                    return true;
                }
                try {
                    if(!RankManager.doesRankExist(args[1])){
                        player.sendMessage(ChatColor.RED + "There is no rank by the name " + args[1] + "!");
                        return true;
                    }
                    if(RankManager.doesRankExist(args[2])){
                        player.sendMessage(ChatColor.RED + "There is already a rank by the name " + args[2] + "!");
                        return true;
                    }
                    if(!StringUtils.isAlpha(args[1])){
                        player.sendMessage(ChatColor.RED + "Rank names can only contain letters!");
                        return true;
                    }
                    RankManager.renameRank(args[1], args[2]);
                    player.sendMessage(ChatColor.GREEN + "Successfully renamed "+ ChatColor.GOLD + args[1] +ChatColor.GREEN + " to " + ChatColor.GOLD + args[2] + ChatColor.GREEN + "!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                sendUsageMessage(player);
                break;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
