package com.volnetiks.icerunner.utils;

import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/* Date: 23/10/2018 For Commande By Volnetiks */
public class Trail {

    private EnumParticle e;

    public Trail(EnumParticle e) {
        this.e = e;
    }

    private ArrayList<Arrow> arrows = new ArrayList<>();

    public void tick() {
        for (Arrow a : arrows) {
            if (a.isOnGround() || a.isDead() || a == null) {
                arrows.remove(a);
                return;
            } else {
                particle(a.getLocation());
            }
        }
    }

    private void particle(Location loc) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(e, true, (float) loc.getX(),
                (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 15, null);
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void addArrow(Arrow a) {
        arrows.add(a);
    }
}
