package markehme.FactionsPerms;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import markehme.FactionsPerms.listeners.ReadyCheck;
import markehme.FactionsPerms.obj.Group;
import markehme.FactionsPerms.obj.PermissionUser;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Welcome to the delicious code of FactionsPerms! Highly un-documented (sorry, I do try sometimes).
 * 
 * W A R N I NG
 * ****************
 * This plugin has to load BEFORE Factions and Vault, so therefore
 * this main class CAN NOT make any reference (at all) to Factions
 * 
 * 
 * @author MarkehMe <mark@markeh.me>
 * @url http://dev.bukkit.org/bukkit-plugins/factionsperms/
 *
 */
public class FactionsPerms extends JavaPlugin {
	
	public static Map<String, Group> permissionsSet = new HashMap<>();
	public static Map<String, PermissionUser> userSet = new HashMap<>();
	
	public static boolean isReady = false;
	
	private FileConfiguration permissionsConfig = null;
	private FileConfiguration usersConfig = null;
	
	private File permissionsConfigFile = null;
	private File usersConfigFile = null;
	
	private static FactionsPerms plugin;
	
	public static FactionsPerms get() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		// Return everything to default (incase plugin reload) 
		userSet.clear();
		permissionsSet.clear();
		
		// Load files
		permissionsConfigFile = new File(getDataFolder(), "permissions.yml");
		usersConfigFile = new File(getDataFolder(), "users.yml");
		
		saveDefaultConfig();
		
		permissionsConfig = YamlConfiguration.loadConfiguration(permissionsConfigFile);
		
		if(!permissionsConfigFile.exists()) {
			// Let's build a permissions file. 
			permissionsConfig.set("Permissions.default.global", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.current", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.ally", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.neutral", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.enemy", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.safezone", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.warzone", Arrays.asList(""));
			permissionsConfig.set("Permissions.default.factions.wilderness", Arrays.asList(""));
			try {
				permissionsConfig.save(permissionsConfigFile);
			} catch (IOException e) {
				this.getLogger().log(Level.INFO, "Failed to create a default permissions file. Do we have permission?");
			}
		}
		
		if(getConfig().getBoolean("disallowConnectionsUntilReady")) {
			getServer().getPluginManager().registerEvents(new ReadyCheck(), this);
		}
			
		
		// tests 
		//permissionsSet.put("Default", new Group("Default", null, null, null, null, null, null, null, null));
		
		//permissionsSet.get("Default").Permissions_Ally.containsKey("factionsplus.reload");
		
		this.getLogger().log(Level.INFO, "FactionsPerms is ready, waiting for Factions to be ready.");
		
		// This task is used to check if Factions is enabled
		new FactionsPermsReady(this).runTaskTimer(this, 1, 5);
	}
	


}
