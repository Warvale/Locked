package net.warvale.prison.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class AbstractItem {
    private static ArrayList<AbstractItem> items;
    private String name;
    private ItemStack item;
    private int id;

    public AbstractItem(int id, String name, Material mat, ChatColor nameColor) {
        this.name = name;
        this.id = id;
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(nameColor + name);
        i.setItemMeta(meta);
        this.item = i;
    }

    public static void setup() {
        items = new ArrayList<>();
    }

    public static ArrayList<AbstractItem> getItems() {
        return items;
    }

    public static AbstractItem getItem(String name) {
        for (AbstractItem item : getItems()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    } //Not to be confused with getItemStack

    public static AbstractItem getItem(int id) {
        for (AbstractItem item : getItems()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    } //Not to be confused with getItemStack

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
