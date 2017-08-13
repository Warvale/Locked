package net.warvale.prison.messages;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MSGCommand extends AbstractCommand {
    public static HashMap<UUID, UUID> recentMessages = new HashMap<>();
    public MSGCommand(){
        super("msg", "<player> <message>");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(!(args.length >= 2)){
            return false;
        }
        boolean x = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(args[0])) {
                x = true;
                break;
            }
        }
        if (!x) {
            player.sendMessage(ChatColor.RED + args[0] + " is not online!");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        recentMessages.put(player.getUniqueId(), target.getUniqueId());
        recentMessages.put(target.getUniqueId(), player.getUniqueId());
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i<args.length;i++){
            sb.append(args[i]);
            sb.append(" ");
        }
        String message = sb.toString();
        try {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',ChatColor.AQUA + "To "+(!RankManager.getRankPrefix(target).equals(" ")?RankManager.getRankPrefix(target) : "") + RankManager.getRankNameColor(target) + " " + target.getName() + ChatColor.GRAY + ": " +ChatColor.WHITE+ message));
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',ChatColor.AQUA + "From "+(!RankManager.getRankPrefix(player).equals(" ")?RankManager.getRankPrefix(player) : "") + RankManager.getRankNameColor(player) + " " + player.getName() + ChatColor.GRAY + ": " +ChatColor.WHITE+ message));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        ArrayList<String> a = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            a.add(p.getName());
        }
        return a;
    }
}
