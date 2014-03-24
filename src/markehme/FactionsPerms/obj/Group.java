package markehme.FactionsPerms.obj;

import java.util.HashMap;

public class Group {
	public HashMap<String, Boolean> Permissions_Global			= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Current			= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Ally 			= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Neutral 		= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Enemy 			= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Safezone 		= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Warzone 		= new HashMap<String, Boolean>();
	public HashMap<String, Boolean> Permissions_Wilderness		= new HashMap<String, Boolean>();
		
	public Group(String name,
			HashMap<String, Boolean> Perms_Global,
			HashMap<String, Boolean> Perms_Current,
			HashMap<String, Boolean> Perms_Ally,
			HashMap<String, Boolean> Perms_Neutral,
			HashMap<String, Boolean> Perms_Enemy,
			HashMap<String, Boolean> Perms_Safezone,
			HashMap<String, Boolean> Perms_Warzone, 
			HashMap<String, Boolean> Perms_Wilderness) {
		
		Permissions_Global				= Perms_Global;
		Permissions_Current				= Perms_Current;
		Permissions_Ally				= Perms_Ally;
		Permissions_Neutral				= Perms_Neutral;
		Permissions_Enemy				= Perms_Enemy;
		Permissions_Safezone			= Perms_Safezone;
		Permissions_Warzone				= Perms_Warzone;
		Permissions_Wilderness			= Perms_Wilderness;
		
	}
}
