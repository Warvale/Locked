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

public class Sugar extends AbstractSubstance {

    public Sugar(){
        super(1, "Sugar", Material.SUGAR, ChatColor.WHITE);
    }

    @Override
    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1200, 2, false, false));
    }
}
