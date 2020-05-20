package com.karateman.discordlink.modules.verification.commands;

import com.karateman.discordlink.DiscordLinkPlugin;
import com.karateman.discordlink.modules.util.LangUtil;
import com.karateman.discordlink.modules.verification.storage.VerificationUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerifyCommand implements CommandExecutor {

    private DiscordLinkPlugin plugin;

    public VerifyCommand(DiscordLinkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LangUtil langUtil = new LangUtil(plugin);
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        VerificationUtils verificationUtils = new VerificationUtils(plugin);

        if(args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /verify [Code]");
            return false;
        }

        String code = args[0];

        if(verificationUtils.isVerified(player)) {
            player.sendMessage(ChatColor.RED + langUtil.getMessage("verification-game-message-already-verified"));
            return false;
        }

        if(!verificationUtils.checkCode(code)) {
            player.sendMessage(ChatColor.RED + langUtil.getMessage("verification-game-message-no-code"));
            return false;
        }

        verificationUtils.verifyUser(code, player.getName());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&9DiscordLink&8] &b"
                + langUtil.getMessage("verification-game-message-success")
                .replace("%discord%", plugin.getJda().getGuilds().get(0).getMemberById(verificationUtils.getDiscord(player.getName())).getEffectiveName())));

        plugin.getJda().getGuilds().get(0).addRoleToMember(verificationUtils.getDiscord(player.getName()),
                plugin.getJda().getRoleById(plugin.getVerificationModule().getRole())).queue();
        return false;
    }
}
