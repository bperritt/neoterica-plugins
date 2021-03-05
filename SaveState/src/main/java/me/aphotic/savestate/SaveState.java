package me.aphotic.savestate;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.aphotic.savestate.commands.CommandLoad;
import me.aphotic.savestate.commands.CommandSave;
import me.aphotic.savestate.events.EventClick;
import me.aphotic.savestate.events.EventLogin;
import me.aphotic.savestate.gson.GsonManagement;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public final class SaveState extends JavaPlugin {
    public static HashMap<UUID, ArrayList<SaveLocation>> savestateList = new HashMap<>();

    @Override
    public void onEnable() {
        // Startup logic for commands and events
        getCommand("save").setExecutor(new CommandSave());
        getCommand("load").setExecutor(new CommandLoad());
        getServer().getPluginManager().registerEvents(new EventClick(), this);
        getServer().getPluginManager().registerEvents(new EventLogin(), this);
    }

    @Override
    public void onDisable() {

        // Save data to file on server disable
        save();
    }

    public void save() {

        // Serialize the data itself.
        Gson gson = GsonManagement.getGson();
        File dir = new File(this.getDataFolder(), "/SaveStates/");
        dir.mkdirs();
        // Write it to the file.
        try {
            for(Map.Entry<UUID, ArrayList<SaveLocation>> save : savestateList.entrySet()) {
                File f = new File(dir, save.getKey().toString() + ".json");
                ArrayList<SaveLocation> saves = save.getValue();
                String data = gson.toJson(saves);
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                fw.write(data);
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void load(String filename, UUID uuid) {

        // Serialize the data itself
        Gson gson = GsonManagement.getGson();
        JsonParser parser = new JsonParser();
        // Define directories
        File dir = new File(SaveState.getPlugin(SaveState.class).getDataFolder(), "/SaveStates/");
        File f = new File(dir, filename);
        // try reading from the file
        try {
            if (!f.exists()) return;
            FileReader fr = new FileReader(f);

            // try parsing file into a Json Array and putting it into the savestate Map
            try {
                JsonArray list = parser.parse(fr).getAsJsonArray();
                Type type = new TypeToken<ArrayList<SaveLocation>>() {}.getType();
                ArrayList<SaveLocation> savestates = gson.fromJson(list, type);
                savestateList.put(uuid, savestates);
                } catch (Exception e) {
                    System.out.println("Found malformed entry in savestate data file");
                }
        } catch (Exception e) {
            System.out.println("Something went wrong loading data! (" + e.getMessage() + ")");
        }

        // Wipe player's data file as to not repeat savestates on save
        try {
            FileWriter fw = new FileWriter(f);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
