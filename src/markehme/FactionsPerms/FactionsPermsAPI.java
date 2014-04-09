package markehme.FactionsPerms;

import java.util.Map.Entry;
import java.util.logging.Level;

import markehme.FactionsPerms.obj.FPermGroup;
import markehme.FactionsPerms.obj.Group;
import markehme.FactionsPerms.obj.PermissionUser;
import markehme.FactionsPerms.utilities.FType;

import org.bukkit.Bukkit;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.ps.PS;


/**
 * Simply added this to make a reference to API
 * so there would be no reference issues 
 * @author markhughes
 *
 */
public class FactionsPermsAPI {
	/**
	 * Method to check a permission for a group in an area
	 * @param group
	 * @param permission
	 * @param in
	 * @return
	 */
	public boolean hasGroupPermission(String group, String permission, FPermGroup in) {
		Group wGroup = FactionsPerms.permissionsSet.get(group);
		
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
	public boolean playerHas(String player, String permission, String world) {
		
		permission = permission.toLowerCase(); // permissions are not case-sensitive
		
		PermissionUser permuser = FactionsPerms.userSet.get(player);
		
		// See if we fetched the player
		if(permuser == null) {
			FactionsPerms.get().getLogger().log(Level.INFO, "(odd) user " + player + " was not found in the userSet");
			return false;
		}
		
		// First, check the player defined values. These override all permissions
		if(permuser.validPerms.containsKey(permission)) {
			FactionsPerms.userSet.get(player);
			return(permuser.validPerms.get(permission)); // will return the valid boolean
		}
		
		Boolean result = false; // default result value
		
		// loop through each group
		for(String group : permuser.inGroups.keySet()) {
			if(FactionsPerms.permissionsSet.get(group).Permissions_Global.containsKey(permission)) {
				// each group can override another group - top to bottom
				result = FactionsPerms.permissionsSet.get(group).Permissions_Global.get(permission);
			}
		}
		
		// Get the Players uplayer object
		UPlayer uplayer = UPlayer.get(player); 
		
		// Work out what sort of land they're in
		
		// loop through each group
		for(String group : permuser.inGroups.keySet()) {
			if(FactionsPerms.permissionsSet.get(group) == null) {
				FactionsPerms.get().getLogger().log(Level.INFO, "(odd) " + group + " is not in the permissionsSet");
				break;
			}
			
			Boolean gTest = groupHas(uplayer, group, permission);
			
			if(gTest) {
				result = gTest;
			}
			
		}
		
		// Return the result
		return result;
	}
	
	public boolean groupHas(UPlayer uplayer, String group, String permission) {
		Faction factionLandIn = BoardColls.get().getFactionAt(PS.valueOf(Bukkit.getPlayer(uplayer.getName())));
		FType factionType = FType.valueOf(factionLandIn);

		Boolean result = false;
		
		Group workingGroup = FactionsPerms.permissionsSet.get(group);
		
		if(factionType.equals(FType.FACTION)) {
			
			// Get relationship to the land they're in
			Rel relation = uplayer.getRelationTo(factionLandIn);
			
			if(relation.equals(Rel.LEADER) || relation.equals(Rel.OFFICER) || relation.equals(Rel.MEMBER) || relation.equals(Rel.RECRUIT)) {
				// In own land, so apply current specific permissions 
				if(workingGroup.Permissions_Current.containsKey(permission)) {
					result = workingGroup.Permissions_Current.get(permission);
				}
			} else if(relation.equals(Rel.ALLY)) {
				// In Ally land, apply ally-specific permissions 
				if(workingGroup.Permissions_Ally.containsKey(permission)) {
					result = workingGroup.Permissions_Ally.get(permission);
				}
			} else if(relation.equals(Rel.ENEMY)) {
				// In enemy land, apply enemy-specific permissions 
				if(workingGroup.Permissions_Enemy.containsKey(permission)) {
					result = workingGroup.Permissions_Enemy.get(permission);
				}			
			} else if(relation.equals(Rel.TRUCE) || relation.equals(Rel.NEUTRAL)) {
				// In neutral land, so apply neutral-specific permissions 
				if(workingGroup.Permissions_Neutral.containsKey(permission)) {
					result = workingGroup.Permissions_Neutral.get(permission);
				}	
			}
		} else if(factionType.equals(FType.SAFEZONE)) {
			// In a SafeZone so apply SafeZone permissions 
			if(workingGroup.Permissions_Safezone.containsKey(permission)) {
				result = workingGroup.Permissions_Safezone.get(permission);
			}
		} else if(factionType.equals(FType.WARZONE)) {
			// In a WarZone so apply WarZone permissions 
			if(workingGroup.Permissions_Warzone.containsKey(permission)) {
				result = workingGroup.Permissions_Warzone.get(permission);
			}
		} else if(factionType.equals(FType.WILDERNESS)) {
			// In wilderness, apply wilderness based permissions 
			if(workingGroup.Permissions_Wilderness.containsKey(permission)) {
				result = workingGroup.Permissions_Wilderness.get(permission);
			}
		}
		
		// Now go through inherited groups 
		if(!workingGroup.inheritsGroups.isEmpty()) {
			for(Entry<String, Group> currentGroup : workingGroup.inheritsGroups.entrySet()) {
				Boolean secondResult = groupHas(uplayer, currentGroup.getKey(), permission);
				
				if(secondResult) {
					result = secondResult;
				}
			}
		}
		
		return result;
	}
}
