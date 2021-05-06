package net.luckynetwork.luckywarps;

import net.luckynetwork.luckywarps.commands.WarpCommand;
import net.luckynetwork.luckywarps.listeners.PlayerListener;
import net.luckynetwork.luckywarps.managers.WarmupManager;
import net.luckynetwork.luckywarps.managers.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LuckyWarps extends JavaPlugin {

    private WarpManager warpManager;
    private final WarmupManager warmupManager = new WarmupManager();

    @Override
    public void onEnable(){

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        registerCommandsAndListener();
        warpManager = new WarpManager(this);

    }

    private void registerCommandsAndListener(){

        WarpCommand warpCommand = new WarpCommand(this);
        this.getCommand("warp").setExecutor(warpCommand);
        this.getCommand("warp").setTabCompleter(warpCommand);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

    }

    public WarpManager getWarpManager() { return warpManager; }
    public WarmupManager getWarmupManager() { return warmupManager; }

}
