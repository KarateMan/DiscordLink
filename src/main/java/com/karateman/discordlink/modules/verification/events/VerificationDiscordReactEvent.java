package com.karateman.discordlink.modules.verification.events;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.modules.verification.storage.VerificationUtils;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class VerificationDiscordReactEvent implements EventListener {

    private DiscordLinkPlugin plugin;

    public VerificationDiscordReactEvent(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(!(genericEvent instanceof GuildMessageReactionAddEvent)) return;
        GuildMessageReactionAddEvent event = (GuildMessageReactionAddEvent) genericEvent;

        VerificationUtils verificationUtils = new VerificationUtils(plugin);

        if(event.getUser().isBot()) return;

        if(!event.getChannel().getId().equalsIgnoreCase(plugin.getVerificationModule().getChannel())) {
            event.getReaction().removeReaction(event.getUser()).queue();
            return;
        }

        if(verificationUtils.hasCode(event.getUserId())) {
            event.getReaction().removeReaction(event.getUser()).queue();
            return;
        }

        if(verificationUtils.isVerified(event.getUserId())) {
            event.getReaction().removeReaction(event.getUser()).queue();
            return;
        }

        event.getReaction().removeReaction(event.getUser()).queue();

        String code = verificationUtils.createVerificationCode(event.getUserId());

        event.getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessage("Type `/verify " + code + "` in-game to verify yourself!").queue();
        });
    }
}
