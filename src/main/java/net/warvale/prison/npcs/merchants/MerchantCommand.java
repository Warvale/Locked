package net.warvale.prison.npcs.merchants;

import net.warvale.prison.commands.AbstractCommand;
import net.warvale.prison.commands.CommandException;
import net.warvale.prison.commands.CommandUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MerchantCommand extends AbstractCommand {
    public MerchantCommand(){
        super("shopnpc","");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can execute this command!");
        }
        Player player = (Player) sender;
        if(args.length==0){
            sendUsageMessage(player);
            return true;
        }
        switch (args[0]){
            case "create":
                if(args.length!=2){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc create <name>");
                    return true;
                }
                if(MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is already an NPC by the name " + args[1] + "!");
                    return true;
                }
                MerchantManager.createNPC(args[1], player);
                player.sendMessage(ChatColor.GREEN + "Successfully created the NPC " + args[1] + "!");
                break;
            case "delete":
                if(args.length!=2){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc delete <name>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                MerchantManager.deleteNPC(args[1]);
                player.sendMessage(ChatColor.GREEN + "Successfully deleted the NPC " + args[1] + "!");
                break;
            case "setprice":
                if(args.length!=3){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc setprice <npc> <price>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                if(!StringUtils.isNumeric(args[2])){
                    player.sendMessage(ChatColor.RED + "Price must be numeric!");
                    return false;
                }
                MerchantManager.setPrice(args[1], Integer.valueOf(args[2]));
                player.sendMessage(ChatColor.GREEN + "Successfully set the price of the item of the NPC " + args[1] + " to "+args[2]+"!");
                break;
            case "setitem":
                if(args.length!=2){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc setitem <npc>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
                    player.sendMessage(ChatColor.RED + "You must be holding an item for the npc to sell!");
                    return false;
                }
                MerchantManager.setItem(args[1], player.getInventory().getItemInMainHand());
                player.sendMessage(ChatColor.GREEN + "Successfully set the item of the NPC " + args[1] + " to "+player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()+"!");
                break;
            case "setdenialmessage":
                if(!(args.length>=3)){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc setdenialmessage <npc> <message>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                for(int i = 2; i < args.length; i++){
                    sb.append(args[i]);
                    sb.append(" ");
                }
                String message = sb.toString();
                MerchantManager.setDenialMessage(args[1], message);
                player.sendMessage(ChatColor.GREEN + "Successfully set the denial message of the NPC " + args[1] + " to \""+message+"\"!");
                break;
            case "setsuccessmessage":
                if(!(args.length>=3)){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc setsuccessmessage <npc> <message>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                StringBuilder sbb = new StringBuilder();
                for(int i = 2; i < args.length; i++){
                    sbb.append(args[i]);
                    sbb.append(" ");
                }
                String messagee = sbb.toString();
                MerchantManager.setSuccessMessage(args[1], messagee);
                player.sendMessage(ChatColor.GREEN + "Successfully set the denial message of the NPC " + args[1] + " to \""+messagee+"\"!");
                break;
            case "movehere":
                if(!(args.length>=2)){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc movehere <npc>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                MerchantManager.setLocation(player.getLocation(), args[1]);
                player.sendMessage(ChatColor.GREEN + "Successfully moved the NPC " + args[1] + " to your location!");
                break;
            case "tp":
                if(!(args.length>=2)){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc tp <npc>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                player.teleport(MerchantManager.getLocation(args[1]));
                player.sendMessage(ChatColor.GREEN + "Successfully teleported to the NPC " + args[1] + "!");
                break;
            case "setnamecolor":
                player.sendMessage(ChatColor.RED + "WIP");
                break;
            case "rename":
                if(!(args.length>=3)){
                    player.sendMessage(ChatColor.GOLD + "/shopnpc rename <oldName> <newName>");
                    return true;
                }
                if(!MerchantManager.getAllNPCs().contains(args[1])){
                    player.sendMessage(ChatColor.RED + "There is not an NPC by the name " + args[1] + "!");
                    return true;
                }
                if(MerchantManager.getAllNPCs().contains(args[2])){
                    player.sendMessage(ChatColor.RED + "There is already an NPC by the name " + args[2] + "!");
                    return true;
                }
                MerchantManager.renameNPC(args[1], args[2]);
                player.sendMessage(ChatColor.GREEN + "Successfully changed the name of the NPC " + args[1] + " to " + args[2] + "!");
                break;
            case "list":
                player.sendMessage(ChatColor.GREEN + "All NPCs:");
                StringBuilder sbbb = new StringBuilder();
                for(String s : MerchantManager.getAllNPCs()){
                    sbbb.append(s);
                    sbbb.append(",");
                    sbbb.append(" ");
                }
                sbbb.deleteCharAt(sbbb.length()-2);
                String messageee = sbbb.toString();
                player.sendMessage(ChatColor.GREEN + messageee);
                break;
            default:
                sendUsageMessage(player);
                break;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return CommandUtils.generateTabCompleteArgs("create", "delete", "setprice", "setitem", "setdenialmessage", "setsuccessmessage", "movehere", "tp", "rename", "setnamecolor", "list");
    }

    private void sendUsageMessage(Player p){
        p.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "(NPC names are case-sensitive)");
        p.sendMessage(ChatColor.GOLD + "/shopnpc create <name>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc delete <name>" + ChatColor.RED + " CAN NOT BE UNDONE!");
        p.sendMessage(ChatColor.GOLD + "/shopnpc rename <oldName> <newName>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setprice <npc> <price>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setitem <npc>" + ChatColor.GREEN + " Sets sold item to itemstack in your hand.");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setdenialmessage <npc> [denialMessage]");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setsuccessmessage <npc> [successMessage]");
        p.sendMessage(ChatColor.GOLD + "/shopnpc setnamecolor <name> <colorcode>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc tp <npc>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc movehere <npc>");
        p.sendMessage(ChatColor.GOLD + "/shopnpc list");
    }
}
