package net.islandearth.forcepack.spigot.listener;

import net.islandearth.forcepack.spigot.ForcePack;
import net.islandearth.forcepack.spigot.resourcepack.ResourcePack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourcePackListener implements Listener {

	private final ForcePack plugin;
	
	public ResourcePackListener(ForcePack plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void ResourcePackStatus(PlayerResourcePackStatusEvent event) {
		Player player = event.getPlayer();
		String declined = ChatColor.RED + "Vous devez accepter le resource pack!";
		String error = ChatColor.RED + "Erreur de t" + '\u00E9' + "l" + '\u00E9' + "chargement!";
		switch (event.getStatus()) {
			case DECLINED: {
				if (getConfig().getBoolean("Server.kick")) player.kickPlayer(declined);
				else player.sendMessage(declined);
				break;
			}

			case FAILED_DOWNLOAD: {
				Bukkit.getScheduler ().runTaskLater (plugin, () -> {
					if (getConfig().getBoolean("Server.kick")) player.kickPlayer(error);
					else player.sendMessage(error);
				}, 2);
				break;
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent pje) {
		Player player = pje.getPlayer();
		String url = getConfig().getString("Server.ResourcePack.url");
		String hash = getConfig().getString("Server.ResourcePack.hash");
		ResourcePack pack = new ResourcePack(url, hash);
		player.setResourcePack(url, pack.getHashHex());
	}
	
	private FileConfiguration getConfig() {
		return plugin.getConfig();
	}
}
