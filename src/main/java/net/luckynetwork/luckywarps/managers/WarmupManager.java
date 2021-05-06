package net.luckynetwork.luckywarps.managers;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class WarmupManager {

    private final Set<UUID> players = new HashSet<>();

    public boolean isPlayerOnWarmup(UUID uuid){
        return this.players.contains(uuid);
    }

    public void addPlayerToWarmup(UUID uuid){
        this.players.add(uuid);
    }

    public void removePlayerFromWarmup(UUID uuid){
        this.players.remove(uuid);
    }

}
