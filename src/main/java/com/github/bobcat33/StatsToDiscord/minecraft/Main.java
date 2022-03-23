package com.github.bobcat33.StatsToDiscord.minecraft;

import com.github.bobcat33.StatsToDiscord.discord.DiscordBot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {
    public static String pluginName = "StatsToDiscord";
    public static String prefix = "[StatsToDiscord] ";

    public static boolean enabled;
    public static boolean loggedIn = false;

    public static DiscordBot bot;
    public static UpdatePlayerCount counter;

    @Override
    public void onEnable() {
        Main.load();

        this.getCommand("discordstats").setExecutor(new ConfigCommand());
        this.getCommand("discordstats").setTabCompleter(new ConfigCommandTabCompleter());
    }

    @Override
    public void onDisable() {
        if (Main.counter != null) Main.counter.stop();
    }

    public static void load() {
        Settings.load();

        if (!Main.enabled) return;

        if (!Settings.token.equalsIgnoreCase("token")) {

            new BukkitRunnable() {

                @Override
                public void run() {
                    try {
                        Main.bot = new DiscordBot();
                        loggedIn = true;
                        Main.counter = new UpdatePlayerCount();
                        Main.counter.start();
                    } catch (LoginException | IllegalStateException e) {
                        log("Error occurred while logging in with discord bot, token may need updated.");
                        enabled = false;
                    }
                }
            }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin(Main.pluginName));

        }
        else {
            log("Discord bot token has not been set, please change the token in the config file.");
            enabled = false;
        }
    }

    public static void log(String text) {
        System.out.println(Main.prefix + text);
    }

    public static void setNumberPlayers(int numPlayers) {
        if (!Main.loggedIn || !Main.enabled) return;
        Main.bot.setPlaying(Settings.statusMessage.replaceAll("<player-count>", String.valueOf(numPlayers)).replaceAll("<server-name>", Settings.serverName));
    }

}
