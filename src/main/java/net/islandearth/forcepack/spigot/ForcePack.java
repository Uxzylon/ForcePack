package net.islandearth.forcepack.spigot;

import net.islandearth.forcepack.spigot.command.forcepackCommand;
import net.islandearth.forcepack.spigot.listener.ResourcePackListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForcePack extends JavaPlugin {

	public static ForcePack plugin;

	@Override
	public void onEnable() {
		plugin = this;
		this.createConfig();
		this.registerListeners();
		this.getLogger().info("Enabled!");
		getCommand("forcepack").setExecutor(new forcepackCommand());
	}
	
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ResourcePackListener(this), this);
	}

	private void createConfig() {
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("Server.ResourcePack.url", "https://plopsalandminecraft.parks-mc.fr/Plopsacraft_Pack-v18.zip");
		getConfig().addDefault("Server.ResourcePack.hash", "bb3cb6741d9bae330f868f43edcdcebbed5550db");
		getConfig().addDefault("Server.kick", true);
		saveConfig();
	}
}
