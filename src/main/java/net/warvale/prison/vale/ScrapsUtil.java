package net.warvale.prison.vale;

import net.warvale.prison.Prison;
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

public class ScrapsUtil {

    private static Prison plugin = Prison.get();

    public static void setScraps(Player player, int amount) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE scraps_eco SET amount = " + amount + " WHERE uuid = '" + uuid.toString() + "'");
        stmt.executeUpdate();

        ItemStack item = new ItemStack(Material.SULPHUR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN.toString() + getScraps(player) + (getScraps(player) == 1 ? " Scrap" : " Scraps"));
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        player.getInventory().setItem(8, item);
    }

    public static int getScraps(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT amount FROM scraps_eco WHERE uuid = '" + uuid.toString() + "'");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("amount");
    }

    public static void removeScraps(Player player, int amount) throws SQLException {
        int current = getScraps(player);
        setScraps(player, current - amount);
    }

    public static void addScraps(Player player, int amount) throws SQLException {
        int current = getScraps(player);
        setScraps(player, current + amount);
    }

    public static void payScraps(Player from, Player to, int amount) throws SQLException {
        int fromBal = getScraps(from);
        int toBal = getScraps(to);
        setScraps(from, fromBal - amount);
        setScraps(to, toBal + amount);
    }
}
