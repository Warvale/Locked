package net.warvale.prison.items;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class GiveGodItem implements CommandExecutor {

	public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("givegoditem")) {
			Player player = (Player) cs;

			ItemStack goditem = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta goditemMeta = goditem.getItemMeta();
			goditemMeta.setDisplayName(ChatColor.BLUE + "GOD ITEM!");
			goditemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1000, true);
			goditemMeta.addEnchant(Enchantment.DURABILITY, 1000, true);
			goditem.setItemMeta(goditemMeta);

			player.getInventory().addItem(goditem);
			player.sendMessage(ChatColor.AQUA + "Recieving item...");

		}
		return true;

	}
}
