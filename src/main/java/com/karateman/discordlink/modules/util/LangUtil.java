package com.karateman.discordlink.modules.util;

import com.karateman.discordlink.DiscordLinkPlugin;

import java.io.*;

public class LangUtil {

    private DiscordLinkPlugin plugin;

    private String langToUse;
    private File langFile;

    public LangUtil(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
        langToUse = plugin.getConfig().getString("language");
        setupDefaults();
        getFile();
    }

    private void setupDefaults() {
        File languages = new File("plugins/DiscordLink/languages");
        if(!languages.exists()) languages.mkdirs();
        try {
            String[] defaultLangs = {"en.lang", "es.lang"};
            for(String lang : defaultLangs) {
                InputStream resourceStream = DiscordLinkPlugin.getPlugin(DiscordLinkPlugin.class).getResource("languages/" + lang);
                File f = new File("plugins/DiscordLink/languages/" + lang);
                if(f.exists()) return;
                f.createNewFile();
                byte[] buffer = new byte[resourceStream.available()];
                resourceStream.read(buffer);

                OutputStream stream = new FileOutputStream(f);
                stream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFile() {
        File file = new File("plugins/DiscordLink/languages/" + langToUse);
        if(file.exists()) langFile = file;
        else langFile = null;
    }

    public String getMessage(String key) {
        if(langFile == null) return "Language File Error";
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(langFile), "UTF-8"))) {
            String line = reader.readLine();

            while(line != null) {
                if(line.startsWith(key)) {
                    line = line.substring(line.indexOf(":")+1);
                    line = line.trim();
                    line = line.substring(1, line.length()-1);
                    return line;
                }
                line = reader.readLine();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Language File Error.";
    }
}
