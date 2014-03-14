package markehme.FactionsPerms;

import markehme.FactionsPerms.commands.*;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FCommand;

public class FactionsPermsCommandSetup {

	public FactionsPermsCommandSetup() {
		// Command: /f permissions
		addSubCommand(new CmdPerm());
	}
	
	public void addSubCommand(FCommand subCommand) {
		Factions.get().getOuterCmdFactions().addSubCommand(subCommand);
	}
}
