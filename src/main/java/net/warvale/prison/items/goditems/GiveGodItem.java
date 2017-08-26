package net.warvale.prison.items.goditems;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.commands.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiveGodItem extends AbstractCommand {

    public GiveGodItem() {
        super("givegoditem", "");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof Player)) {
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;

        ItemStack goditem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta goditemMeta = goditem.getItemMeta();
        goditemMeta.setDisplayName(ChatColor.BLUE + "GOD ITEM!");
        goditemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1000, true);
        goditemMeta.addEnchant(Enchantment.DURABILITY, 1000, true);
        goditem.setItemMeta(goditemMeta);
        if (args.length == 0) {
            player.getInventory().addItem(goditem);
            player.sendMessage(ChatColor.AQUA + "Receiving item...");
            return true;
        }
        if (args.length == 1) {
            boolean x = false;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equals(args[0])) {
                    x = true;
                    break;
                }
            }
            if (!x) {
                player.sendMessage(ChatColor.RED + "Could not find the specified player.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            target.getInventory().addItem(goditem);
            target.sendMessage(ChatColor.AQUA + "Receiving item...");
            return true;
        }
        player.sendMessage(ChatColor.RED + "/givegoditem [player]");
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return CommandUtils.generateTabCompletePlayers();
    }
}
