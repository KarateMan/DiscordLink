package com.karateman.discordlink.modules.commands.data;

import com.karateman.discordlink.DiscordLinkPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DiscordCommandSender implements CommandSender {

    private final Server server;
    private final PermissibleBase perm = new PermissibleBase(this);

    private String channel;
    private DiscordLinkPlugin plugin;
    private String command;

    public DiscordCommandSender(Server server, String channel, DiscordLinkPlugin plugin, String command) {
        this.server = server;
        this.channel = channel;
        this.plugin = plugin;
        this.command = command;
    }

    @Override
    public void sendMessage(String s) {
        EmbedBuilder embed = plugin.getDiscordUtils().getDefaultEmbed("Command Response");
        embed.addField(command, s, false);
        plugin.getDiscordUtils().sendMessage(embed.build(), channel);
    }

    @Override
    public void sendMessage(String[] strings) {
        EmbedBuilder embed = plugin.getDiscordUtils().getDefaultEmbed("Command Response");

        String field = "";
        for(String s : strings) {
            field += s + "\n";
        }
        embed.addField(command, field, false);

        plugin.getDiscordUtils().sendMessage(embed.build(), channel);
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of server console");
    }

    public boolean isPlayer() {
        return false;
    }

    public Server getServer() {
        return server;
    }

    @Override
    public @NotNull String getName() {
        return "DISCORD";
    }

    @Override
    public @NotNull Spigot spigot() {
        return null;
    }

    public boolean isPermissionSet(String name) {
        return perm.isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return this.perm.isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return perm.hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return this.perm.hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return perm.addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return perm.addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return perm.addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return perm.addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        perm.removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        perm.recalculatePermissions();
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return perm.getEffectivePermissions();
    }
}
