package me.aphotic.parkourpractice.commands;
import me.aphotic.parkourpractice.ParkourPractice;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandReturnToCP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player
            && ((Player) sender).hasPermission("parkourpractice.returntocp")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (ParkourPractice.practicing.containsKey(uuid)) {
                player.teleport(ParkourPractice.practicing.get(uuid));
            } else {
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] You must be in practice mode to do that.");
            }
        }
        return false;
    }
}
