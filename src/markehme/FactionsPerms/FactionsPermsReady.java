package markehme.FactionsPerms;

import markehme.FactionsPerms.utilities.Metrics;
import markehme.FactionsPerms.utilities.Metrics.Graph;

import org.bukkit.Bukkit;
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
				
		new FactionsPermsCommandSetup();
		
		Graph FactionsVersion = FactionsPerms.metrics.createGraph("Factions Version");
		
		FactionsVersion.addPlotter(new Metrics.Plotter(Bukkit.getPluginManager().getPlugin("Factions").getDescription().getVersion()) {
			@Override
			public int getValue() {
				return 1;
			}
		});
		
		Graph MCoreVersion = FactionsPerms.metrics.createGraph("MCore Version");
		
		MCoreVersion.addPlotter(new Metrics.Plotter(Bukkit.getPluginManager().getPlugin("mcore").getDescription().getVersion()) {
			@Override
			public int getValue() {
				return 1;
			}
		});
		
		FactionsPerms.metrics.start();
			
		FactionsPerms.isReady = true;
		cancel();
		
	}
}
