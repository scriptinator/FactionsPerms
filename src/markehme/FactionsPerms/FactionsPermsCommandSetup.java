package markehme.FactionsPerms;

import java.util.logging.Level;

import markehme.FactionsPerms.commands.*;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FCommand;

public class FactionsPermsCommandSetup {

	public FactionsPermsCommandSetup() {
		// Command: /f permissions
		FactionsPerms.get().getLogger().log(Level.INFO, "Hooking command ... ");
		addSubCommand(new CmdPerm());
		FactionsPerms.get().getLogger().log(Level.INFO, "Command hooked! Ready.");
	}
	
	public void addSubCommand(FCommand subCommand) {
		try {
			Factions.get().getOuterCmdFactions().addSubCommand(subCommand);
		} catch(Exception e) {
			FactionsPerms.get().getLogger().log(Level.INFO, "Apparently factions was ready, but we still couldn't add the permissons subcommand?");
		}
	}
}
