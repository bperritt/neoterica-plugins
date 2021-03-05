package me.aphotic.parkourpractice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.aphotic.parkourpractice.commands.CommandPractice;
import me.aphotic.parkourpractice.commands.CommandReturnToCP;
import me.aphotic.parkourpractice.commands.CommandUnpractice;
import me.aphotic.parkourpractice.gson.GsonManagement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class ParkourPractice extends JavaPlugin implements Listener {
    public static HashMap<UUID, Location> practicing = new HashMap<UUID, Location>();
    private File f = new File(this.getDataFolder(), "practiceData.json");

    @Override
    public void onEnable() {
        // Register commands and events
        getCommand("practice").setExecutor(new CommandPractice());
        getCommand("unpractice").setExecutor(new CommandUnpractice());
        getCommand("returntocp").setExecutor(new CommandReturnToCP());
        // Once server is fully loaded load player data from file
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            load();
        });
    }

    @Override
    public void onDisable() {
        //Save player data to file
        save();
    }

    public void save() {

        // Serialize the data itself.
        Gson gson = GsonManagement.getGson();
        String data = gson.toJson(this.practicing);

        // Write it to the file.
        try {
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(data);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void load() {

        // Serialize the data itself
        Gson gson = GsonManagement.getGson();
        JsonParser parser = new JsonParser();

        // try converting a file to a JsonObject if it exists
        try {

            if (!f.exists()) return;
            FileReader fr = new FileReader(f);
            JsonObject list = parser.parse(fr).getAsJsonObject();

            // try reading the JsonObject to a Map
            try {
                Type type = new TypeToken<HashMap<UUID, Location>>() {
                }.getType();
                practicing = gson.fromJson(list, type);
            } catch (Exception e) {
                System.out.println("Found malformed entry in location data file");
            }


        } catch (Exception e) {
            System.out.println("Something went wrong loading data! (" + e.getMessage() + ")");
        }

        //
       try {
            FileWriter fw = new FileWriter(f);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}