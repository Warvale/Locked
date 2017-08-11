package net.warvale.prison.items.goditems;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveGodItem extends AbstractCommand {

	public GiveGodItem() {
		super("givegoditem", "");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) throws CommandException {

		Player player = (Player) sender;

		ItemStack goditem = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta goditemMeta = goditem.getItemMeta();
		goditemMeta.setDisplayName(ChatColor.BLUE + "GOD ITEM!");
		goditemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1000, true);
		goditemMeta.addEnchant(Enchantment.DURABILITY, 1000, true);
		goditem.setItemMeta(goditemMeta);
		player.getInventory().addItem(goditem);
		player.sendMessage(ChatColor.AQUA + "Receiving item...");

		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}
}
