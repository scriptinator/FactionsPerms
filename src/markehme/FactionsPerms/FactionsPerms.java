package markehme.FactionsPerms;

import java.util.HashMap;
import java.util.Map;

import markehme.FactionsPerms.obj.FPermGroup;
import markehme.FactionsPerms.obj.Group;
import markehme.FactionsPerms.obj.PermissionUser;
import markehme.FactionsPerms.utilities.FType;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.ps.PS;

/**
 * Welcome to the delicious code of FactionsPerms! Highly un-documented (sorry, I do try sometimes).
 * @author MarkehMe <mark@markeh.me>
 * @url http://dev.bukkit.org/bukkit-plugins/factionsperms/
 *
 */
public class FactionsPerms extends JavaPlugin {
	
	public static Map<String, Group> permissionsSet = new HashMap<>();
	public static Map<String, PermissionUser> userSet = new HashMap<>();
	
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
		
		// Create commands 
		new FactionsPermsCommandSetup();
		
		// tests 
		permissionsSet.put("Default", new Group("Default", null, null, null, null, null, null, null, null));
		
		permissionsSet.get("Default").Permissions_Ally.containsKey("factionsplus.reload");
	}
	
	/**
	 * Method to check a permission for a group in an area
	 * @param group
	 * @param permission
	 * @param in
	 * @return
	 */
	public static boolean hasGroupPermission(String group, String permission, FPermGroup in) {
		Group wGroup = permissionsSet.get(group);
		
		switch(in) {
		case Ally:
			if(wGroup.Permissions_Ally.containsKey(permission)) {
				return(wGroup.Permissions_Ally.get(permission));
			}
			
			return false;
			
		case Current:
			if(wGroup.Permissions_Current.containsKey(permission)) {
				return(wGroup.Permissions_Current.get(permission));
			}
			
			return false;
			
		case Enemy:
			if(wGroup.Permissions_Enemy.containsKey(permission)) {
				return(wGroup.Permissions_Enemy.get(permission));
			}
			
			return false;
			
		case Global:
			if(wGroup.Permissions_Global.containsKey(permission)) {
				return(wGroup.Permissions_Global.get(permission));
			}
			
			return false;
			
		case Neutral:
			if(wGroup.Permissions_Neutral.containsKey(permission)) {
				return(wGroup.Permissions_Neutral.get(permission));
			}
			
			return false;
			
		case Safezone:
			if(wGroup.Permissions_Safezone.containsKey(permission)) {
				return(wGroup.Permissions_Safezone.get(permission));
			}
			
			return false;
			
		case Warzone:
			if(wGroup.Permissions_Warzone.containsKey(permission)) {
				return(wGroup.Permissions_Warzone.get(permission));
			}
			
			return false;
			
		case Wilderness:
			if(wGroup.Permissions_Wilderness.containsKey(permission)) {
				return(wGroup.Permissions_Wilderness.get(permission));
			}
			
			return false;
			
		}
		return false;
	}
	
	/**
	 * Method provides a way to check permission for a player
	 * @param player
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(String player, String permission) {
		UPlayer uPlayer = UPlayer.get(player);
		
		permission = permission.toLowerCase(); // permissions are not case-sensitive
		
		// First, check the player defined values. These override all permissions
		if(userSet.get(player).validPerms.containsKey(permission)) {
			return(userSet.get(player).validPerms.get(permission)); // will return the valid boolean
		}
		
		Faction factionLandIn = BoardColls.get().getFactionAt(PS.valueOf(Bukkit.getPlayer(player)));
		FType factionType = FType.valueOf(factionLandIn);
		
		
		if(factionType.equals(FType.FACTION)) {
			
			Rel relation = uPlayer.getRelationTo(factionLandIn);
			
			if(relation.equals(Rel.LEADER) || relation.equals(Rel.OFFICER) || relation.equals(Rel.MEMBER) || relation.equals(Rel.RECRUIT)) {
				// current
			} else if(relation.equals(Rel.ALLY)) {
				// ally
			} else if(relation.equals(Rel.ENEMY)) {
				// enemy 				
			} else if(relation.equals(Rel.TRUCE) || relation.equals(Rel.NEUTRAL)) {
				// neutral 
			}
			
		} else if(factionType.equals(FType.SAFEZONE)) {
			
		} else if(factionType.equals(FType.WARZONE)) {
			
		} else if(factionType.equals(FType.WILDERNESS)) {
			
		}
		return false;
	}
	

}
