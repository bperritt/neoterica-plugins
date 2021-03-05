package me.aphotic.savestate.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;

public class GsonManagement {

    public static Gson getGson() {

        GsonBuilder gb = new GsonBuilder();

        // Config
        gb.enableComplexMapKeySerialization();
        gb.serializeNulls();
        gb.setPrettyPrinting(); // :)

        // Handlers
        gb.registerTypeAdapter(Location.class, new LocationAdapter());

        return gb.create();

    }
}
