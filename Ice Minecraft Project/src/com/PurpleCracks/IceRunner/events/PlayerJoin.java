package com.volnetiks.icerunner.events;

import com.volnetiks.icerunner.IceRunner;
import com.volnetiks.icerunner.utils.State;
import com.volnetiks.icerunner.utils.Trail;
import com.volnetiks.icerunner.utils.WaitingTask;
import net.minecraft.server.v1_9_R2.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

/* Date: 19/10/2018 For Commande By Volnetiks */
public class PlayerJoin implements Listener {

    private IceRunner iceRunner;
    private WaitingTask timerTask;
    private Configuration config;

    public PlayerJoin(IceRunner iceRunner) {
        this.iceRunner = iceRunner;
        this.config = iceRunner.getConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawn = new Location(Bukkit.getWorld(config.getString("location.spawn.world")), config.getInt("location.spawn.x"), config.getInt("location.spawn.y"), config.getInt("location.spawn.z"));

        EnumParticle e = EnumParticle.FLAME;
        iceRunner.getPlayers().put(player.getUniqueId(), new Trail(e));
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.teleport(spawn);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.setHealth(20);
        player.setFoodLevel(20);
        if(iceRunner.getServer().getOnlinePlayers().size() == 1) {
            timerTask = new WaitingTask(60, iceRunner);
            timerTask.runTaskTimer(iceRunner, 0, 20);
            iceRunner.setState(State.STARTING);
        }

        if(iceRunner.getServer().getOnlinePlayers().size() == 1) {
            timerTask.setTimer(10);
            iceRunner.setState(State.STARTING);
        }

        if(iceRunner.getState() == State.WAITING || (iceRunner.getState() == State.STARTING && Bukkit.getOnlinePlayers().size() != 8)) {
            chooseTeam(player);
            event.setJoinMessage(iceRunner.getConfig().getString("message.join").replace("{player}", player.getName()));
        } else {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(iceRunner.getConfig().getString("message.alreadyStart"));
            event.setJoinMessage(null);
        }
    }

    private void chooseTeam(Player player) {
        int size = iceRunner.getServer().getOnlinePlayers().size();
        if(size == 1) {
            Random r = new Random(3);
            if(r.nextInt() == 1) {
                iceRunner.getRedTeam().add(player);
                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("teamRed").addPlayer(player);
            } else {
                iceRunner.getBlueTeam().add(player);
                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("teamBlue").addPlayer(player);
            }
            return;
        }
        if(iceRunner.getRedTeam().size() > iceRunner.getBlueTeam().size()) {
            iceRunner.getBlueTeam().add(player);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("teamBlue").addPlayer(player);
        } else {
            iceRunner.getRedTeam().add(player);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("teamRed").addPlayer(player);
        }
    }

}
