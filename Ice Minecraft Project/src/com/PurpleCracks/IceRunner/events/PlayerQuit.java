package com.volnetiks.icerunner.events;

import com.volnetiks.icerunner.IceRunner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/* Date: 21/10/2018 For Commande By Volnetiks */
public class PlayerQuit implements Listener {

    private IceRunner iceRunner;

    public PlayerQuit(IceRunner iceRunner) {
        this.iceRunner = iceRunner;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(iceRunner.getRedTeam().contains(player)) {
            iceRunner.getRedTeam().remove(player);
        } else {
            iceRunner.getBlueTeam().remove(player);
        }
        event.setQuitMessage(iceRunner.getConfig().getString("message.quit").replace("{player}", player.getName()));
    }

}
