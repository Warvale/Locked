package net.warvale.prison.listeners;

import net.warvale.prison.Prison;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerListener implements Listener {
    private Prison plugin;
    public PlayerListener(Prison plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player user = event.getPlayer();
        //Put player into database
        try {
            PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT * FROM users_locked WHERE uuid = '"+user.getUniqueId().toString()+"' LIMIT 1");
            ResultSet set = stmt.executeQuery();
            if (!set.next()) {
                stmt.close();
                stmt = plugin.getDb().getConnection().prepareStatement("INSERT INTO users_locked (uuid, name) VALUES ('"+user.getUniqueId().toString()+"', '"+user.getName()+"')");
                stmt.execute();
                stmt.close();
            }
            set.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
