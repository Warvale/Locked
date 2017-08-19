package net.warvale.prison.listeners;

import net.warvale.prison.Prison;
import net.warvale.prison.ranks.RankManager;
import net.warvale.prison.vale.ScrapsUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;


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
            PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT * FROM scraps_eco WHERE uuid = '"+player.getUniqueId().toString()+"' LIMIT 1");
            ResultSet set = stmt.executeQuery();
            if (!set.next()) {
                stmt.close();
                stmt = plugin.getDb().getConnection().prepareStatement("INSERT INTO scraps_eco (uuid, name, amount) VALUES ('"+player.getUniqueId().toString()+"','"+player.getName()+"', 0)");
                stmt.execute();
                stmt.close();
            }
            set.close();
            ScrapsUtil.setScraps(player, ScrapsUtil.getScraps(player));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        plugin.retrievePlayTime(player);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        plugin.savePlayTime(player);
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
        if(event.getItemDrop().getItemStack().getType().equals(Material.SULPHUR)){
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
                ScrapsUtil.setScraps((Player)event.getWhoClicked(), ScrapsUtil.getScraps((Player)event.getWhoClicked()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(!(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)){
            if(BlockListener.inMineLocation(player.getLocation())){
                if(player.getGameMode() != GameMode.SURVIVAL){player.setGameMode(GameMode.SURVIVAL);}
            } else {
                if(player.getGameMode() != GameMode.ADVENTURE){player.setGameMode(GameMode.ADVENTURE);}
            }
        }
    }

}
