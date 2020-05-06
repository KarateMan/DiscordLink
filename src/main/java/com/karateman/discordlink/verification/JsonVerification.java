package com.karateman.discordlink.verification;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class JsonVerification implements VerificationType {

    private HashMap<String, String> verificationCodes = new HashMap<>();
    private HashMap<String, String> verifications = new HashMap<>();

    public void registerCode(String code, String discordId) {
        verificationCodes.put(code, discordId);
    }

    public void verifyUser(String code, String mcUsername) {
        verifications.put(verificationCodes.get(code), mcUsername);
        verificationCodes.remove(code);
    }

    public boolean isVerified(String discordId) {
        if(verifications.containsKey(discordId)) return true;
        return false;
    }

    public boolean isVerified(Player player) {
        if(verifications.containsValue(player.getName())) return true;
        return false;
    }

    public boolean codeExists(String code) {
        if(verificationCodes.containsKey(code)) return true;
        return false;
    }

    @Override
    public boolean hasCode(String discordId) {
        if(verificationCodes.containsValue(discordId)) return true;
        return false;
    }

    @Override
    public String getDiscord(String username) {
        for(String user : verifications.keySet()) {
            if(verifications.get(user).equalsIgnoreCase(username)) return user;
        }

        return "";
    }
}
