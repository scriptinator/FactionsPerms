package markehme.FactionsPerms;

import java.util.logging.Level;

import markehme.FactionsPerms.commands.*;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FCommand;

public class FactionsPermsCommandSetup {
	
	public static FactionsPermsAPI api;
	
	public FactionsPermsCommandSetup() {
		// Command: /f permissions
		addSubCommand(new CmdPerm());
		FactionsPerms.get().getLogger().log(Level.INFO, "Factions found and hooked.");
	}
	
	public void addSubCommand(FCommand subCommand) {
		try {
			Factions.get().getOuterCmdFactions().addSubCommand(subCommand);
		} catch(Exception e) {
			FactionsPerms.get().getLogger().log(Level.INFO, "Apparently factions was ready, but we still couldn't add the permissons subcommand?");
		}
	}
}
