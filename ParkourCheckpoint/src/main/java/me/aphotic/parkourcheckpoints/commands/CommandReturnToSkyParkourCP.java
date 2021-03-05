package me.aphotic.parkourcheckpoints.commands;

import me.aphotic.parkourcheckpoints.ParkourCheckpoints;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandReturnToSkyParkourCP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player
                && ((Player) sender).hasPermission("parkourcheckpoints.returntoskyparkourcp")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (ParkourCheckpoints.checkpointList.containsKey(uuid)) {
                player.teleport(ParkourCheckpoints.checkpointList.get(uuid));
            } else {
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] No checkpoint to return to.");
            }
        }
        return true;
    }
}
