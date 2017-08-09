package net.warvale.prison.listeners;

import net.warvale.prison.Prison;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class BlockListener implements Listener {

    private Prison plugin;

    public BlockListener(Prison plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();
        if(!player.getGameMode().equals(GameMode.CREATIVE)){
            if(inMineLocation(loc)){
                if(block.getType().equals(Material.COAL_ORE)){
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            loc.getBlock().setType(Material.COAL_ORE);
                        }

                    }.runTaskLater(this.plugin, 1200);
                } else if(block.getType().equals(Material.IRON_ORE)){
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            loc.getBlock().setType(Material.IRON_ORE);
                        }

                    }.runTaskLater(this.plugin, 6000);
                } else if(block.getType().equals(Material.DIAMOND_ORE)){
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.DIAMOND_ORE));
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            loc.getBlock().setType(Material.DIAMOND_ORE);
                        }

                    }.runTaskLater(this.plugin, 12000);
                } else { event.setCancelled(true); }
            } else { event.setCancelled(true);}
        }
    }

    private boolean inMineLocation(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return (x>=1348 && x<=1383 && y>=1 && y<=14 && z<=361&&z>=338);
    }

}
