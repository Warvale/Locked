package net.warvale.prison;


import net.warvale.prison.commands.CommandHandler;
import net.warvale.prison.items.substances.AbstractSubstance;
import net.warvale.prison.items.substances.SubstanceListener;
import net.warvale.prison.listeners.BlockListener;
import net.warvale.prison.listeners.IronKeyListener;
import net.warvale.prison.listeners.PlayerListener;
import net.warvale.prison.ranks.RankListener;
import net.warvale.prison.sql.SQLConnection;
import net.warvale.prison.utils.BlockUtils;
import net.warvale.prison.vale.ValeUtil;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public class Prison extends JavaPlugin {
    private static Prison instance;

    private static CommandHandler cmds;

    private static World world = Bukkit.getWorld("world");

    public static Prison get() {
        return instance;
    }

    private SQLConnection db;
    private ValeUtil vale;

    public ValeUtil getVale() {
        return vale;
    }

    public SQLConnection getDb() {
        return db;
    }

    public static World getWorld(){return world;}

    @Override
    public void onEnable() {
        instance = this;
        init();
        //Register events here:
        Bukkit.getPluginManager().registerEvents(new SubstanceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
        Bukkit.getPluginManager().registerEvents(new IronKeyListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new RankListener(), this);
    }

    @Override
    public void onDisable() {
    }

    private void init(){ //All startup methods here (excluding events):
        cmds = new CommandHandler(this);
        cmds.registerCommands();

        db = new SQLConnection(getConfig().getString("hostname"), getConfig().getInt("port"), getConfig().getString("database"), getConfig().getString("username"), getConfig().getString("password"));
        try {
            db.openConnection(); } catch(Exception e) {
            getLogger().log(Level.WARNING, "Could not establish connection to database, exception: "+e);
            return;
        }

        vale = new ValeUtil();

        AbstractSubstance.setup();

        BlockUtils.resetOres();
    }
}