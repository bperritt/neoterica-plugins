package me.aphotic.savestate.events;

import me.aphotic.savestate.SaveLocation;
import me.aphotic.savestate.SaveState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.ArrayList;

public class EventClick implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {

        // Ensure clicked item is within save state inventory and isn't an empty square
        int saveNumber = e.getCurrentItem().getAmount();
        if(e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE +
                "Which state will you load?")
        && e.getCurrentItem().getAmount() > 0) {
            // remove the player's list of saves from the map
            Player p = (Player) e.getWhoClicked();
            ArrayList<SaveLocation> savestates = SaveState.savestateList.remove(p.getUniqueId());

            // close the menu and teleport the player to the specified save
            p.closeInventory();
            p.teleport(savestates.remove(saveNumber - 1).getLoc());
            p.sendMessage(ChatColor.GRAY + "[" +
                    ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                    ChatColor.GRAY + "] Your save has been loaded!");

            // put list back into map with entry removed
            SaveState.savestateList.put(p.getUniqueId(), savestates);

            // Disallow players from moving items while in the savestate window
            e.setCancelled(true);
        }
    }
}
