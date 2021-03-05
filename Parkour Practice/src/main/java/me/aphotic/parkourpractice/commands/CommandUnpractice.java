package me.aphotic.parkourpractice.commands;
import me.aphotic.parkourpractice.ParkourPractice;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class CommandUnpractice implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player
                && ((Player) sender).hasPermission("parkourpractice.unpractice")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (ParkourPractice.practicing.containsKey(uuid)) {
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] You have now exited practice mode.");
                player.teleport(ParkourPractice.practicing.remove(uuid));
                player.setVelocity(new Vector(0, 0, 0));
            }
        }
        return false;
    }
}