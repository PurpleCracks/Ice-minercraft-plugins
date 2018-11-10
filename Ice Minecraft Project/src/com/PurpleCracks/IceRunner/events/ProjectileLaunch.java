package com.volnetiks.icerunner.events;

import com.volnetiks.icerunner.IceRunner;
import com.volnetiks.icerunner.utils.SnowLiveTimer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

/* Date: 23/10/2018 For Commande By Volnetiks */
public class ProjectileLaunch implements Listener {

    private BukkitRunnable runnable;

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() != null) {
            if (e.getEntity().getShooter() instanceof Player) {
                if (e.getEntity() instanceof Arrow) {
                    Player p = (Player) e.getEntity().getShooter();
                    IceRunner.getInstance().getTrail(p).addArrow((Arrow) e.getEntity());
                }
                if(e.getEntity() instanceof Snowball) {
                    System.out.println("4");
                    runnable = new SnowLiveTimer((Snowball) e.getEntity());
                    runnable.runTaskTimer(IceRunner.getInstance(), 0, 5);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity e = event.getEntity();

        if(e instanceof Snowball) {
            System.out.println("1");
            if(((Snowball) e).getShooter() instanceof Player) {
                System.out.println("2");
                if(runnable != null) {
                    System.out.println("3");
                    runnable.cancel();
                }
            }
        }
    }

}
