package net.warvale.prison.items.substances;

import net.warvale.prison.items.substances.substances.Blast;
import net.warvale.prison.items.substances.substances.Coco;
import net.warvale.prison.items.substances.substances.EnergyDrink;
import net.warvale.prison.items.substances.substances.Sugar;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created By AAces on 8/6/17
 */

public abstract class AbstractSubstance {
    private static ArrayList<AbstractSubstance> substances;
    private String name;
    private ItemStack item;
    private int id;

    public AbstractSubstance(int id, String name, Material mat, ChatColor nameColor) {
        this.name = name;
        this.id = id;
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "[Substance] " + nameColor + name);
        meta.setLore(Collections.singletonList(ChatColor.BOLD.toString() + ChatColor.DARK_RED + "CONTRABAND"));
        i.setItemMeta(meta);
        this.item = i;
    }

    public static void setup() {
        substances = new ArrayList<>();
        substances.add(new Sugar());
        substances.add(new Coco());
        substances.add(new EnergyDrink());
        substances.add(new Blast());
    }

    public static ArrayList<AbstractSubstance> getSubstances() {
        return substances;
    }

    public static AbstractSubstance getSubstance(String name) {
        for (AbstractSubstance drug : getSubstances()) {
            if (drug.getName().equals(name)) {
                return drug;
            }
        }
        return null;
    }

    public static AbstractSubstance getSubstance(int id) {
        for (AbstractSubstance drug : getSubstances()) {
            if (drug.getId() == id) {
                return drug;
            }
        }
        return null;
    }

    public abstract void effect(Player player);

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return item;
    }
}
