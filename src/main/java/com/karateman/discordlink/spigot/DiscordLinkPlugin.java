package com.karateman.discordlink.spigot;

import com.karateman.discordlink.discord.Bot;
import com.karateman.discordlink.discord.utils.SetupUtils;
import com.karateman.discordlink.spigot.commands.DiscordLinkCommand;
import com.karateman.discordlink.spigot.events.SpigotChatEvent;
import com.karateman.discordlink.spigot.events.SpigotJoinLeaveEvent;
import com.karateman.discordlink.spigot.util.ConfigUtil;
import com.karateman.discordlink.spigot.util.RankUtil;
import com.karateman.discordlink.verification.VerifyCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class DiscordLinkPlugin extends JavaPlugin {

    private static Bot bot;
    private ConfigUtil configUtil;
    private RankUtil rankUtil;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SpigotChatEvent(this), this);
        getServer().getPluginManager().registerEvents(new SpigotJoinLeaveEvent(this), this);
        getCommand("verify").setExecutor(new VerifyCommand(this));
        getCommand("discordlink").setExecutor(new DiscordLinkCommand(this));

        rankUtil = new RankUtil();
        configUtil = new ConfigUtil(this);

        bot = new Bot(this);

        new SetupUtils(this).setupVerificationChannel();

        if(getConfig().getBoolean("gamechat-startstop-messages")) {
            DiscordLinkPlugin.getBot().getJda().getTextChannelById(getConfig().getString("gamechat-id")).sendMessage(getConfig().getString("gamechat-start-message")).queue();
        }
    }

    @Override
    public void onDisable() {
        if(getConfig().getBoolean("gamechat-startstop-messages")) {
            DiscordLinkPlugin.getBot().getJda().getTextChannelById(getConfig().getString("gamechat-id")).sendMessage(getConfig().getString("gamechat-stop-message")).queue();
        }
    }

    @Override
    public synchronized void saveConfig() {
        try {
            File file = new File("plugins/DiscordLink/config.yml");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            StringBuilder newFile = new StringBuilder();
            String line = reader.readLine();

            while(line != null) {
                if(line.startsWith("#")) {
                    newFile.append(line);
                } else {
                    try {
                        int colon = line.indexOf(":");
                        if(getConfig().get(line.substring(0, colon)) instanceof String) {
                            line = line.substring(0, colon+1) + " \'" + getConfig().get(line.substring(0, colon)) + "\'";
                        } else {
                            line = line.substring(0, colon+1) + " " + getConfig().get(line.substring(0, colon));
                        }
                        newFile.append(line);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                line = reader.readLine();
                newFile.append(System.lineSeparator());
            }

            OutputStream confStream = new FileOutputStream(file);
            confStream.write(newFile.toString().getBytes());

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bot getBot() {
        return bot;
    }

    public ConfigUtil getConfigUtil() {
        return configUtil;
    }

}
