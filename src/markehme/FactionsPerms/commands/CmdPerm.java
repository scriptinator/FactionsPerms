package markehme.FactionsPerms.commands;

import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;

public class CmdPerm extends FCommand {
	public CmdPerm() {
		this.aliases.add( "permissions" );
		
		this.errorOnToManyArgs = false;
		
		this.addRequirements( ReqFactionsEnabled.get() );
		
		this.setHelp( "manages FactionsPerms" );
		
	}

	@Override
	public void perform() {
			
		
			
	}
}