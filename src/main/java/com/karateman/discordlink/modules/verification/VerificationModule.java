package com.karateman.discordlink.modules.verification;

import com.karateman.discordlink.configuration.Config;
import com.karateman.discordlink.modules.data.Module;
import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.modules.verification.commands.UnVerifyCommand;
import com.karateman.discordlink.modules.verification.commands.VerifyCommand;
import com.karateman.discordlink.modules.verification.events.VerificationDiscordReactEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.util.logging.Level;

public class VerificationModule implements Module {

    private DiscordLinkPlugin plugin;

    public VerificationModule(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean setup() {
        if(isSetup()) return false;
        Category category;
        try {
            category = plugin.getJda().getTextChannelById(plugin.getGamechatModule().getChannel()).getParent();
        } catch(Exception e) {
            category = plugin.getJda().getGuilds().get(0).createCategory("discord link").complete();
        }

        if(category == null) return false;

        if(getChannel().equalsIgnoreCase("123")) {
            category.createTextChannel("verification").queue((channel) -> {
                plugin.getConfig().set(Config.VERIFICATION_CHANNEL_ID.getId(), channel.getId());
                plugin.saveConfig();
                if(getMessage().equalsIgnoreCase("123")) {
                    MessageEmbed embed = plugin.getDiscordUtils().getDefaultEmbed("Discord Link")
                            .addField("__**Verification**__", "React with the check mark below to Verify!", false).build();

                    plugin.getJda().getTextChannelById(getChannel()).sendMessage(embed).queue((msg) -> {
                        plugin.getConfig().set(Config.VERIFICATION_MESSAGE_ID.getId(), msg.getId());
                        plugin.saveConfig();
                        plugin.getDiscordUtils().react("U+2714", msg);
                    });
                }
            });
        }

        if(getRole().equalsIgnoreCase("123")) {
            plugin.getJda().getGuilds().get(0).createRole().setName("Verified").queue((role) -> {
                plugin.getConfig().set(Config.VERIFICATION_ROLE_ID.getId(), role.getId());
                plugin.saveConfig();
                if(plugin.getGamechatModule().isEnabled()) {
                    try {
                        plugin.getJda().getTextChannelById(plugin.getGamechatModule().getChannel()).createPermissionOverride(role).setAllow(Permission.MESSAGE_WRITE).queue();
                    } catch (Exception e) {
                        // Ignored
                    }
                    for(Role r : plugin.getJda().getGuilds().get(0).getRoles()) {
                        if(r.isPublicRole()) {
                            try {
                                plugin.getJda().getTextChannelById(plugin.getGamechatModule().getChannel()).createPermissionOverride(r).setDeny(Permission.MESSAGE_WRITE).queue();
                            } catch(Exception e) {
                                // Ignored
                            }
                        }
                    }
                }
            });
        }

        plugin.saveConfig();
        runStartup();

        return true;
    }

    @Override
    public boolean isSetup() {
        if(!isEnabled()) return false;
        if(getChannel().equalsIgnoreCase("123")) return false;
        if(getRole().equalsIgnoreCase("123")) return false;
        if(getMessage().equalsIgnoreCase("123")) return false;

        return true;
    }

    @Override
    public boolean isEnabled() {
        return Config.VERIFICATION_MODULE.getAsBoolean();
    }

    @Override
    public void runStartup() {
        if(isEnabled() && isSetup()) {
            plugin.getLogger().log(Level.INFO, "Enabling Verification Module");
            plugin.getJda().addEventListener(new VerificationDiscordReactEvent(plugin));
            plugin.getCommand("verify").setExecutor(new VerifyCommand(plugin));
            plugin.getCommand("unverify").setExecutor(new UnVerifyCommand(plugin));
        }
    }

    @Override
    public void runShutdown() {

    }

    public String getChannel() {
        return Config.VERIFICATION_CHANNEL_ID.getAsString();
    }

    public String getRole() {
        return Config.VERIFICATION_ROLE_ID.getAsString();
    }

    public String getMessage() {
        return Config.VERIFICATION_MESSAGE_ID.getAsString();
    }
}
