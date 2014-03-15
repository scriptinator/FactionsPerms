package markehme.FactionsPerms.listeners;

import markehme.FactionsPerms.FactionsPerms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class ReadyCheck implements Listener {
	
	/**
	 * Removes the PlayerLoginCheck
	 */
	public void removePlayerLoginCheck() {
		PlayerLoginEvent.getHandlerList().unregister(FactionsPerms.get());
	}
	
	/**
	 * Checks if FactionsPerms is ready 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onLogin(PlayerLoginEvent event) {
		if(!FactionsPerms.isReady) {
			event.disallow(Result.KICK_OTHER, "Server is still starting up.. please try again.");
		} else {
			this.removePlayerLoginCheck();
		}
	}
}
