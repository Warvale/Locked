package net.warvale.prison.ranks;

import net.warvale.prison.Prison;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class RankManager {
    private static Prison plugin = Prison.get();
    //Returns the Prefix of the player's rank
    public static String getRankPrefix(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT prefix FROM ranks_locked WHERE id = "+getRankId(player)+" LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("prefix");
    }
    //Returns the Suffix of the player's rank
    public static String getRankSuffix(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT suffix FROM ranks_locked WHERE id = "+getRankId(player)+" LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("suffix");
    }
    //Returns the name color of the player's rank
    public static String getRankNameColor(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT color FROM ranks_locked WHERE id = "+getRankId(player)+" LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("color");
    }
    //Returns the id of the player's rank
    public static int getRankId(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT network_rank FROM users_locked WHERE uuid = '"+player.getUniqueId().toString()+"' LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("network_rank");
    }

    //Wanted Level:

    public static void setWantedLevel(Player player, int level) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE users_locked SET locked_wanted="+level+" WHERE uuid = '"+player.getUniqueId()+"'");
        stmt.executeUpdate();
    }

    public static int getWantedLevel(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT locked_wanted FROM users_locked WHERE uuid = '"+player.getUniqueId()+"' LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("locked_wanted");
    }

    public static String wantedLevelParser(int level){
        if(level == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(String.valueOf(level));
        sb.append("⋆");
        return sb.toString();
    }

    //Guard Level:
    //0=prisoner, 1=guard, 2=warden
    public static void setGuardLevel(Player player, int level) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE users_locked SET locked_guard_level="+level+" WHERE uuid = '"+player.getUniqueId()+"'");
        stmt.executeUpdate();
    }

    public static int getGuardLevel(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT locked_guard_level FROM users_locked WHERE uuid = '"+player.getUniqueId()+"' LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("locked_guard_level");
    }

    public static String guardLevelParser(int level){
        String s;
        switch (level){
            case 1:
                s=" ✵";
                break;
            case 2:
                s=" ❈";
                break;
            default:
                s="";
                break;
        }
        return s;
    }
}
