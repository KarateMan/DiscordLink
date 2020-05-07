package com.karateman.discordlink;

import com.karateman.discordlink.configuration.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    private JDA jda;
    private DiscordLinkPlugin plugin;

    public Bot(DiscordLinkPlugin plugin) {
        this.plugin = plugin;

        try {
            String serverName = Config.SERVER_NAME.getAsString();
            String serverIp = Config.SERVER_IP.getAsString();
            JDA jda = new JDABuilder(Config.TOKEN.getAsString()).setActivity(Activity.playing(serverName + " at " + serverIp)).build().awaitReady();
            this.jda = jda;
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
        }

    }

    public JDA getJda() {
        return jda;
    }
}
