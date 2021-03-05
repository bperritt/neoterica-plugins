package me.aphotic.savestate.commands;

import me.aphotic.savestate.SaveLocation;
import me.aphotic.savestate.SaveState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class CommandLoad implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // If the command sender is a player, construct a new inventory GUI for them
        if(sender instanceof Player
        && sender.hasPermission("savestate.use")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            // If the player has save states, set up the GUI
            if(SaveState.savestateList.containsKey(uuid)
            && SaveState.savestateList.get(uuid).size() > 0) {
                ArrayList<SaveLocation> savestates = SaveState.savestateList.get(uuid);
                Inventory gui = Bukkit.createInventory(player, ((int) (savestates.size() / 9) + 1) * 9,
                        ChatColor.LIGHT_PURPLE +
                                "Which state will you load?");
                ItemStack[] menu_items = new ItemStack[savestates.size()];

                // For each save state in the GUI, create an item stack to represent the save state
                for (int i = 0; i < savestates.size(); i++) {
                    ItemStack savedLocation = new ItemStack(Material.STAINED_GLASS_PANE, i + 1, (short) 15);
                    ItemMeta saveMeta = savedLocation.getItemMeta();
                    saveMeta.setDisplayName(ChatColor.AQUA + "Save state #" + (i + 1));
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(ChatColor.WHITE + savestates.get(i).getDesc());
                    saveMeta.setLore(lore);
                    savedLocation.setItemMeta(saveMeta);
                    menu_items[i] = savedLocation;
                }

                // Set the contents of the GUI and display them to the player
                gui.setContents(menu_items);
                player.openInventory(gui);

                // If the player has no save states, tell them
            } else {
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] No save states to load.");
            }

            // If the player does not have permission to load a save, tell them
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
