package com.karateman.discordlink.modules.verification.storage;

import com.google.gson.Gson;
import com.karateman.discordlink.DiscordLinkPlugin;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Random;

public class VerificationUtils {

    private DiscordLinkPlugin plugin;

    public VerificationUtils(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    public String createVerificationCode(String discordId) {
        StringBuilder codeBuilder = new StringBuilder();

        for(int i = 0; i < 6; ++i) {
            char x = (char) (new Random().nextInt(26) + 65);
            codeBuilder.append(x);
        }
        //TODO: Take into account repeat codes
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);
                jsonVerification.registerCode(codeBuilder.toString(), discordId);

                OutputStream outputStream = new FileOutputStream(new File("plugins/DiscordLink/verifications.json"));
                outputStream.write(new Gson().toJson(jsonVerification).getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }

        return codeBuilder.toString();
    }

    public void verifyUser(String code, String mcUsername) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);
                jsonVerification.verifyUser(code, mcUsername);

                OutputStream outputStream = new FileOutputStream(new File("plugins/DiscordLink/verifications.json"));
                outputStream.write(new Gson().toJson(jsonVerification).getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }
    }

    public boolean isVerified(String discordId) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);

                return jsonVerification.isVerified(discordId);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }

        return false;
    }

    public boolean isVerified(Player player) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);

                return jsonVerification.isVerified(player);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }

        return false;
    }

    public boolean checkCode(String code) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);

                return jsonVerification.codeExists(code);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }

        return false;
    }

    public boolean hasCode(String discordId) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);

                return jsonVerification.hasCode(discordId);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }

        return false;
    }

    public String getDiscord(String username) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);

                return jsonVerification.getDiscord(username);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }

        return "";
    }

    public void unVerify(Player player) {
        String storageType = plugin.getConfig().getString("verification-storage");
        if(storageType.equalsIgnoreCase("json")) {
            try {
                JsonVerification jsonVerification = new Gson().fromJson(new FileReader(new File("plugins/DiscordLink/verifications.json")), JsonVerification.class);
                jsonVerification.unVerify(player);

                OutputStream outputStream = new FileOutputStream(new File("plugins/DiscordLink/verifications.json"));
                outputStream.write(new Gson().toJson(jsonVerification).getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(storageType.equalsIgnoreCase("mysql")) {
            //TODO: Setup MySQL
        } else if(storageType.equalsIgnoreCase("sqlite")) {
            //TODO: Setup SQLite
        }
    }
}
