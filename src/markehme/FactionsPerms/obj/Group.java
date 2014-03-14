package markehme.FactionsPerms.obj;

import java.util.Map;

public class Group {
	public Map<String, Boolean> Permissions_Global			= null;
	public Map<String, Boolean> Permissions_Current		= null;
	public Map<String, Boolean> Permissions_Ally 			= null;
	public Map<String, Boolean> Permissions_Neutral 		= null;
	public Map<String, Boolean> Permissions_Enemy 			= null;
	public Map<String, Boolean> Permissions_Safezone 		= null;
	public Map<String, Boolean> Permissions_Warzone 		= null;
	public Map<String, Boolean> Permissions_Wilderness		= null;
		
	public Group(String name,
			Map<String, Boolean> Perms_Global,
			Map<String, Boolean> Perms_Current,
			Map<String, Boolean> Perms_Ally,
			Map<String, Boolean> Perms_Neutral,
			Map<String, Boolean> Perms_Enemy,
			Map<String, Boolean> Perms_Safezone,
			Map<String, Boolean> Perms_Warzone, 
			Map<String, Boolean> Perms_Wilderness) {
		
		Permissions_Global				= Perms_Global;
		Permissions_Current				= Perms_Current;
		Permissions_Ally				= Perms_Ally;
		Permissions_Neutral				= Perms_Neutral;
		Permissions_Safezone			= Perms_Neutral;
		Permissions_Warzone				= Perms_Neutral;
		Permissions_Wilderness			= Perms_Neutral;
		
	}
}
