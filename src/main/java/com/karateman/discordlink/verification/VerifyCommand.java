package com.karateman.discordlink.verification;

import com.karateman.discordlink.spigot.DiscordLinkPlugin;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
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
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        VerificationUtils verificationUtils = new VerificationUtils(plugin);

        if(args.length != 1) {
            player.sendMessage(ChatColor.RED + "Please use the format /verify [code]");
            return false;
        }

        if(verificationUtils.isVerified(player)) {
            player.sendMessage(ChatColor.RED + "You are already verified!");
        } else {
            if(verificationUtils.checkCode(args[0])) {
                try {
                    verificationUtils.verifyUser(args[0], player.getName());

                    User user = DiscordLinkPlugin.getBot().getJda().getUserById(verificationUtils.getDiscord(player.getName()));
                    Role role  = DiscordLinkPlugin.getBot().getJda().getRoleById(plugin.getConfig().getString("verification-role-id"));
                    DiscordLinkPlugin.getBot().getJda().getGuilds().get(0).addRoleToMember(verificationUtils.getDiscord(player.getName()), role).queue();

                    player.sendMessage(ChatColor.GREEN + "You have been verified with the discord " + ChatColor.BLUE + user.getName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                player.sendMessage(ChatColor.RED + "That code could not be found.");
            }
        }
        return false;
    }

}
