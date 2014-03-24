package markehme.FactionsPerms;

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
	public boolean hasPermission(String player, String permission) {
		UPlayer uPlayer = UPlayer.get(player); // Get the Players UPlayer object
		
		permission = permission.toLowerCase(); // permissions are not case-sensitive
		
		PermissionUser permuser = FactionsPerms.userSet.get(player);
		
		// First, check the player defined values. These override all permissions
		if(permuser.validPerms.containsKey(permission)) {
			FactionsPerms.userSet.get(player);
			return(permuser.validPerms.get(permission)); // will return the valid boolean
		}
		
		Boolean result = false; // default result value
	
		FactionsPerms.userSet.get(player);
		// loop through each group
		for(String currentGroup : permuser.inGroups.keySet()) {
			if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Global.containsKey(permission)) {
				// each group can override another group - top to bottom
				result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Global.get(permission);
			}
		}
		
		// Work out what sort of land they're in
		Faction factionLandIn = BoardColls.get().getFactionAt(PS.valueOf(Bukkit.getPlayer(player)));
		FType factionType = FType.valueOf(factionLandIn);
		
		// loop through each group
		for( String currentGroup : permuser.inGroups.keySet()) {
			if(factionType.equals(FType.FACTION)) {
				
				// Get relationship to the land they're in
				Rel relation = uPlayer.getRelationTo(factionLandIn);
				
				if(relation.equals(Rel.LEADER) || relation.equals(Rel.OFFICER) || relation.equals(Rel.MEMBER) || relation.equals(Rel.RECRUIT)) {
					// In own land, so apply current specific permissions 
					if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Current.containsKey(permission)) {
						result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Current.get(permission);
					}
				} else if(relation.equals(Rel.ALLY)) {
					// In Ally land, apply ally-specific permissions 
					if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Ally.containsKey(permission)) {
						result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Ally.get(permission);
					}
				} else if(relation.equals(Rel.ENEMY)) {
					// In enemy land, apply enemy-specific permissions 
					if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Enemy.containsKey(permission)) {
						result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Enemy.get(permission);
					}			
				} else if(relation.equals(Rel.TRUCE) || relation.equals(Rel.NEUTRAL)) {
					// In neutral land, so apply neutral-specific permissions 
					if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Neutral.containsKey(permission)) {
						result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Neutral.get(permission);
					}	
				}
				
			} else if(factionType.equals(FType.SAFEZONE)) {
				// In a SafeZone so apply SafeZone permissions 
				if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Safezone.containsKey(permission)) {
					result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Safezone.get(permission);
				}
			} else if(factionType.equals(FType.WARZONE)) {
				// In a WarZone so apply WarZone permissions 
				if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Warzone.containsKey(permission)) {
					result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Warzone.get(permission);
				}
			} else if(factionType.equals(FType.WILDERNESS)) {
				// In wilderness, apply widlerness based permissions 
				if(FactionsPerms.permissionsSet.get(currentGroup).Permissions_Wilderness.containsKey(permission)) {
					result = FactionsPerms.permissionsSet.get(currentGroup).Permissions_Wilderness.get(permission);
				}
			}
		}	
		
		// Return the result
		return result;
	}
	
}
