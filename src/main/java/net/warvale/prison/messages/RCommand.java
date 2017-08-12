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
import java.util.List;

public class RCommand extends AbstractCommand {
    public RCommand(){
        super("r", "<message>");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(!(args.length >= 1)){
            return false;
        }
        if(!MSGCommand.recentMessages.containsKey(player.getUniqueId())){
            player.sendMessage(ChatColor.RED + "You have not spoken to anyone recently!");
            return true;
        }
        boolean x = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(Bukkit.getPlayer(MSGCommand.recentMessages.get(player.getUniqueId())).getName())) {
                x = true;
                break;
            }
        }
        if (!x) {
            player.sendMessage(ChatColor.RED + Bukkit.getPlayer(MSGCommand.recentMessages.get(player.getUniqueId())).getName() + " is not online!");
            return true;
        }
        Player target = Bukkit.getPlayer(MSGCommand.recentMessages.get(player.getUniqueId()));
        MSGCommand.recentMessages.put(player.getUniqueId(), target.getUniqueId());
        MSGCommand.recentMessages.put(target.getUniqueId(), player.getUniqueId());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<args.length;i++){
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
        return new ArrayList<>();
    }
}
