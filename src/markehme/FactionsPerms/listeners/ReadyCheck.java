package markehme.FactionsPerms.listeners;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

import markehme.FactionsPerms.FactionsPerms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class ReadyCheck implements Listener {
	
	/**
	 * Checks if FactionsPerms is ready 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onLogin(PlayerLoginEvent event) {
		if(!FactionsPerms.isReady) {
			event.disallow(Result.KICK_OTHER, "Server is still starting up.. please try again.");
			return;
		}
		
		if(!FactionsPerms.userSet.containsKey(event.getPlayer().getName().toLowerCase())) {
			FactionsPerms.get().getLogger().log(Level.INFO, "Adding " + event.getPlayer().getName() + " to default group");
			FactionsPerms.usersConfig.set("Users." + event.getPlayer().getName() + ".groups", Arrays.asList("default"));
			FactionsPerms.usersConfig.set("Users." + event.getPlayer().getName() + ".permissions", Arrays.asList(""));
			
			FactionsPerms.loadUser(event.getPlayer().getName());
			
			try {
				FactionsPerms.usersConfig.save(FactionsPerms.usersConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
