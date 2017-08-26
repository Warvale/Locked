package net.warvale.prison.npcs.merchants;

import net.warvale.prison.vale.ScrapsUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class MerchantListener implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        try {
            int balance = ScrapsUtil.getScraps(player);
            if (entity.getType().equals(EntityType.VILLAGER)) {
                event.setCancelled(true);
                String npc = entity.getName();
                if (MerchantManager.getAllNPCs().contains(npc)) {
                    ItemStack selling = MerchantManager.getItem(npc);
                    int price = MerchantManager.getPrice(npc);
                    if (balance >= price) {
                        player.getInventory().addItem(selling);
                        player.sendMessage(MerchantManager.getSuccessMessage(npc));
                        ScrapsUtil.removeScraps(player, price);
                    } else {
                        player.sendMessage(MerchantManager.getDenialMessage(npc));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
