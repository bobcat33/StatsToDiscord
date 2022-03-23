package com.github.bobcat33.StatsToDiscord.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommandTabCompleter implements TabCompleter {
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final List<String> list = new ArrayList<String>();
        if (args.length == 1) {
            list.add("enable");
            list.add("disable");
            list.add("reload");
            list.add("restart");
            final List<String> suggestions = new ArrayList<String>();
            for (String add : list)
                if (add.toLowerCase().startsWith(args[0].toLowerCase()))
                    suggestions.add(add);
            return suggestions;
        }

        return null;
    }
}
