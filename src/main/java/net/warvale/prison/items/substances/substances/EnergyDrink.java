package net.warvale.prison.items.substances.substances;

import net.warvale.prison.items.substances.AbstractSubstance;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnergyDrink extends AbstractSubstance {

    public EnergyDrink() {
        super(2, "Energy Drink", Material.GLASS_BOTTLE, ChatColor.GREEN);
    }

    @Override
    public void effect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1200, 3, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 2, false, false));

    }
}
