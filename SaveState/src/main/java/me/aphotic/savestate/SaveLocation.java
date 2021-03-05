package me.aphotic.savestate;

import org.bukkit.Location;

// SaveLocation is a Bukkit Location bundled with a player's description of that location
public class SaveLocation {
    String desc;
    Location loc;

    public SaveLocation(String d, Location l) {
        this.desc = d;
        this.loc = l;
    }

    public String getDesc() {
        return this.desc;
    }

    public Location getLoc() {
        return this.loc;
    }
}
