package com.volnetiks.icerunner;

import com.volnetiks.icerunner.events.PlayerJoin;
import com.volnetiks.icerunner.events.PlayerQuit;
import com.volnetiks.icerunner.events.ProjectileLaunch;
import com.volnetiks.icerunner.utils.Cuboid;
import com.volnetiks.icerunner.utils.State;
import com.volnetiks.icerunner.utils.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

/* Date: 19/10/2018 For Commande By Volnetiks */
public class IceRunner extends JavaPlugin {

    int rougeMultiplier = 1;
    int bleuMultiplier = 1;
    boolean hasZoneRouge = false, hasZoneBlue = false;

    BukkitTask runnable;
    Scoreboard s;
    List<Player> redTeam;
    List<Player> blueTeam;
    List<Location> spawns = new ArrayList<>();
    State state;
    Team redTeamScoreboard, blueTeamScoreboard;
    private Map<UUID, Trail> players = new HashMap<>();
    static IceRunner iceRunner;
    Cuboid zone1;
    Cuboid zone2;
    Cuboid zone3;

    @Override
    public void onEnable() {
        zone1 = new Cuboid(new Location(Bukkit.getWorld("world"), getConfig().getDouble("location.zone1.x1"), getConfig().getDouble("location.zone1.y1"), getConfig().getDouble("location.zone1.z1")), new Location(Bukkit.getWorld("world"), getConfig().getDouble("location.zone1.x2"),getConfig().getDouble("location.zone1.y2"), getConfig().getDouble("location.zone1.z2")));;
        zone2 = new Cuboid(new Location(Bukkit.getWorld("world"), getConfig().getDouble("location.zone2.x1"), getConfig().getDouble("location.zone2.y1"), getConfig().getDouble("location.zone2.z1")), new Location(Bukkit.getWorld("world"), getConfig().getDouble("location.zone2.x2"),getConfig().getDouble("location.zone2.y2"), getConfig().getDouble("location.zone2.z2")));
        zone3 = new Cuboid(new Location(Bukkit.getWorld("world"), getConfig().getDouble("location.zone3.x1"), getConfig().getDouble("location.zone3.y1"), getConfig().getDouble("location.zone3.z1")), new Location(Bukkit.getWorld("world"), getConfig().getDouble("location.zone3.x2"),getConfig().getDouble("location.zone3.y2"), getConfig().getDouble("location.zone3.z2")));
        iceRunner = this;
        redTeam = new ArrayList<>();
        blueTeam = new ArrayList<>();
        s = Bukkit.getScoreboardManager().getMainScoreboard();
        state = State.WAITING;
        PluginManager pm = getServer().getPluginManager();
        System.out.println("The Plugin Is Started!");
        registerEvents(pm);
        registerCommands();
        saveDefaultConfig();
        registerTeam();
         runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Trail t : players.values()) {
                    t.tick();
                }

            }
        }.runTaskTimerAsynchronously(this, 0, 1);
    }



    private void registerTeam() {
        if(s.getTeam("teamBlue") != null)
            s.getTeam("teamBlue").unregister();
        if(s.getTeam("teamRed") != null) {
            s.getTeam("teamRed").unregister();
        }

        redTeamScoreboard = s.registerNewTeam("teamRed");
        blueTeamScoreboard = s.registerNewTeam("teamBlue");
        redTeamScoreboard.setPrefix(ChatColor.RED + "");
        blueTeamScoreboard.setPrefix(ChatColor.BLUE + "");
    }

    private void registerEvents(PluginManager pm) {
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new ProjectileLaunch(), this);
    }

    private void registerCommands() {

    }

    public List<Player> getRedTeam() {
        return redTeam;
    }

    public List<Player> getBlueTeam() {
        return blueTeam;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static IceRunner getInstance() {
        return iceRunner;
    }

    public Trail getTrail(Player p) {
        return players.get(p.getUniqueId());
    }

    public Map<UUID, Trail> getPlayers() {
        return players;
    }

    public Cuboid getZone1() {
        return zone1;
    }

    public Cuboid getZone2() {
        return zone2;
    }

    public Cuboid getZone3() {
        return zone3;
    }
    public int getRougeMultiplier() {
        return rougeMultiplier;
    }

    public void setRougeMultiplier(int rougeMultiplier) {
        this.rougeMultiplier = rougeMultiplier;
    }

    public int getBleuMultiplier() {
        return bleuMultiplier;
    }

    public void setBleuMultiplier(int bleuMultiplier) {
        this.bleuMultiplier = bleuMultiplier;
    }

    public boolean isHasZoneRouge() {
        return hasZoneRouge;
    }

    public void setHasZoneRouge(boolean hasZoneRouge) {
        this.hasZoneRouge = hasZoneRouge;
    }

    public boolean isHasZoneBlue() {
        return hasZoneBlue;
    }

    public void setHasZoneBlue(boolean hasZoneBlue) {
        this.hasZoneBlue = hasZoneBlue;
    }

    public BukkitTask getRunnable() {
        return runnable;
    }
}
