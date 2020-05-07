package com.karateman.discordlink;

import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.util.DiscordUtils;
import com.karateman.discordlink.modules.commands.CommandsModule;
import com.karateman.discordlink.modules.gamechat.GamechatModule;
import com.karateman.discordlink.modules.util.RankUtil;
import com.karateman.discordlink.modules.util.UpdateChecker;
import com.karateman.discordlink.modules.verification.VerificationModule;
import com.karateman.discordlink.configuration.ConfigUtil;
import net.dv8tion.jda.api.JDA;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Logger;

public class DiscordLinkPlugin extends JavaPlugin {

    private JDA jda;

    private GamechatModule gamechatModule;
    private VerificationModule verificationModule;
    private CommandsModule commandsModule;

    private DiscordUtils discordUtils;
    private RankUtil rankUtil;

    @Override
    public void onEnable() {
        new ConfigUtil(this);

        if(getConfig().getString("token").equalsIgnoreCase("token-placeholder")) {
            getLogger().severe("Token not set. Disabling plugin. Follow the instructions in the config.yml to setup token.");
            getPluginLoader().disablePlugin(this);
        }

        jda = new Bot(this).getJda();
        discordUtils = new DiscordUtils(jda, this);
        rankUtil = new RankUtil();

        gamechatModule = new GamechatModule(this);
        gamechatModule.runStartup();

        verificationModule = new VerificationModule(this);
        verificationModule.runStartup();

        commandsModule = new CommandsModule(this);
        commandsModule.runStartup();

        if(!gamechatModule.isEnabled() && !verificationModule.isEnabled() && !commandsModule.isEnabled()) {
            getLogger().severe("No modules are enabled. Disabling plugin.");
            getPluginLoader().disablePlugin(this);
        }

        getCommand("discordlink").setExecutor(new DiscordLinkCommand(this));

        if(Config.CHECK_UPDATES.getAsBoolean()) checkUpdates();
    }

    @Override
    public void onDisable() {
        gamechatModule.runShutdown();
        verificationModule.runShutdown();
        commandsModule.runShutdown();
        getJda().shutdownNow();
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
                        // Ignore any exceptions
                    }
                }

                line = reader.readLine();
                newFile.append(System.lineSeparator());
            }

            OutputStream confStream = new FileOutputStream(file);
            confStream.write(newFile.toString().getBytes());
            confStream.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUpdates() {
        Logger logger = this.getLogger();

        new UpdateChecker(this, 78446).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("Running latest version");
            } else {
                logger.info("There is a new update available.");
            }
        });
    }

    public GamechatModule getGamechatModule() {
        return gamechatModule;
    }

    public VerificationModule getVerificationModule() {
        return verificationModule;
    }

    public CommandsModule getCommandsModule() {
        return commandsModule;
    }

    public JDA getJda() {
        return jda;
    }

    public RankUtil getRankUtil() {
        return rankUtil;
    }

    public DiscordUtils getDiscordUtils() {
        return discordUtils;
    }
}
