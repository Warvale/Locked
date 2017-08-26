package net.warvale.prison.utils;

import net.warvale.prison.Prison;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager { //Saving in case of future use
    public FileConfiguration ranksCFG;
    public File ranksFile;
    private Prison plugin = Prison.getPlugin(Prison.class);

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        ranksFile = new File(plugin.getDataFolder(), "ranks.yml");

        if (!ranksFile.exists()) {
            try {
                ranksFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ranksCFG = YamlConfiguration.loadConfiguration(ranksFile);
    }

    public FileConfiguration getRanksCFG() {
        return ranksCFG;
    }

    public void saveRanks() {
        try {
            ranksCFG.save(ranksFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
