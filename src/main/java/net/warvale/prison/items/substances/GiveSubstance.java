package net.warvale.prison.items.substances;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By AAces on 8/6/17
 */

public class GiveSubstance extends AbstractCommand {

    public GiveSubstance(){
        super("givesubstance", "<substance> [player]");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof Player)){
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;
        if(args.length != 2 && args.length != 1){
            return false;
        }
        String substance = args[0];
        boolean x = false;
        for(AbstractSubstance d : AbstractSubstance.getSubstances()){
            if(d.getName().equals(substance)){
                x = true;
            }
        }
        if(substance.equals("1") || substance.equals("2") || substance.equals("3") || substance.equals("4")){
            x = true;
        }
        if (!x) {
            player.sendMessage(ChatColor.RED + "Drugs:");
            player.sendMessage(ChatColor.GOLD + "1. Sugar");
            player.sendMessage(ChatColor.GOLD + "2. Energy Drink");
            player.sendMessage(ChatColor.GOLD + "3. Coco");
            player.sendMessage(ChatColor.GOLD + "4. Blast");
            return true;
        }
        AbstractSubstance d = AbstractSubstance.getSubstance(1);
        if(StringUtils.isNumeric(substance)){
            for(AbstractSubstance dr : AbstractSubstance.getSubstances()){
                if(dr.getId() == Integer.valueOf(substance)){
                    d = AbstractSubstance.getSubstance(Integer.valueOf(substance));
                    break;
                }
            }
        } else {
            for(AbstractSubstance dr : AbstractSubstance.getSubstances()){
                if(dr.getName().equals(substance)){
                    d = AbstractSubstance.getSubstance(substance);
                    break;
                }
            }
        }
        if(args.length == 1){
            player.getInventory().addItem(d.getItemStack());
            player.sendMessage(ChatColor.RED + "You have given yourself " + d.getName());
            return true;
        } else {
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))){
                Bukkit.getPlayer(args[1]).getInventory().addItem(d.getItemStack());
                player.sendMessage(ChatColor.RED + "You have given " + args[1] + " " + ChatColor.GOLD + d.getName());
                Bukkit.getPlayer(args[1]).sendMessage(ChatColor.RED + "You have been given " + d.getName() + ChatColor.RED + " by " + ChatColor.GOLD + args[1]);
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "The specified player could not be found.");
                return true;
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
