package com.github.bobcat33.StatsToDiscord.discord;

import com.github.bobcat33.StatsToDiscord.discord.listeners.ReadyListener;
import com.github.bobcat33.StatsToDiscord.minecraft.Settings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private final JDA jda;

    public DiscordBot() throws LoginException, IllegalStateException {
        this.jda = JDABuilder.createDefault(Settings.token)
                .addEventListeners(new ReadyListener())
                .build();
    }

    public JDA getJda() { return jda; }

    public void setPlaying(String text) {
        this.jda.getPresence().setActivity(Activity.playing(text));
    }
}
