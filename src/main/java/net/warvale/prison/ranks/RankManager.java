package net.warvale.prison.ranks;

import net.warvale.prison.Prison;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RankManager {
    private static Prison plugin = Prison.get();

    //Returns the Prefix of the player's rank
    public static String getRankPrefix(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT prefix FROM ranks_locked WHERE id = " + getRankId(player) + " LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("prefix");
    }

    //Returns the Suffix of the player's rank
    public static String getRankSuffix(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT suffix FROM ranks_locked WHERE id = " + getRankId(player) + " LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("suffix");
    }

    //Returns the name color of the player's rank
    public static String getRankNameColor(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT color FROM ranks_locked WHERE id = " + getRankId(player) + " LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getString("color");
    }

    //Returns the id of the player's rank
    public static int getRankId(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT network_rank FROM users_locked WHERE uuid = '" + player.getUniqueId().toString() + "' LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("network_rank");
    }
    //Wanted Level:

    public static void setWantedLevel(Player player, int level) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE users_locked SET locked_wanted=" + level + " WHERE uuid = '" + player.getUniqueId() + "'");
        stmt.executeUpdate();
        switch (level) {
            case 0:
                player.setTotalExperience(0);
                break;
            case 1:
                player.setTotalExperience(1);
                break;
            case 2:
                player.setTotalExperience(500);
                break;
            case 3:
                player.setTotalExperience(2300);
                break;
            case 4:
                player.setTotalExperience(10800);
                break;
            case 5:
                player.setTotalExperience(45300);
                break;
            case 6:
                player.setTotalExperience(162500);
                break;
            case 7:
                player.setTotalExperience(452120);
                break;
            case 8:
                player.setTotalExperience(1245980);
                break;
            case 9:
                player.setTotalExperience(4560370);
                break;
        }
    }

    public static int getWantedLevel(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT locked_wanted FROM users_locked WHERE uuid = '" + player.getUniqueId() + "' LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("locked_wanted");
    }

    public static String wantedLevelParser(int level) {
        if (level == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(String.valueOf(level));
        sb.append("⋆");
        return sb.toString();
    }

    public static void updateWantedLevel(Player player) throws SQLException {
        int exp = player.getTotalExperience();
        if (exp >= 4560370) {
            setWantedLevel(player, 9);
        } else if (exp >= 1245980) {
            setWantedLevel(player, 8);
        } else if (exp >= 452120) {
            setWantedLevel(player, 7);
        } else if (exp >= 162500) {
            setWantedLevel(player, 6);
        } else if (exp >= 45300) {
            setWantedLevel(player, 5);
        } else if (exp >= 10800) {
            setWantedLevel(player, 4);
        } else if (exp >= 2300) {
            setWantedLevel(player, 3);
        } else if (exp >= 500) {
            setWantedLevel(player, 2);
        } else if (exp >= 1) {
            setWantedLevel(player, 1);
        } else {
            setWantedLevel(player, 0);
        }

    }

    //Guard Level:
    //0=prisoner, 1=guard, 2=warden
    public static void setGuardLevel(Player player, int level) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE users_locked SET locked_guard_level=" + level + " WHERE uuid = '" + player.getUniqueId() + "'");
        stmt.executeUpdate();
    }

    public static int getGuardLevel(Player player) throws SQLException {
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT locked_guard_level FROM users_locked WHERE uuid = '" + player.getUniqueId() + "' LIMIT 1");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("locked_guard_level");
    }

    public static String guardLevelParser(int level) {
        String s;
        switch (level) {
            case 1:
                s = " ✵";
                break;
            case 2:
                s = " ❈";
                break;
            default:
                s = "";
                break;
        }
        return s;
    }

    public static boolean isGuard(Player player) {
        try {
            return (getGuardLevel(player) == 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isWarden(Player player) {
        try {
            return (getGuardLevel(player) == 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPrisoner(Player player) {
        try {
            return (getGuardLevel(player) == 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
