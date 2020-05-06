package com.karateman.discordlink.discord.events;

import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import com.karateman.discordlink.verification.VerificationUtils;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class DiscordReactEvent implements EventListener {

    private DiscordLinkPlugin plugin;

    public DiscordReactEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(!(genericEvent instanceof GuildMessageReactionAddEvent)) return;
        GuildMessageReactionAddEvent event = (GuildMessageReactionAddEvent) genericEvent;

        if(event.getUser().isBot()) return;

        VerificationUtils verificationUtils = new VerificationUtils(plugin);

        if(!verificationUtils.isVerified(event.getUserId()) && !verificationUtils.hasCode(event.getUserId())) {
            String code = verificationUtils.createVerificationCode(event.getUserId());

            event.getUser().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("Type `/verify " + code + "` in game to verify yourself!").queue();
            });
        }

        event.getReaction().removeReaction(event.getUser()).queue();
    }

}
