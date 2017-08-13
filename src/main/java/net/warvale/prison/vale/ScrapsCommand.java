package net.warvale.prison.vale;

import net.warvale.prison.Prison;
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

public class ScrapsCommand extends AbstractCommand {
    public ScrapsCommand() {
        super("scrap", "<set/get> <player> [amount (only for 'set')]");
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can use this command");
        }
        Player player = (Player) sender;
        if (args.length > 3 || args.length < 2) {
            return false;
        }
        boolean x = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(args[1])) {
                x = true;
                break;
            }
        }
        if (!x) {
            player.sendMessage(ChatColor.RED + "Could not find the specified player.");
            return true;
        }
        Player target = Bukkit.getPlayer(args[1]);
        switch (args[0]) {
            case "get":
                try {
                    player.sendMessage(ChatColor.GREEN + target.getName() + "'s balance is " + ChatColor.GOLD + Integer.toString(ScrapsUtil.getScraps(target)) + ChatColor.GREEN + "!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "set":
                if(args.length!=3){
                    return false;
                }
                if(!StringUtils.isNumeric(args[2])){
                    return false;
                }
                try {
                    ScrapsUtil.setScraps(target, Integer.valueOf(args[2]));
                    player.sendMessage(ChatColor.GREEN + target.getName() + "'s balance is now " + ChatColor.GOLD + args[2] + ChatColor.GREEN + "!");
                    target.sendMessage(ChatColor.GREEN + player.getName() + " set your balance to " + ChatColor.GOLD + args[2] + ChatColor.GREEN + "!");
                } catch (SQLException e){
                    e.printStackTrace();
                }
                break;
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
