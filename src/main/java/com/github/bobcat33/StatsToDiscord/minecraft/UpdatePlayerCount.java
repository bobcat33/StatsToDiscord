package com.github.bobcat33.StatsToDiscord.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdatePlayerCount extends BukkitRunnable {

    private Plugin plugin = Bukkit.getPluginManager().getPlugin(Main.pluginName);

    public void start() {
        this.runTaskTimerAsynchronously(plugin, 0, 100);
    }

    @Override
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Main.setNumberPlayers(Bukkit.getOnlinePlayers().size());
            }
        }.runTask(plugin);
    }

    public void stop() {
        this.cancel();
    }
}
