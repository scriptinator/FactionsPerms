package markehme.FactionsPerms.listeners;

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
			
		}
	}
}
