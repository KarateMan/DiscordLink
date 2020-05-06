package com.karateman.discordlink.spigot.util;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class RankUtil {

    private static Chat chat = null;

    public RankUtil() {
        setupChat();
    }

    public static String getRankPrefix(Player player) {
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
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
}
