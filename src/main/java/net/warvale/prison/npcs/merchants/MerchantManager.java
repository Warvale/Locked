package net.warvale.prison.npcs.merchants;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MerchantManager {
    public static void createNPC(String name, Player creator){

    }
    public static void deleteNPC(String name){

    }
    public static void renameNPC(String oldName, String newName){

    }

    public static void setLocation(Location location, String npc){

    }
    public static void setPrice(String npc, int price){

    }
    public static void setItem(String npc, ItemStack item){

    }
    public static void setDenialMessage(String npc, String message){

    }
    public static void setSuccessMessage(String npc, String message){

    }
    public static void setNameColor(String npc, String colorCode){

    }

    public static Location getLocation(String npc){
        return null;
    }
    public static int getPrice(String npc){
        return 0;
    }
    public static ItemStack getItem(String npc){
        return null;
    }
    public static String getDenialMessage(String npc){
        return "";
    }
    public static String getSuccessMessage(String npc){
        return "";
    }
    public static String getNameColor(String npc){
        return "&f";
    }

    public static ArrayList<String> getAllNPCs(){
        return new ArrayList<>();
    }
    public static ArrayList<Location> getAllNPCsLocations(){
        ArrayList<Location> locations = new ArrayList<>();
        for(String npc : getAllNPCs()){
            locations.add(getLocation(npc));
        }
        return locations;
    }
}
