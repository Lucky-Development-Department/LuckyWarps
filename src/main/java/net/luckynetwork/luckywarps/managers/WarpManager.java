package net.luckynetwork.luckywarps.managers;

import net.luckynetwork.luckywarps.LuckyWarps;
import net.luckynetwork.luckywarps.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {

    private final Map<String, Warp> warps = new HashMap<>();

    private final LuckyWarps plugin;
    public WarpManager(LuckyWarps plugin){
        this.plugin = plugin;
    }

    @Nullable
    public Warp getWarpByName(String name){
        return this.warps.get(name);
    }

    public List<String> getWarpListByName() {
        return new ArrayList<>(this.warps.keySet());
    }

    public void createWarp(String warpName, Location location){
        this.warps.put(warpName, new Warp(warpName, location, "luckywarps.access." + warpName));
    }

    public void deleteWarpByName(String warpName){
        this.warps.remove(warpName);
    }

    public void clearWarp(){
        this.warps.clear();
    }

    public void serializeLocation(Warp warp){

        FileConfiguration config = plugin.getConfig();
        Location location = warp.getLocation().clone();

        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        double yaw = location.getYaw();
        double pitch = location.getPitch();

        config.set("warps." + warp.getName() + ".world", world);
        config.set("warps." + warp.getName() + ".x", x);
        config.set("warps." + warp.getName() + ".y", y);
        config.set("warps." + warp.getName() + ".z", z);
        config.set("warps." + warp.getName() + ".yaw", yaw);
        config.set("warps." + warp.getName() + ".pitch", pitch);

    }

    public void saveWarps(){
        for(String warpName : this.warps.keySet()){
            this.serializeLocation(this.warps.get(warpName));
        }

        plugin.saveConfig();
    }

    public void loadWarps(){

        FileConfiguration config = plugin.getConfig();
        if(!config.isConfigurationSection("warps")) return;

        System.out.println("[LuckyWarps] Loading all saved warps...");
        for(String warpName : config.getConfigurationSection("warps").getKeys(false)){

            World world = Bukkit.getWorld(config.getString("warps." + warpName + ".world"));
            double x = config.getDouble("warps." + warpName + ".x");
            double y = config.getDouble("warps." + warpName + ".y");
            double z = config.getDouble("warps." + warpName + ".z");
            float yaw = (float) config.getDouble("warps." + warpName + ".yaw");
            float pitch = (float) config.getDouble("warps." + warpName + ".pitch");

            Location location = new Location(world, x, y, z, yaw, pitch);

            String permission = "luckywarps.access." + warpName;

            this.warps.put(warpName, new Warp(warpName, location, permission));
            System.out.println("[LuckyWarps] Loaded Warp: " + warpName);

        }

        System.out.println("[LuckyWarps] Successfully loaded all saved warps!");

    }

}
