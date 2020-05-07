package com.karateman.discordlink.modules.verification.storage;

import org.bukkit.entity.Player;

public interface VerificationType {

    void registerCode(String code, String discordId);
    void verifyUser(String discordId, String mcUsername);
    void unVerify(Player player);
    boolean isVerified(String discordId);
    boolean isVerified(Player player);
    boolean codeExists(String code);
    boolean hasCode(String discordId);
    String getDiscord(String username);

}
