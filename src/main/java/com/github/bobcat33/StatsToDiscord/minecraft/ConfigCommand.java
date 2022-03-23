package com.github.bobcat33.StatsToDiscord.minecraft;

import com.github.bobcat33.StatsToDiscord.discord.DiscordBot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

public class ConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("discordstats")) {
            if (args.length == 0) {
                logUsage(sender, command);
                return true;
            }

            if (args[0].equalsIgnoreCase("enable")) {
                if (Main.enabled) {
                    message(sender, ChatColor.RED + Main.pluginName + " already enabled");
                    return true;
                }
                if (!Settings.token.equalsIgnoreCase("token")) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            try {
                                Main.bot = new DiscordBot();
                                Main.loggedIn = true;
                                Main.counter = new UpdatePlayerCount();
                                Main.counter.start();
                                Main.enabled = true;
                                message(sender, ChatColor.GREEN + Main.pluginName + " enabled");
                            } catch (LoginException | IllegalStateException e) {
                                Main.log("Error occurred while logging in with discord bot, token may need updated.");
                                message(sender, ChatColor.RED + "Error occurred while logging in with discord bot, token may need updated.");
                            }
                        }
                    }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin(Main.pluginName));
                }
                else {
                    Main.log("Discord bot token has not been set, please change the token in the config file.");
                    message(sender, ChatColor.RED + "Discord bot token has not been set, please change the token in the config file.");
                }

                return true;
            }


            if (args[0].equalsIgnoreCase("disable")) {
                if (!Main.enabled) {
                    message(sender, ChatColor.RED + Main.pluginName + " already disabled");
                    return true;
                }
                Main.enabled = false;
                Main.loggedIn = false;
                Main.counter.stop();
                Main.bot.getJda().shutdown();
                message(sender, ChatColor.RED + Main.pluginName + " disabled");
                return true;
            }


            if (args[0].equalsIgnoreCase("reload")) {
    			Settings.load();
                message(sender, ChatColor.GREEN + Main.pluginName + " config reloaded");
                return true;
            }


            if (args[0].equalsIgnoreCase("restart")) {
                message(sender, ChatColor.GOLD + Main.pluginName + " discord bot restarting...");
                Main.enabled = false;
                Main.loggedIn = false;
                if (Main.counter != null) Main.counter.stop();
                if (Main.bot != null) Main.bot.getJda().shutdown();
                Main.load();
                message(sender, ChatColor.GREEN + Main.pluginName + " discord bot restarted.");
                return true;
            }

            logUsage(sender, command);
            return true;
        }
        return false;
    }

    private static void logUsage(CommandSender sender, Command command) {
        if (!(sender instanceof Player p)) {
            System.out.println(command.getUsage());
            return;
        }
        p.sendRawMessage(ChatColor.GOLD + command.getUsage());
    }

    private static void message(CommandSender sender, String msg) {
        if (!(sender instanceof Player p)) {
            msg = ChatColor.stripColor(msg);
            System.out.println(msg);
            return;
        }
        p.sendRawMessage(msg);
    }
}
