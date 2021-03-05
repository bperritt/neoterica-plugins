package me.aphotic.savestate.events;

import me.aphotic.savestate.SaveState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventLogin implements Listener {

    @EventHandler
    public void LoginEvent(PlayerLoginEvent e) {
        // Load a player's save data on login if it hasn't been loaded already
        if(!SaveState.savestateList.containsKey(e.getPlayer().getUniqueId())) {
            String filename = e.getPlayer().getUniqueId().toString() + ".json";
            SaveState.load(filename, e.getPlayer().getUniqueId());
        }
    }
}
