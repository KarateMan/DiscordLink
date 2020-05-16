package com.karateman.discordlink.modules.util;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class RankUtil {

    private Chat chat = null;

    public RankUtil() {
        if(Bukkit.getPluginManager().getPlugin("Vault") == null) return;
        setupChat();
    }

    public String getRankPrefix(Player player) {
        if(chat == null) return "";
        try {
            StringBuilder prefix = new StringBuilder();

            boolean removeNext = false;
            for(char c : chat.getPlayerPrefix(player).toCharArray()) {
                if(removeNext) {
                    removeNext = false;
                    continue;
                }

                if(c == '&') {
                    removeNext = true;
                    continue;
                }

                prefix.append(c);
            }

            return prefix.toString();
        } catch(Exception e) {

        }
        return "";
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
