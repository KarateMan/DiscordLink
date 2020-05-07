package com.karateman.discordlink.configuration;

import com.karateman.discordlink.DiscordLinkPlugin;

public enum Config {

    TOKEN("token"),
    SERVER_NAME("server-name"),
    SERVER_IP("server-ip"),

    GAMECHAT_MODULE("gamechat-module"),
    GAMECHAT_ID("gamechat-id"),
    GAMECHAT_INGAME_MESSAGES("gamechat-ingame-messages"),
    GAMECHAT_DISCORD_FORMAT("gamechat-discord-format"),
    GAMECHAT_SERVER_FORMAT("gamechat-server-format"),
    GAMECHAT_STARTSTOP_MESSAGES("gamechat-startstop-messages"),
    GAMECHAT_START_FORMAT("gamechat-start-message"),
    GAMECHAT_STOP_FORMAT("gamechat-stop-message"),
    GAMECHAT_LOGINLOGOUT_MESSAGES("gamechat-loginlogout-messages"),
    GAMECHAT_LOGIN_FORMAT("gamechat-login-message"),
    GAMECHAT_LOGOUT_FORMAT("gamechat-logout-message"),
    GAMECHAT_DEATH_MESSAGES("gamechat-death-messages"),
    GAMECHAT_DEATH_FORMAT("gamechat-death-format"),

    VERIFICATION_MODULE("verification-module"),
    VERIFICATION_CHANNEL_ID("verification-channel-id"),
    VERIFICATION_ROLE_ID("verification-role-id"),
    VERIFICATION_STORAGE_TYPE("verification-storage"),
    VERIFICATION_ADDRESS("address"),
    VERIFICATION_USER("user"),
    VERIFICATION_PASS("pass"),
    VERIFICATION_MESSAGE_ID("verification-message-id"),

    COMMANDS_MODULE("commands-module"),
    COMMANDS_PREFIX("commands-prefix"),
    COMMANDS_SILENT_PREFIX("commands-silent-prefix"),
    COMMANDS_CHANNEL("commands-channel"),

    CHECK_UPDATES("check-updates");

    private String id;

    Config(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAsString() {
        return DiscordLinkPlugin.getPlugin(DiscordLinkPlugin.class).getConfig().getString(id);
    }

    public boolean getAsBoolean() {
        return DiscordLinkPlugin.getPlugin(DiscordLinkPlugin.class).getConfig().getBoolean(id);
    }

}
