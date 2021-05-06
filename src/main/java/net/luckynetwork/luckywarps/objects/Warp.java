package net.luckynetwork.luckywarps.objects;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Warp {

    private final String name;
    private final Location location;
    private final String permission;

    public Warp(String name, Location location, String permission) {
        this.name = name;
        this.location = location;
        this.permission = permission;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Location getLocation() {
        return location;
    }

    @NotNull
    public String getPermission() {
        return permission;
    }

}
