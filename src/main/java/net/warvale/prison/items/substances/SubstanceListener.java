package net.warvale.prison.items.substances;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created By AAces on 8/6/17
 */

public class SubstanceListener implements Listener {
    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null) {
            ItemStack itemR = new ItemStack(item.getType(), 1);
            itemR.setItemMeta(item.getItemMeta());
            for (AbstractSubstance d : AbstractSubstance.getSubstances()) {
                if (d.getItemStack().getType().equals(item.getType())) {
                    d.effect(player);
                    if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.getInventory().removeItem(itemR);
                        player.updateInventory();
                    }
                    return;
                }
            }
        }
    }
}
