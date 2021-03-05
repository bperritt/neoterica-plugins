package me.aphotic.parkourcheckpoints;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.aphotic.parkourcheckpoints.commands.CommandDeleteSkyParkourCP;
import me.aphotic.parkourcheckpoints.commands.CommandReturnToSkyParkourCP;
import me.aphotic.parkourcheckpoints.gson.GsonManagement;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class ParkourCheckpoints extends JavaPlugin implements Listener {
    public static HashMap<UUID, Location> checkpointList = new HashMap<UUID, Location>();

    @Override
    public void onEnable() {
        getCommand("returntoskyparkourcp").setExecutor(new CommandReturnToSkyParkourCP());
        getCommand("deleteskyparkourcp").setExecutor(new CommandDeleteSkyParkourCP());
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            load();
        });
    }

    @Override
    public void onDisable() {
        save();
    }

    public void save() {

        // Serialize the data itself.
        Gson gson = GsonManagement.getGson();
        String data = gson.toJson(this.checkpointList);
        File dir = new File(this.getDataFolder(), "/CzechpointData/");
        File f = new File(dir, "CheckpointList.json");

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

        Gson gson = GsonManagement.getGson();
        JsonParser parser = new JsonParser();
        File dir = new File(this.getDataFolder(), "/CzechpointData/");
        File f = new File(dir, "CheckpointList");

        try {
            if (!f.exists()) return;
            FileReader fr = new FileReader(f);
            JsonObject list = parser.parse(fr).getAsJsonObject();

                    try {
                        Type type = new TypeToken<HashMap<UUID, Location>>(){}.getType();
                        checkpointList = gson.fromJson(list, type);
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



    @EventHandler
    public void onPressurePlate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location loc = player.getLocation();
        if (player.getWorld().getName().equals("SkyParkour")
        && event.getAction().equals(Action.PHYSICAL)
        && event.getClickedBlock().getType() == Material.IRON_PLATE
        && player.hasPermission("parkourcheckpoints.set")) {
            if (!checkpointList.containsKey(uuid)
                    || Math.abs(loc.getX() - checkpointList.get(uuid).getX()) > 1
                    || Math.abs(loc.getZ() - checkpointList.get(uuid).getZ()) > 1) {
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] Checkpoint Set!");
            }
            if (checkpointList.containsKey(uuid)) {
                checkpointList.replace(uuid, loc);
            } else {
                checkpointList.put(uuid, loc);

            }
        }
    }
}
