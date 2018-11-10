package com.volnetiks.icerunner.utils;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;

/* Date: 23/10/2018 For Commande By Volnetiks */
public class SnowLiveTimer extends BukkitRunnable {

    private Snowball snowball;

    public SnowLiveTimer(Snowball snowball) {
        this.snowball = snowball;
    }

    @Override
    public void run() {
        System.out.println("5");
        snowball.getLocation().getBlock().setType(Material.ICE);
    }
}
