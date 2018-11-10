package com.volnetiks.icerunner.utils;

import com.volnetiks.icerunner.IceRunner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

/* Date: 19/10/2018 For Commande By Volnetiks */
public class WaitingTask extends BukkitRunnable {

    int timer;
    private IceRunner iceRunner;

    public WaitingTask(int time, IceRunner iceRunner) {
        this.timer = time;
        this.iceRunner = iceRunner;
    }

    @Override
    public void run() {

        Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(timer));

        if(timer == 60 || timer == 30 || timer == 15 || timer == 10 || timer <= 5 && timer != 0) {
            Bukkit.broadcastMessage(iceRunner.getConfig().getString("message.starting").replace("{time}", String.valueOf(timer)));
        }

        if(timer == 0) {
            Bukkit.broadcastMessage(iceRunner.getConfig().getString("message.start"));
            iceRunner.setState(State.PLAYING);
            GameCycle gameCycle = new GameCycle(iceRunner);
            gameCycle.runTaskTimer(iceRunner, 0, 20);
            cancel();
            teleportPlayer();
        }
        timer--;
    }

    private void teleportPlayer() {
        Location spawnTeam1 = new Location(Bukkit.getWorld(iceRunner.getConfig().getString("location.spawn.world")), iceRunner.getConfig().getInt("location.spawnTeam1.x"), iceRunner.getConfig().getInt("location.spawnTeam1.y"), iceRunner.getConfig().getInt("location.spawnTeam1.z"));
        Location spawnTeam2 = new Location(Bukkit.getWorld(iceRunner.getConfig().getString("location.spawn.world")), iceRunner.getConfig().getInt("location.spawnTeam2.x"), iceRunner.getConfig().getInt("location.spawnTeam2.y"), iceRunner.getConfig().getInt("location.spawnTeam2.z"));
        iceRunner.getRedTeam().forEach(player -> player.teleport(spawnTeam1));
        iceRunner.getBlueTeam().forEach(player -> player.teleport(spawnTeam2));
        Bukkit.getOnlinePlayers().forEach(this::setGameInventory);
    }

    private void setGameInventory(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        bow.setItemMeta(bowMeta);
        ItemStack snowBall = new ItemStack(Material.SNOW_BALL, 8);
        ItemStack arrow = new ItemStack(Material.ARROW, 4);
        player.getInventory().clear();
        player.getInventory().setItem(0, bow);
        player.getInventory().setItem(1, snowBall);
        player.getInventory().setItem(2, arrow);
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
