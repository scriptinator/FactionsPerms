package markehme.FactionsPerms.obj;

import java.util.HashMap;
import java.util.Map.Entry;

import markehme.FactionsPerms.FactionsPerms;

/**
 * Each user gets one of these that is defined in the permissions.yml file.
 * These are stored in FactionsPerms.userSet. 
 * @author MarkehMe <mark@markeh.me>
 *
 */
public class PermissionUser {
	public HashMap<String, Boolean> validPerms;
	public HashMap<String, Group> inGroups;
	
	public PermissionUser(String PlayerName, HashMap<String, String> playerpermissions, HashMap<String, String> groups) {
		
		// Store all groups 
		for(Entry<String, Group> entry : FactionsPerms.permissionsSet.entrySet()) {
			//String key = entry.getKey();
			inGroups.put(entry.getKey(), entry.getValue());
		}
		
		// Now apply user specific permissions 
		for(Entry<String, String> entry : playerpermissions.entrySet()) {
			//String key = entry.getKey();
			String value = entry.getValue().toLowerCase();
			
			if(value.startsWith("-")) {
				// Remove existing permission
				validPerms.remove(value.substring(1));
				
				// is a negative permission, so make it a false value 
				validPerms.put(value.substring(1), false);
			} else {
				// Remove existing permission
				validPerms.remove(value);
				
				// is a normal permission, so make a true value 
				validPerms.put(value, true);
			}
		}
	}	
}
