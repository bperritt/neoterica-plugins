package me.aphotic.parkourcheckpoints.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.aphotic.parkourcheckpoints.ParkourCheckpoints;
import me.aphotic.parkourcheckpoints.gson.GsonManagement;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.UUID;

public class LoadData implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        load(new File("checkpointData.json"));
        sender.sendMessage("nigga");
        return false;
    }

    public void load(File f) {

        Gson gson = GsonManagement.getGson();
        JsonParser parser = new JsonParser();

        try {

            if (!f.exists()) return;

            FileReader fr = new FileReader(f);
            JsonArray list = parser.parse(fr).getAsJsonArray();

            if (list.isJsonObject()) {

                try {
                    ParkourCheckpoints.checkpointList = gson.fromJson
                            (list, new TypeToken<HashMap<UUID, Location>>() {}.getType());
                } catch (Exception e) {
                    System.out.println("Found malformed entry in location data file");
                }

            } else {
                System.out.println("Found invalid entry in location data file");
            }

        } catch (Exception e) {
            System.out.println("Something went wrong loading data! (" + e.getMessage() + ")");
        }

    }
}
