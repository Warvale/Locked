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

public class BlockListener implements Listener {

    private Prison plugin;

    public BlockListener(Prison plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (inMineLocation(loc)) {
                if (block.getType().equals(Material.COAL_ORE)) {
                    event.setExpToDrop(0);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            loc.getBlock().setType(Material.COAL_ORE);
                        }

                    }.runTaskLater(this.plugin, 1200);
                } else if (block.getType().equals(Material.IRON_ORE)) {
                    event.setExpToDrop(0);
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            loc.getBlock().setType(Material.IRON_ORE);
                        }

                    }.runTaskLater(this.plugin, 6000);
                } else if (block.getType().equals(Material.DIAMOND_ORE)) {
                    event.setCancelled(true);
                    block.setType(Material.AIR);
                    event.setExpToDrop(0);
                    loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.DIAMOND_ORE));
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            loc.getBlock().setType(Material.DIAMOND_ORE);
                        }

                    }.runTaskLater(this.plugin, 12000);
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    public static boolean inMineLocation(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return (x >= 1347 && x <= 1383 && y >= 0 && y <= 25 && z <= 362 && z >= 338);
    }

}
