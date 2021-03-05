package me.aphotic.axiscompass;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.aphotic.axiscompass.commands.CommandSetCompass;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public final class AxisCompass extends JavaPlugin implements Listener {
    //public static HashMap<UUID, Boolean> compassList = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("setcompass").setExecutor(new CommandSetCompass());
        //load();
    }
/*
    @Override
    public void onDisable() {
        //save();
    }

    public void save() {

        // Serialize the data itself.
        Gson gson = new Gson();
        String data = gson.toJson(this.compassList);
        File f = new File(this.getDataFolder(), "CheckpointList");

        // Write it to the file.
        try {
            f.getParentFile().mkdirs();
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(data);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void load() {

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        File f = new File(this.getDataFolder(), "CheckpointList");

        try {
            if (!f.exists()) return;
            FileReader fr = new FileReader(f);
            JsonObject list = parser.parse(fr).getAsJsonObject();

            try {
                Type type = new TypeToken<HashMap<UUID, Boolean>>(){}.getType();
                compassList = gson.fromJson(list, type);
            } catch (Exception e) {
                System.out.println("Found malformed entry in location data file");
            }


        } catch (Exception e) {
            System.out.println("Something went wrong loading data! (" + e.getMessage() + ")");
        }

        try {
            FileWriter fw = new FileWriter(f);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        /*
        UUID uuid = player.getUniqueId();
        if(compassList.containsKey(uuid)
        && !compassList.get(uuid)){
            loc.setX(12550820);
        }
        else if (compassList.containsKey(uuid)){
            loc.setZ(12550820);
        } else {
            loc.setZ(125508020);
            compassList.put(uuid, true);
        } */
        loc.setZ(12550820);
        player.setCompassTarget(loc);
    }

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        /*
        UUID uuid = player.getUniqueId();
        if(compassList.containsKey(uuid)
                && !compassList.get(uuid)){
            loc.setX(12550820);
        }
        else if (compassList.containsKey(uuid)){
            loc.setZ(12550820);
        } else {
            loc.setZ(125508020);
            compassList.put(uuid, true);
        } */
        loc.setZ(-125508020);
        player.setCompassTarget(loc);
    }
}
