package net.warvale.prison.utils;

import net.warvale.prison.Prison;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Broadcast {

    public enum BroadcastType {
        SUCCESS,
        FAILURE,
        BASIC
    }

    public static void toConsole(Level level, String message) {
        Prison.get().getLogger().log(level, "[WarvalePrison v" + "0.1" + "] " + message);
    }

    public static void toPlayer(Player player, String message) {
        player.sendMessage(message);
    }

    public static void toSender(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void toPlayer(Player player, BroadcastType broadcastType, String message) {
        if(broadcastType == BroadcastType.SUCCESS) {
            player.sendMessage(ChatColor.DARK_GREEN + "[WarvalePrison] " + ChatColor.GREEN + message);
        } else if(broadcastType == BroadcastType.FAILURE) {
            player.sendMessage(ChatColor.DARK_RED + "[WarvalePrison] " + ChatColor.RED + message);
        } else if(broadcastType == BroadcastType.BASIC) {
            player.sendMessage(ChatColor.GOLD + "[WarvalePrison] " + ChatColor.YELLOW + message);
        }
    }

    public static void toSender(CommandSender sender, BroadcastType broadcastType, String message) {
        if(broadcastType == BroadcastType.SUCCESS) {
            sender.sendMessage(ChatColor.DARK_GREEN + "[WarvalePrison] " + ChatColor.GREEN + message);
        } else if(broadcastType == BroadcastType.FAILURE) {
            sender.sendMessage(ChatColor.DARK_RED + "[WarvalePrison] " + ChatColor.RED + message);
        } else if(broadcastType == BroadcastType.BASIC) {
            sender.sendMessage(ChatColor.GOLD + "[WarvalePrison] " + ChatColor.YELLOW + message);
        }
    }
}
