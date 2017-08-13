package net.warvale.prison.vale;

import net.warvale.prison.Prison;
import net.warvale.prison.sql.SQLConnection;
import net.warvale.prison.sql.SQLUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class ValeUtil {

    private static Prison plugin = Prison.get();

    public static void setVale(Player player, int amount) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE vale_eco SET amount = "+amount+" WHERE uuid = '"+uuid.toString()+"'");
        stmt.executeUpdate();

        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN.toString() + getVale(player) + (getVale(player)==1?" Vale":" Vales"));
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        player.getInventory().setItem(8, item);
    }

    public static int getVale(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT amount FROM vale_eco WHERE uuid = '"+uuid.toString()+"'");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("amount");
    }

    public static void removeVale(Player player, int amount) throws SQLException {
        int current = getVale(player);
        setVale(player, current-amount);
    }

    public static void addVale(Player player, int amount) throws SQLException {
        int current = getVale(player);
        setVale(player, current+amount);
    }
}
