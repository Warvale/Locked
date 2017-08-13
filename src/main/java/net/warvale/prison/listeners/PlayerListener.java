package net.warvale.prison.listeners;

import net.warvale.prison.Prison;
import net.warvale.prison.ranks.RankManager;
import net.warvale.prison.vale.ValeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerListener implements Listener {
    private Prison plugin;
    public PlayerListener(Prison plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        try {
            PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT * FROM vale_eco WHERE uuid = '"+player.getUniqueId().toString()+"' LIMIT 1");
            ResultSet set = stmt.executeQuery();
            if (!set.next()) {
                stmt.close();
                stmt = plugin.getDb().getConnection().prepareStatement("INSERT INTO vale_eco (uuid, name, amount) VALUES ('"+player.getUniqueId().toString()+"','"+player.getName()+"', 0)");
                stmt.execute();
                stmt.close();
            }
            set.close();
            ValeUtil.setVale(player, ValeUtil.getVale(player));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
        if(event.getItemDrop().getItemStack().getType().equals(Material.EMERALD)){
            event.setCancelled(true);
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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getSlot() == 8){
            event.setCurrentItem(null);
            try {
                ValeUtil.setVale((Player)event.getWhoClicked(), ValeUtil.getVale((Player)event.getWhoClicked()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.setCancelled(true);
        }
    }

}
