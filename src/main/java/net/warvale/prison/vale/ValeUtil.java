package net.warvale.prison.vale;

import net.warvale.prison.Prison;
import net.warvale.prison.sql.SQLConnection;
import net.warvale.prison.sql.SQLUtil;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class ValeUtil {

    private static Prison plugin = Prison.get();

    public static void setVale(Player player, int amount) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE vale_eco SET amount = "+amount+" WHERE uuid = '"+uuid.toString()+"'");
        stmt.executeUpdate();
    }

    public static int getVale(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT amount FROM vale_eco WHERE uuid = '"+uuid.toString()+"'");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("amount");
    }
}
