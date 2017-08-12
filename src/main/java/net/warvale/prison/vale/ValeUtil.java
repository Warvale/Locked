package net.warvale.prison.vale;

import net.warvale.prison.Prison;
import net.warvale.prison.sql.SQLConnection;
import net.warvale.prison.sql.SQLUtil;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.logging.Level;

public class ValeUtil {


    public boolean setVale(String uuid, int amount) {
        try {
            Prison.get().getDb().executeSQL("UPDATE `vale_eco` SET `amount` = '"+amount+"' WHERE `vale_eco`.`uuid` = "+uuid);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getVale(String uuid) throws SQLException, ClassNotFoundException {
                return Prison.get().getDb().querySQL("SELECT * FROM `vale_eco` WHERE `uuid` = '"+uuid+"'").findColumn("amount");
    }
}
