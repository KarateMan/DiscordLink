package com.karateman.discordlink.modules.verification.commands;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.modules.verification.storage.VerificationUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnVerifyCommand implements CommandExecutor {

    private DiscordLinkPlugin plugin;

    public UnVerifyCommand(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        VerificationUtils verificationUtils = new VerificationUtils(plugin);

        if(args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /unverify");
            return false;
        }

        if(!verificationUtils.isVerified(player)) {
            player.sendMessage(ChatColor.RED + "You are not verified.");
            return false;
        }

        plugin.getJda().getGuilds().get(0).removeRoleFromMember(verificationUtils.getDiscord(player.getName()),
                plugin.getJda().getRoleById(plugin.getVerificationModule().getRole())).queue();

        verificationUtils.unVerify(player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &bYou have been unverified!"));

        return false;
    }
}
