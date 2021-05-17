package net.luckynetwork.luckywarps;

import net.luckynetwork.luckywarps.commands.DeleteWarpCommand;
import net.luckynetwork.luckywarps.commands.SetWarpCommand;
import net.luckynetwork.luckywarps.commands.WarpCommand;
import net.luckynetwork.luckywarps.listeners.PlayerListener;
import net.luckynetwork.luckywarps.managers.WarmupManager;
import net.luckynetwork.luckywarps.managers.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LuckyWarps extends JavaPlugin {

    private final WarpManager warpManager = new WarpManager(this);
    private final WarmupManager warmupManager = new WarmupManager();

    @Override
    public void onEnable(){

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        registerCommandsAndListener();
        Bukkit.getScheduler().runTaskLater(this, warpManager::loadWarps, 100L);

    }

    @Override
    public void onDisable(){
        warpManager.saveWarps();
    }

    private void registerCommandsAndListener(){

        WarpCommand warpCommand = new WarpCommand(this);
        this.getCommand("warp").setExecutor(warpCommand);
        this.getCommand("warp").setTabCompleter(warpCommand);

        SetWarpCommand setWarpCommand = new SetWarpCommand(this);
        this.getCommand("setwarp").setExecutor(setWarpCommand);
        this.getCommand("setwarp").setTabCompleter(warpCommand);

        DeleteWarpCommand deleteWarpCommand = new DeleteWarpCommand(this);
        this.getCommand("delwarp").setExecutor(deleteWarpCommand);
        this.getCommand("delwarp").setTabCompleter(deleteWarpCommand);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

    }

    public WarpManager getWarpManager() { return warpManager; }
    public WarmupManager getWarmupManager() { return warmupManager; }

}
