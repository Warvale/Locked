package net.warvale.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandUtils {
    public static ArrayList<String> generateTabCompleteArgs(String... args){
        ArrayList<String> n = new ArrayList<>();
        n.addAll(Arrays.asList(args));
        return n;
    }
    public static ArrayList<String> generateTabCompletePlayers(){
        ArrayList<String> n = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            n.add(p.getName());
        }
        return n;
    }
}
