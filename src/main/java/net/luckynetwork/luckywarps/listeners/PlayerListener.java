package net.luckynetwork.luckywarps.listeners;

import net.luckynetwork.luckywarps.LuckyWarps;
import net.luckynetwork.luckywarps.managers.WarmupManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private final LuckyWarps plugin;
    public PlayerListener(LuckyWarps plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        WarmupManager warmupManager = plugin.getWarmupManager();
        Player player = event.getPlayer();

        if(!warmupManager.isPlayerOnWarmup(player.getUniqueId())) return;

        Location to = event.getTo();
        Location from = event.getFrom();

        if(to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ()){
            warmupManager.removePlayerFromWarmup(player.getUniqueId());
        }

    }

}
