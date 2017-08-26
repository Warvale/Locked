package net.warvale.prison.items.substances.substances;

import net.warvale.prison.items.substances.AbstractSubstance;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blast extends AbstractSubstance {

    public Blast() {
        super(4, "Blast", Material.BONE, ChatColor.GRAY);
    }

    @Override
    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 900, 3, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10000, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 10000, 3, false, false));
    }
}
