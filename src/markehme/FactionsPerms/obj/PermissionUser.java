package markehme.FactionsPerms.obj;

import java.util.Map;
import java.util.Map.Entry;

import markehme.FactionsPerms.FactionsPerms;

public class PermissionUser {
	public Map<String, Boolean> validPerms;
	public Map<String, Group> inGroups;
	
	public PermissionUser(String PlayerName, Map<String, String> playerpermissions, Map<String, String> groups) {
		
		// Store all groups 
		for(Entry<String, Group> entry : FactionsPerms.permissionsSet.entrySet()) {
			//String key = entry.getKey();
			inGroups.put(entry.getKey(), entry.getValue());
		}
		
		// Now apply user specific permissions 
		for(Entry<String, String> entry : playerpermissions.entrySet()) {
			//String key = entry.getKey();
			String value = entry.getValue().toLowerCase();
			
			// Remove any existing value 
			validPerms.remove(value);
			
			if(value.startsWith("-")) {
				// is a negative permission, so make it a false value 
				validPerms.put(value.substring(1), false);
			} else {
				// is a normal permission, so make a true value 
				validPerms.put(value, true);
			}
		}
	}	
}
