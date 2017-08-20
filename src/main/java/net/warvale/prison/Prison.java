package net.warvale.prison;


import net.warvale.prison.commands.CommandHandler;
import net.warvale.prison.items.substances.AbstractSubstance;
import net.warvale.prison.items.substances.SubstanceListener;
import net.warvale.prison.listeners.BlockListener;
import net.warvale.prison.listeners.PlayerListener;
import net.warvale.prison.ranks.RankListener;
import net.warvale.prison.ranks.RankManager;
import net.warvale.prison.sql.SQLConnection;
import net.warvale.prison.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;


public class Prison extends JavaPlugin {
    private static Prison instance;

    private static CommandHandler cmds;

    private static World world = Bukkit.getWorld("world");

    public static Prison get() {
        return instance;
    }

    private SQLConnection db;

    public SQLConnection getDb() {
        return db;
    }

    public static World getWorld(){return world;}

    private HashMap<UUID, Integer> playtime = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        init();
        //Register events here:
        Bukkit.getPluginManager().registerEvents(new SubstanceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new RankListener(), this);
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()){
            savePlayTime(player);
        }
        playtime.clear();
    }

    private void init(){ //All startup methods here (excluding events):
        loadConfiguration();
        db = new SQLConnection(getConfig().getString("hostname"), getConfig().getInt("port"), getConfig().getString("database"), getConfig().getString("username"), getConfig().getString("password"));
        try {
            db.openConnection(); } catch(Exception e) {
            getLogger().log(Level.WARNING, "Could not establish connection to database, exception: "+e);
            return;
        }

        cmds = new CommandHandler(this);
        cmds.registerCommands();

        AbstractSubstance.setup();

        BlockUtils.resetOres();

        playTimeCounter();
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void retrievePlayTime(Player player){
        PreparedStatement stmt;
        int x = 0;
        try {
            stmt = getDb().getConnection().prepareStatement("SELECT playtime FROM users_locked WHERE uuid = '"+player.getUniqueId()+"' LIMIT 1");
            ResultSet set = stmt.executeQuery();
            set.next();
            x=set.getInt("playtime");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        playtime.put(player.getUniqueId(), x);
    }

    public void savePlayTime(Player player){
        try {
            PreparedStatement stmt = getDb().getConnection().prepareStatement("UPDATE users_locked SET playtime="+playtime.get(player.getUniqueId())+" WHERE uuid = '"+player.getUniqueId()+"'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        playtime.remove(player.getUniqueId());
    }

    private void playTimeCounter(){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(UUID uuid : playtime.keySet()){
                    playtime.put(uuid, playtime.get(uuid)+1);
                    if(playtime.get(uuid) % 3600 == 0){
                        if(RankManager.isPrisoner(Bukkit.getPlayer(uuid))){
                            Bukkit.getPlayer(uuid).setTotalExperience(Bukkit.getPlayer(uuid).getTotalExperience() + 100);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
    }
}