package me.aphotic.savestate.commands;

import me.aphotic.savestate.SaveLocation;
import me.aphotic.savestate.SaveState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class CommandSave implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If the player has permission to save, make a list of their saves or get their existing one
        if(sender instanceof Player
          && sender.hasPermission("savestate.use")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            ArrayList<SaveLocation> savestates = new ArrayList<>();

            // If the player already has a list of save states save to the existing one
            if(SaveState.savestateList.containsKey(uuid)) {
                savestates = SaveState.savestateList.remove(uuid);
            }

            // Make sure the player doesn't already have too many saves, or has permission to save more
            if (savestates.size() == 0
                    || (player.hasPermission("savestate.unlimited")
                    && savestates.size() < 50)) {

                // Create a save with player input description or default to "unnamed save"
                if (args.length > 0) {
                    savestates.add(new SaveLocation(String.join(" ", args), player.getLocation()));
                } else {
                    savestates.add(new SaveLocation("Unnamed Save", player.getLocation()));
                }

                // Put the list back in the map and let the player know their location has been saved
                SaveState.savestateList.put(uuid, savestates);
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] Location Saved!");

                // Spawn player so they can't abuse the save states
                player.teleport(player.getWorld().getSpawnLocation());

                // If the player cannot create more saves, let them know
            } else {
                SaveState.savestateList.put(uuid, savestates);
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] Cannot create any additional save states.");
            }

            // If the player doesn't have permission to save, let them know
        } else {
            if(sender instanceof Player) {
                sender.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] Cannot do this right now.");
            }
        }
        return false;
    }
}
