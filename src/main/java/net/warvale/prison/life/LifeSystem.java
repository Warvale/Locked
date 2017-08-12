package net.warvale.prison.life;

import net.warvale.prison.Prison;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by CommandFox on 8/12/2017.
 */
public class LifeSystem {
    private static Prison plugin = Prison.get();
    private HashMap<UUID, String> playerVals = new HashMap<>(); //UUID, thirst|sanitation|exercise

    public void onPlayerJoin(PlayerJoinEvent event) {
        // When a player joins, add their correct values to the `playerVals` hashmap
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable(){ // Run the database functions async
            public void run() {
                Player p = event.getPlayer();
                UUID id = p.getUniqueId();
                String uuid = id.toString();
                PreparedStatement stmt = null;
                try {
                    stmt = plugin.getDb().getConnection().prepareStatement("SELECT tse FROM life_system WHERE uuid = "+uuid+" LIMIT 1");
                    ResultSet set = stmt.executeQuery(); // Get the player with the UUID `uuid`'s life levels from the database
                    set.next(); // Get the first matching row
                    playerVals.put(id, set.getString("tse")); // Add the player's values to `playerVals`
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            };
        });
    }

    public int getThirstLevel(UUID id){
        return Integer.parseInt(playerVals.get(id).split("|")[0]); // Get first part of the string, split by "|", where the uuid is `id`.
    }

    public int getSanitationLevel(UUID id){
        return Integer.parseInt(playerVals.get(id).split("|")[1]); // Get second part of the string, split by "|", where the uuid is `id`.
    }

    public int getExerciseLevel(UUID id){
        return Integer.parseInt(playerVals.get(id).split("|")[2]); // Get third part of the string, split by "|", where the uuid is `id`.
    }
}