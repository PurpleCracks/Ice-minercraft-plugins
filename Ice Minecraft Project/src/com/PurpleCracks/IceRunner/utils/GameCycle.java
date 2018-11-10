package com.volnetiks.icerunner.utils;

import com.volnetiks.icerunner.IceRunner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/* Date: 21/10/2018 For Commande By Volnetiks */
public class GameCycle extends BukkitRunnable {

    private IceRunner iceRunner;

    public GameCycle(IceRunner iceRunner) {
        this.iceRunner = iceRunner;
    }

    int redPoints = 360;
    int bluePoints = 360;
    int sec = 0;

    @Override
    public void run() {
        if(iceRunner.isHasZoneBlue()) {
            bluePoints--;
            System.out.println("Moins 1 points");
        }
        if(iceRunner.isHasZoneRouge()) {
            redPoints--;
            System.out.println("Moins 1 points");
        }
        if(bluePoints == 0) {
            winner(iceRunner.getBlueTeam());
            cancel();
        }
        if(redPoints == 0) {
            winner(iceRunner.getRedTeam());
            cancel();
        }

        checkPlayerZone();

        sec++;

        if(sec == 5) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getInventory().addItem(new ItemStack(Material.ARROW));
                player.getInventory().addItem(new ItemStack(Material.SNOW_BALL));
            }
        }

    }

    private void checkPlayerZone() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(iceRunner.getZone1().contains(player.getLocation()) || iceRunner.getZone2().contains(player.getLocation()) || iceRunner.getZone3().contains(player.getLocation())) {
                System.out.println("il est dans une zone");
//                if(getBlueTeam().contains(player)) {
//
//                    setHasZoneBlue(true);
//                } else {
//                    setHasZoneRouge(true);
//                }
            }
        }
    }

    private void winner(List<Player> team) {
        iceRunner.getRunnable().cancel();
        if(team == iceRunner.getBlueTeam()) {
            Bukkit.broadcastMessage(iceRunner.getConfig().getString("message.win").replace("{team}", "bleu"));
        } else {
            Bukkit.broadcastMessage(iceRunner.getConfig().getString("message.win").replace("{team}", "rouge"));
        }
    }
}
