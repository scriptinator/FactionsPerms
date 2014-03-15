package markehme.FactionsPerms;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FactionsPermsReady extends BukkitRunnable {
	private final JavaPlugin plugin;
	
	public FactionsPermsReady(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		if(!plugin.getServer().getPluginManager().isPluginEnabled("Factions")) return;
		
		FactionsPerms.get().getLogger().log(Level.INFO, "Factions found");
		
		new FactionsPermsCommandSetup();
		
		FactionsPerms.isReady = true;
		cancel();
		
	}
}
