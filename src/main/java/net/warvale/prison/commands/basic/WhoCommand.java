package net.warvale.prison.commands.basic;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WhoCommand extends AbstractCommand {
    public WhoCommand() {
        super("who", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof Player)) {
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;
        int online = Bukkit.getOnlinePlayers().size();
        int max = Bukkit.getMaxPlayers();
        try {
            player.sendMessage(ChatColor.GOLD + "There are " + ChatColor.RED + online + ChatColor.GOLD + " players online out of " + ChatColor.RED + max + ChatColor.GOLD + " players!");
            ArrayList<Player> p = new ArrayList<>();
            p.addAll(Bukkit.getOnlinePlayers());
            StringBuilder sb = new StringBuilder();
            player.sendMessage(ChatColor.GOLD + "Online Players:");
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 3) {//Admin
                    sb.append(ChatColor.translateAlternateColorCodes('&', RankManager.getRankPrefix(p.get(i)) + " " + ChatColor.WHITE + p.get(i).getName()));
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 8) {//Dev
                    sb.append(ChatColor.translateAlternateColorCodes('&', RankManager.getRankPrefix(p.get(i)) + " " + ChatColor.WHITE + p.get(i).getName()));
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 11) {//SrMod
                    sb.append(ChatColor.translateAlternateColorCodes('&', RankManager.getRankPrefix(p.get(i)) + " " + ChatColor.WHITE + p.get(i).getName()));
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 5) {//Mod
                    sb.append(ChatColor.translateAlternateColorCodes('&', RankManager.getRankPrefix(p.get(i)) + " " + ChatColor.WHITE + p.get(i).getName()));
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 10) {//JrMod
                    sb.append(ChatColor.translateAlternateColorCodes('&', RankManager.getRankPrefix(p.get(i)) + " " + ChatColor.WHITE + p.get(i).getName()));
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 4) {//Builder
                    sb.append(ChatColor.translateAlternateColorCodes('&', RankManager.getRankPrefix(p.get(i)) + " " + ChatColor.WHITE + p.get(i).getName()));
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            for (int i = 0; i < p.size(); i++) {
                if (RankManager.getRankId(p.get(i)) == 0) {//Default
                    sb.append(ChatColor.WHITE + p.get(i).getName());
                    sb.append(ChatColor.GOLD + ", ");
                }
            }
            sb.deleteCharAt(sb.length() - 2);//Removes last comma
            player.sendMessage(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
