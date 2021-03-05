package me.aphotic.axiscompass.commands;

import me.aphotic.axiscompass.AxisCompass;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandSetCompass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player
        && sender.hasPermission("axiscompass.setcompass")) {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            UUID uuid = player.getUniqueId();
            String axis = args[0];
            switch(axis) {
                case "z":
                    loc.setZ(12550820);
                    player.setCompassTarget(loc);
                    //AxisCompass.compassList.put(uuid, true);
                    player.sendMessage(ChatColor.GRAY +
                            "[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" + ChatColor.GRAY +
                            "] Your compass has been set to Z facing.");
                    break;
                case "x":
                    loc.setX(12550820);
                    player.setCompassTarget(loc);
                    //AxisCompass.compassList.put(uuid, false);
                    player.sendMessage(ChatColor.GRAY +
                            "[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" + ChatColor.GRAY +
                            "] Your compass has been set to X facing.");
                    break;
                default:
                    player.sendMessage(ChatColor.GRAY +
                            "[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" + ChatColor.GRAY +
                            "] Invalid arguments.");
                    break;
            }
        }
        return false;
    }
}
