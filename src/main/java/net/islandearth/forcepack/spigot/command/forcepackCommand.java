package net.islandearth.forcepack.spigot.command;

import net.islandearth.forcepack.spigot.ForcePack;
import net.islandearth.forcepack.spigot.resourcepack.ResourcePack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class forcepackCommand implements TabExecutor {

    ForcePack plugin = ForcePack.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && Objects.equals(args[0], "reload")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("forcepack.reload")) {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission!");
                    return true;
                }
            }
            plugin.reloadConfig();
            String url = plugin.getConfig().getString("Server.ResourcePack.url");
            String hash = plugin.getConfig().getString("Server.ResourcePack.hash");
            ResourcePack pack = new ResourcePack(url, hash);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setResourcePack(url, pack.getHashHex());
                player.sendMessage(ChatColor.BLUE + "[ForcePack] " + ChatColor.YELLOW + "Resource Pack Recharg" + '\u00E9' + "!");
            }
            plugin.getLogger().info("Resource Pack Recharg" + '\u00E9' + "!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        } else if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}
