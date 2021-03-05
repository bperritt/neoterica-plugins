package me.aphotic.parkourpractice.commands;
import me.aphotic.parkourpractice.ParkourPractice;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class CommandPractice implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player
           && sender.hasPermission("parkourpractice.practice")) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (!ParkourPractice.practicing.containsKey(uuid)) {
                player.sendMessage(ChatColor.GRAY + "[" +
                        ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                        ChatColor.GRAY + "] You have now entered practice mode.");
                Location loc = player.getLocation();
                ParkourPractice.practicing.put(uuid, loc);
            }
        }
        return false;
    }
}
