package me.aphotic.parkourpractice.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

public class LocationAdapter extends TypeAdapter<Location> {
    @Override
    public void write(JsonWriter out, Location loc) throws IOException {
        out.beginObject();
        out.name("world").value(loc.getWorld().getName());
        out.name("x").value(loc.getX());
        out.name("y").value(loc.getY());
        out.name("z").value(loc.getZ());
        out.name("yaw").value(loc.getYaw());
        out.name("pitch").value(loc.getPitch());
        out.endObject();
    }

    @Override
    public Location read(JsonReader in) throws IOException {
        final Location loc = new Location(Bukkit.getWorld("main"), 0, 0, 0);
        in.beginObject();
        while(in.hasNext()) {
            switch (in.nextName()) {
                case "world":
                    loc.setWorld(Bukkit.getWorld(in.nextString()));
                    break;
                case "x":
                    loc.setX(in.nextDouble());
                    break;
                case "y":
                    loc.setY(in.nextDouble());
                    break;
                case "z":
                    loc.setZ(in.nextDouble());
                    break;
                case "yaw":
                    loc.setYaw((float) in.nextDouble());
                    break;
                case "pitch":
                    loc.setPitch((float) in.nextDouble());
                    break;
            }
        }
        in.endObject();
        return loc;
    }
}
