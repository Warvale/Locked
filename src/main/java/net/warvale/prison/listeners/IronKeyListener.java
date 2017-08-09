package net.warvale.prison.listeners;

import net.warvale.prison.Prison;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class IronKeyListener implements Listener {

    private Prison plugin;

    public IronKeyListener(Prison plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Action act = e.getAction();
        if (act.equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getItem().getType().getData().getName() == "Guard Entry Key") return;
        if (!(e.getClickedBlock() instanceof Openable)) return;
        Openable ope = (Openable) e.getClickedBlock();
        ope.setOpen(!ope.isOpen());
    }

}
