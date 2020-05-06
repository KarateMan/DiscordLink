package com.karateman.discordlink.modules.util;

import com.karateman.discordlink.DiscordLinkPlugin;

import java.io.*;

public class ConfigUtil {

    private static final String CONF_DIR = "plugins/DiscordLink";

    private DiscordLinkPlugin plugin;

    public ConfigUtil(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
        createConfDir();
        loadDefaultConfig();
    }

    private void loadDefaultConfig() {
        try {
            File confFile = new File(CONF_DIR + File.separator + "config.yml");

            if(confFile.exists()) return;

            confFile.createNewFile();

            InputStream resourceStream = DiscordLinkPlugin.getPlugin(DiscordLinkPlugin.class).getResource("config.yml");
            byte[] buffer = new byte[resourceStream.available()];
            resourceStream.read(buffer);

            OutputStream confStream = new FileOutputStream(confFile);
            confStream.write(buffer);

        } catch (IOException exception) {
            // Failed
        }
    }

    private void createConfDir() {
        File confDir = new File(CONF_DIR);
        if(!confDir.exists()) confDir.mkdirs();
    }

}
