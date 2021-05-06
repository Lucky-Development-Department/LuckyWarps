package net.luckynetwork.luckywarps.commands;

import net.luckynetwork.luckywarps.LuckyWarps;
import net.luckynetwork.luckywarps.managers.WarmupManager;
import net.luckynetwork.luckywarps.managers.WarpManager;
import net.luckynetwork.luckywarps.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    private final LuckyWarps plugin;
    public WarpCommand(LuckyWarps plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        WarpManager warpManager = plugin.getWarpManager();
        WarmupManager warmupManager = plugin.getWarmupManager();

        if(sender instanceof Player){
            if(args.length < 1){
                sender.sendMessage(this.color("&cUsage: /warp <warpName>"));
                return true;
            }

            String warpName = args[0];

            Warp warp = warpManager.getWarpByName(warpName);
            if(warp == null){
                sender.sendMessage(this.color("&cThere is no warp with that name!"));
                return true;
            }

            Player player = (Player) sender;

            if(warmupManager.isPlayerOnWarmup(player.getUniqueId())){
                player.sendMessage(this.color("&cYou are already teleporting, please wait!"));
                return true;
            }

            String title = "&e&lTeleporting...";
            String subTitle = "&7&oYou will be teleported in &f&o&n ";

            new BukkitRunnable(){
                int counter = 1;
                @Override
                public void run(){

                    if(!warmupManager.isPlayerOnWarmup(player.getUniqueId())){
                        player.sendMessage(color("&cDon't move! Your teleportation has been cancelled!"));
                        this.cancel();
                        return;
                    }

                    if(counter == 5){
                        player.teleport(warp.getLocation());
                        this.cancel();
                        return;
                    }

                    counter++;
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
                            player.sendTitle(color(title), color(subTitle + counter), 0, 30, 0));

                }

            }.runTaskTimer(plugin, 0L, 20L);

        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        WarpManager warpManager = plugin.getWarpManager();

        if(args.length == 1){
            return warpManager.getWarpListByName();
        }

        if(args.length == 2){
            List<String> suggestions = new ArrayList<>();
            for(Player online : Bukkit.getOnlinePlayers()){
                suggestions.add(online.getName());
            }
            return suggestions;
        }

        return null;
    }

    private String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
