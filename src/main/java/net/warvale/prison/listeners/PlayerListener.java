package net.warvale.prison.listeners;

import net.warvale.prison.ranks.RankManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        try {
            if (RankManager.getGuardLevel(player) == 1 || RankManager.getGuardLevel(player) == 2) {
                player.sendMessage(ChatColor.RED + "Guards and wardens can not drop items!");
                event.setCancelled(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        try {
            if (RankManager.getGuardLevel(player) == 1 || RankManager.getGuardLevel(player) == 2) {
                event.setKeepInventory(true);
            } else {
                event.setKeepInventory(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
