package com.github.bobcat33.StatsToDiscord.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin(Main.pluginName);

    // Minecraft
    public static String serverName;

    // Discord
    public static String token;
    public static String statusMessage;


    public static void load() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        // Add the default values
        config.addDefault("initially-enabled", true);
        config.addDefault("server-name", "Minecraft Server Name");
        config.addDefault("status-message", "with <player-count> players on <server-name>");
        config.addDefault("bot-token", "token");

        // Save
        config.options().copyDefaults(true);
        plugin.saveConfig();

        // Load in new values
        Settings.serverName = config.getString("server-name");
        Settings.statusMessage = config.getString("status-message");
        Settings.token = config.getString("bot-token");

        // Set the enabled state
        Main.enabled = config.getBoolean("initially-enabled");
    }
}
