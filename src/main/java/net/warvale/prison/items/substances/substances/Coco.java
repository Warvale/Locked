package net.warvale.prison.items.substances.substances;

import net.warvale.prison.items.substances.AbstractSubstance;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created By AAces on 8/6/17
 */

public class Coco extends AbstractSubstance {

    public Coco(){
        super(3, "Coco", Material.COCOA, ChatColor.RED);
    }

    @Override
    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2, false, false));
    }
}
