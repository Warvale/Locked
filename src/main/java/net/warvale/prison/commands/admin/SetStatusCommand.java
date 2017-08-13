package net.warvale.prison.commands.admin;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.ranks.RankManager;
import net.warvale.prison.vale.ValeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetStatusCommand extends AbstractCommand {
    public SetStatusCommand(){
        super("setstatus", "<player> <prisoner|guard|warden>");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;
        if(args.length != 2){
            return false;
        }
        boolean x = false;
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getName().equals(args[0])){
                x = true;
                break;
            }
        }
        if(!x){
            player.sendMessage(ChatColor.RED + "Could not find the specified player.");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        try {
            switch (args[1]) {
                case "prisoner":
                    if (RankManager.getGuardLevel(player) == 0){
                        player.sendMessage(ChatColor.RED + target.getName() + " is already a prisoner!");
                        return true;
                    }
                    RankManager.setGuardLevel(target, 0);
                    RankManager.setWantedLevel(target, 1);
                    target.getInventory().clear();
                    player.sendMessage(ChatColor.GREEN + target.getName() + " is now a prisoner!");
                    target.sendMessage(ChatColor.GREEN + player.getName() + " set your rank to prisoner!");
                    break;
                case "guard":
                    if (RankManager.getGuardLevel(player) == 1){
                        player.sendMessage(ChatColor.RED + target.getName() + " is already a guard!");
                        return true;
                    }
                    setGuardInv(target);
                    player.sendMessage(ChatColor.GREEN + target.getName() + " is now a guard!");
                    target.sendMessage(ChatColor.GREEN + player.getName() + " set your rank to guard!");
                    break;
                case "warden":
                    if (RankManager.getGuardLevel(player) == 2){
                        player.sendMessage(ChatColor.RED + target.getName() + " is already a warden!");
                        return true;
                    }
                    setWardenInv(target);
                    player.sendMessage(ChatColor.GREEN + target.getName() + " is now a warden!");
                    target.sendMessage(ChatColor.GREEN + player.getName() + " set your rank to warden!");
                    break;
                default:
                    return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    private void setWardenInv(Player target) throws SQLException {
        RankManager.setGuardLevel(target, 2);
        RankManager.setWantedLevel(target, 0);
        target.getInventory().clear();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(224, 33, 15));
        boots.setItemMeta(bootsMeta);
        target.getInventory().setBoots(boots);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
        leggingsMeta.setColor(Color.fromRGB(224, 33, 15));
        leggings.setItemMeta(leggingsMeta);
        target.getInventory().setLeggings(leggings);

        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta)chest.getItemMeta();
        chestMeta.setColor(Color.fromRGB(224, 33, 15));
        chest.setItemMeta(chestMeta);
        target.getInventory().setChestplate(chest);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
        helmetMeta.setColor(Color.fromRGB(224, 33, 15));
        helmet.setItemMeta(helmetMeta);
        target.getInventory().setHelmet(helmet);
        try{
            ValeUtil.setVale(target, ValeUtil.getVale(target));
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void setGuardInv(Player target) throws SQLException {
        RankManager.setGuardLevel(target, 1);
        RankManager.setWantedLevel(target, 0);
        target.getInventory().clear();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
        bootsMeta.setColor(Color.fromRGB(145, 0, 0));
        boots.setItemMeta(bootsMeta);
        target.getInventory().setBoots(boots);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
        leggingsMeta.setColor(Color.fromRGB(145, 0, 0));
        leggings.setItemMeta(leggingsMeta);
        target.getInventory().setLeggings(leggings);

        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta)chest.getItemMeta();
        chestMeta.setColor(Color.fromRGB(145, 0, 0));
        chest.setItemMeta(chestMeta);
        target.getInventory().setChestplate(chest);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
        helmetMeta.setColor(Color.fromRGB(145, 0, 0));
        helmet.setItemMeta(helmetMeta);
        target.getInventory().setHelmet(helmet);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
