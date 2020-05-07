package com.karateman.discordlink.configuration;

import com.karateman.discordlink.DiscordLinkPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;

public class ConfigUtil {

    private static final String CONF_DIR = "plugins/DiscordLink";

    private DiscordLinkPlugin plugin;

    public ConfigUtil(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
        createConfDir();
        loadDefaultConfig();
        updateConfig();
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

    private void updateConfig() {
        FileConfiguration previousConf = plugin.getConfig();
        File confFile = new File(CONF_DIR + File.separator + "config.yml");

        confFile.delete();
        loadDefaultConfig();
        plugin.saveConfig();
        plugin.reloadConfig();

        /**try {
            File confFile = new File(CONF_DIR + File.separator + "config.yml");

            HashMap<String, Object> configVals = new HashMap<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(confFile), "UTF-8"));

            String line = reader.readLine();

            while(line != null) {
                if(line.startsWith("#")) {
                    continue;
                } else {
                    try {
                        int colon = line.indexOf(":");
                        if(plugin.getConfig().get(line.substring(0, colon)) instanceof String) {
                            configVals.put(line.substring(0, colon+1), plugin.getConfig().getString(line.substring(0, colon+1)));
                        } else {
                            configVals.put(line.substring(0, colon+1), plugin.getConfig().getBoolean(line.substring(0, colon+1)));
                        }
                    } catch (Exception e) {
                        // Ignore any exceptions
                    }
                }

                line = reader.readLine();
            }

            OutputStream confStream = new FileOutputStream(file);
            confStream.write(newFile.toString().getBytes());
            confStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }**/
    }

    private void createConfDir() {
        File confDir = new File(CONF_DIR);
        if(!confDir.exists()) confDir.mkdirs();
    }

}
