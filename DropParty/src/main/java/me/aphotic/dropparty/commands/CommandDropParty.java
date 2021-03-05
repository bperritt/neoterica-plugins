package me.aphotic.dropparty.commands;

import me.aphotic.dropparty.DropParty;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class CommandDropParty implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String username = args[0];
        int numdrops = Integer.parseInt(args[1]);
        for (int i = 0; i < numdrops; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(DropParty.getProvidingPlugin(DropParty.class),
                    () -> {
                        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
                        for (Player p : players) {
                            dropMoney(p, username);
                        }
                    }, 500 * i);
        }
        return false;
    }

    private void dropMoney(Player p, String sender) {
        Economy econ = DropParty.getEconomy();
        Double playerBal = econ.getBalance(p);
        Random rand = new Random();
        Double adjustedAmount = Math.sqrt(500000000 / (0.015 * (playerBal + 4000.0))) + 1800;
        int variance = rand.nextInt((int) (adjustedAmount / 3));
        int dropAmount = (int) (adjustedAmount + (adjustedAmount / 6) - variance);
        int dropNum = dropAmount / 600 + rand.nextInt(4);
        int currentDropped = 0;
        p.sendMessage(ChatColor.GRAY + "[" +
                ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Neoterica" +
                ChatColor.GRAY + "] " + ChatColor.GOLD + ChatColor.BOLD + sender +
                ChatColor.GRAY + " has started a drop party!");
        for(int i = 0; i < dropNum; i++) {
            if(currentDropped > dropAmount) {
                break;
            }
            int smallDrop = rand.nextInt((int) (1.35 * dropAmount / dropNum)) + 150;
            Bukkit.getScheduler().scheduleSyncDelayedTask(DropParty.getProvidingPlugin(DropParty.class),
                    () -> {
                        econ.depositPlayer(p, smallDrop);
                        p.sendMessage(ChatColor.GRAY + "[" +
                                ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Drop Party" +
                                ChatColor.GRAY + "] You received " + String.valueOf(smallDrop) + " dollars!");
                        }, 50 * i);
            currentDropped += smallDrop;
        }

        if(dropAmount > currentDropped) {
            int smallDrop = dropAmount - currentDropped;
            Bukkit.getScheduler().scheduleSyncDelayedTask(DropParty.getProvidingPlugin(DropParty.class),
                    () -> {
                        econ.depositPlayer(p, smallDrop);
                        p.sendMessage(ChatColor.GRAY + "[" +
                                ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Drop Party" +
                                ChatColor.GRAY + "] You received " + String.valueOf(smallDrop) + " dollars!");
                    }, 50 * dropNum);
        }
    }
}
