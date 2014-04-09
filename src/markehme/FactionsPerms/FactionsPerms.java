package markehme.FactionsPerms;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import markehme.FactionsPerms.listeners.ReadyCheck;
import markehme.FactionsPerms.obj.Group;
import markehme.FactionsPerms.obj.PermissionUser;
import markehme.FactionsPerms.utilities.Metrics;

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
	
	public static Map<String, Group> permissionsSet		= new HashMap<>();
	public static Map<String, PermissionUser> userSet	= new HashMap<>();
	
	public static boolean isReady = false;
	
	public static FileConfiguration permissionsConfig = null;
	public static FileConfiguration usersConfig = null;
	
	public static File permissionsConfigFile = null;
	public static File usersConfigFile = null;
	
	private static FactionsPerms plugin;
	
	public static Metrics metrics = null;
	
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
		
		// Save default config.yml
		saveDefaultConfig();
		
		// Load permissions file
		permissionsConfig = YamlConfiguration.loadConfiguration(permissionsConfigFile);
		
		// Make sure it exists
		if(!permissionsConfigFile.exists()) {
			createDefaultPermissionsFile();
		}
		
		// Load the permissions file 
		loadPermissions();
		
		// Load users file
		usersConfig = YamlConfiguration.loadConfiguration(usersConfigFile);
		
		// Make sure it exists
		if(!usersConfigFile.exists()) {
			createDefaultUsersFile();
		}
		
		// Load the users file
		loadUsers();
		
		if(getConfig().getBoolean("disallowConnectionsUntilReady")) {
			getServer().getPluginManager().registerEvents(new ReadyCheck(), this);
		}
		
		// tests 
		//permissionsSet.put("Default", new Group("Default", null, null, null, null, null, null, null, null));
		
		//permissionsSet.get("Default").Permissions_Ally.containsKey("factionsplus.reload");
		
		
		
		this.getLogger().log(Level.INFO, "FactionsPerms is ready, waiting for Factions to be ready.");
		
		try {
			metrics = new Metrics(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// This task is used to check if Factions is enabled
		new FactionsPermsReady(this).runTaskTimer(this, 1, 5);
		
	}
	
	/**
	 * Creates a default permissions file 
	 */
	private void createDefaultPermissionsFile() {
		permissionsConfig.set("Permissions.default.global", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.current", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.ally", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.neutral", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.enemy", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.safezone", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.warzone", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.factions.wilderness", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.world.world_the_nether.global", Arrays.asList(""));
		permissionsConfig.set("Permissions.default.world.world_the_nether.factions.warzone", Arrays.asList(""));
		
		try {
			permissionsConfig.save(permissionsConfigFile);
		} catch (IOException e) {
			this.getLogger().log(Level.INFO, "Failed to create a default permissions.yml file. Do we have permission?");
		}
		
	}
	
	/**
	 * Creates a default users.yml file
	 */
	private void createDefaultUsersFile() {
		try {
			usersConfigFile.createNewFile();
		} catch (IOException e) {
			this.getLogger().log(Level.INFO, "Failed to create a default users.yml file. Do we have permission?");
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the permissions.yml file 
	 */
	private void loadPermissions() {
		Set<String> groups = permissionsConfig.getConfigurationSection("Permissions").getKeys(false);
		
		FactionsPerms.permissionsSet.clear();
		
		int groupCount = 0;
		for(String group : groups) {
			groupCount++;
						
			// All the HashMaps for a group
			HashMap<String, Boolean> Permissions_Global		= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Current	= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Ally 		= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Neutral 	= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Enemy 		= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Safezone 	= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Warzone 	= new HashMap<String, Boolean>();
			HashMap<String, Boolean> Permissions_Wilderness	= new HashMap<String, Boolean>();
			
			// Global permissions 
			if(!permissionsConfig.getList("Permissions."+group+".global").isEmpty()) {
				List<?> globalPerms = permissionsConfig.getList("Permissions."+group+".global");
				
				for (int i=0;i<globalPerms.size();i++) {
					String perm = (String) globalPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Global.containsKey(perm.substring(1))) {
							Permissions_Global.remove(perm.substring(1));
						}
						
						Permissions_Global.put(perm.substring(1), false);
					} else {
						Permissions_Global.put(perm, true);
					}
				}
			}
			
			// Factions.current
			if(!permissionsConfig.getList("Permissions."+group+".factions.current").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.current");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Current.containsKey(perm.substring(1))) {
							Permissions_Current.remove(perm.substring(1));
						}
						
						Permissions_Current.put(perm.substring(1), false);
					} else {
						Permissions_Current.put(perm, true);
					}
				}
			}
			
			// Factions.ally
			if(!permissionsConfig.getList("Permissions."+group+".factions.ally").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.ally");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Ally.containsKey(perm.substring(1))) {
							Permissions_Ally.remove(perm.substring(1));
						}
						
						Permissions_Ally.put(perm.substring(1), false);
					} else {
						Permissions_Ally.put(perm, true);
					}
				}
			}
			
			// factions.neutral
			if(!permissionsConfig.getList("Permissions."+group+".factions.neutral").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.neutral");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Neutral.containsKey(perm.substring(1))) {
							Permissions_Neutral.remove(perm.substring(1));
						}
						
						Permissions_Neutral.put(perm.substring(1), false);
					} else {
						Permissions_Neutral.put(perm, true);
					}
				}
			}
			
			
			// factions.enemy
			if(!permissionsConfig.getList("Permissions."+group+".factions.enemy").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.enemy");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Enemy.containsKey(perm.substring(1))) {
							Permissions_Enemy.remove(perm.substring(1));
						}
						
						Permissions_Enemy.put(perm.substring(1), false);
					} else {
						Permissions_Enemy.put(perm, true);
					}
				}
			}
			
			// factions.safezone
			if(!permissionsConfig.getList("Permissions."+group+".factions.safezone").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.safezone");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Safezone.containsKey(perm.substring(1))) {
							Permissions_Safezone.remove(perm.substring(1));
						}
						
						Permissions_Safezone.put(perm.substring(1), false);
					} else {
						Permissions_Safezone.put(perm, true);
					}
				}
			}

			
			// factions.warzone
			if(!permissionsConfig.getList("Permissions."+group+".factions.warzone").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.warzone");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
 					if(perm.startsWith("-")) {
						if(Permissions_Warzone.containsKey(perm.substring(1))) {
							Permissions_Warzone.remove(perm.substring(1));
						}
						Permissions_Warzone.put(perm.substring(1), false);
					} else {
						Permissions_Warzone.put(perm, true);
					}
				}
			}
			
			// factions.wilderness
			if(!permissionsConfig.getList("Permissions."+group+".factions.wilderness").isEmpty()) {
				List<?> fCurrentPerms = permissionsConfig.getList("Permissions."+group+".factions.wilderness");
				
				for (int i = 0; i < fCurrentPerms.size(); i++) {
					String perm = (String) fCurrentPerms.get(i);
					
					if(perm.startsWith("-")) {
						if(Permissions_Wilderness.containsKey(perm.substring(1))) {
							Permissions_Wilderness.remove(perm.substring(1));
						}
						
						Permissions_Wilderness.put(perm.substring(1), false);
					} else {
						Permissions_Wilderness.put(perm, true);
					}
				}
			}
			
			List<?> inheritGroups = null;
			
			if(!permissionsConfig.getList("Permissions."+group+".inherit").isEmpty()) {
				inheritGroups = permissionsConfig.getList("Permissions."+group+".inherit");
			}
			
			Group groupToAdd = new Group(group, Permissions_Global, Permissions_Current, Permissions_Ally, Permissions_Neutral, Permissions_Enemy, Permissions_Safezone, Permissions_Warzone, Permissions_Wilderness, inheritGroups);
			
			FactionsPerms.permissionsSet.put(group, groupToAdd);
			
		}
		
		// Now that all the groups are loaded, we can load the inherits 
		if(groupCount > 0) {
			for(String group : FactionsPerms.permissionsSet.keySet()) {
				FactionsPerms.permissionsSet.get(group).setAllInherits();
			}
		}
		
		if(groupCount > 1) {
			this.getLogger().log(Level.INFO, "Loaded " + groupCount + " group's");
		} else if(groupCount == 0) {
			this.getLogger().log(Level.INFO, "No groups loaded");
		} else {
			this.getLogger().log(Level.INFO, "Loaded 1 group");
		}
		
		
	}
	
	/**
	 * Load the users.yml file
	 */
	private void loadUsers() {
		if(usersConfig.getConfigurationSection("Users") == null) {
			this.getLogger().log(Level.INFO, "No Users section in users.yml - no users loaded (this is ok).");
			return;
		}
		
		Set<String> users = usersConfig.getConfigurationSection("Users").getKeys(false);
		
		int userCount = 0;
		
		for(String user : users) {
			userCount++;
			
			loadUser(user);
			
		}
		
		if(userCount > 1) {
			this.getLogger().log(Level.INFO, "Loaded " + userCount + " users");
		} else if(userCount == 0) {
			this.getLogger().log(Level.INFO, "No users loaded");
		} else {
			this.getLogger().log(Level.INFO, "Loaded 1 user");
		}
	}
	
	public static void loadUser(String user) {
		
		List<?> user_groups = usersConfig.getList("Users." + user + ".groups");
		List<?> user_perms = usersConfig.getList("Users." + user + ".permissions");
		
		HashMap<String, Boolean> permissions = new HashMap<String, Boolean>();
		HashMap<String, String> groups = new HashMap<String, String>();
		
		// Load users permissions
		for (int i = 0; i < user_perms.size(); i++) {
			String perm = (String) user_perms.get(i);
			
			if(perm.startsWith("-")) {
				permissions.remove(perm.substring(1));
				
				permissions.put(perm.substring(1), false);
			} else {
				permissions.remove(perm);
				
				permissions.put(perm, true);
			}
			
		}
		
		// Load users groups
		for (int i = 0; i < user_groups.size(); i++) {
			String group = (String) user_groups.get(i);
			
			groups.put(group, group);
			
		}
		
		// add user to the userSet
		userSet.put(user, new PermissionUser(user, permissions, groups));

	}
	
	public static void doReload() {
		
	}
}
