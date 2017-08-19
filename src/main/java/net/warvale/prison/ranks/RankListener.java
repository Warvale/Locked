package net.warvale.prison.ranks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;

public class RankListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        event.setCancelled(true);
        Player player = event.getPlayer();
        String message = event.getMessage();
        String playerNameWithPrefixSuffix = ChatColor.RED + "(ERROR FORMATTING NAME) " + ChatColor.WHITE + player.getName() + ": ";
        try {
            if(RankManager.isPrisoner(player)){
                RankManager.updateWantedLevel(player);
            }
            playerNameWithPrefixSuffix = ChatColor.translateAlternateColorCodes('&', (!RankManager.getRankPrefix(player).equals(" ")?RankManager.getRankPrefix(player) : "") + ChatColor.GRAY + RankManager.wantedLevelParser(RankManager.getWantedLevel(player)) + ChatColor.GOLD  + RankManager.guardLevelParser(RankManager.getGuardLevel(player)) + RankManager.getRankNameColor(player) +" "+ player.getName() +(!RankManager.getRankSuffix(player).equals(" ")? " "+ RankManager.getRankSuffix(player) : "") + ChatColor.GRAY + ": ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Player p : event.getRecipients()){
            p.sendMessage(playerNameWithPrefixSuffix + ChatColor.WHITE + message);
        }
    }
    @EventHandler
    public void onKill(PlayerDeathEvent event){
        Player target = event.getEntity();
        Player killer = target.getKiller();
        if(RankManager.isPrisoner(killer)){
            if(RankManager.isPrisoner(target)){
                killer.setTotalExperience(killer.getTotalExperience() + 250);
            } else if(RankManager.isGuard(target) || RankManager.isWarden(target)){
                killer.setTotalExperience(killer.getTotalExperience() + 2000);
            }
        }
    }
}
