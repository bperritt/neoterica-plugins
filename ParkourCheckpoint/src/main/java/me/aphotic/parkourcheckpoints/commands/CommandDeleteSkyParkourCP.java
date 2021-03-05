package me.aphotic.parkourcheckpoints.commands;

import me.aphotic.parkourcheckpoints.ParkourCheckpoints;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandDeleteSkyParkourCP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player
                && ((Player) sender).hasPermission("parkourcheckpoints.deleteskyparkourcp")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (ParkourCheckpoints.checkpointList.containsKey(uuid)) {
                ParkourCheckpoints.checkpointList.remove(uuid);
            }
        }
        return true;
    }
}
