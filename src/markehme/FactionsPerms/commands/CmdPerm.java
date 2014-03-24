package markehme.FactionsPerms.commands;

import markehme.FactionsPerms.FactionsPermsAPI;

import org.bukkit.ChatColor;

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
		FactionsPermsAPI api = new FactionsPermsAPI();
		

		
		if(args.size() == 1) {
			if(args.get(0).equals("reload")) {
				
				if(!api.hasPermission(usender.getName(), "factionsperms.reload") && !usender.isConsole()) {
					msg(ChatColor.RED + "You do not have the required permission to run this command.");
					return;
				}
				
				msg("reload commencing ... ");
				return;
			}
			
			if(args.get(0).equals("ihave")) {
				if(!api.hasPermission(usender.getName(), "factionsperms.ihave") && !usender.isConsole()) {
					msg(ChatColor.RED + "You do not have the required permission to run this command.");
					return;
				}
				msg(ChatColor.DARK_RED + "Please specificy the permission!");
				return;
			}
			
		}
		
		if(args.size() > 1) {
			if(args.get(0).equals("ihave")) {
				if(!api.hasPermission(usender.getName(), "factionsperms.ihave") && !usender.isConsole()) {
					msg(ChatColor.RED + "You do not have the required permission to run this command.");
					return;
				}
				
				if(api.hasPermission(usender.getName(), args.get(1))) {
					msg(ChatColor.GREEN + args.get(1) + " - yes");
				} else {
					msg(ChatColor.RED   + args.get(1) + " - no");
				}
				
				return;
			}
			
			if(args.get(0).equals("exec")) {
				/*	
					
					Set MarkehMe's group to vip and add the permission factions.admin
					/f permissions exec u:MarkeMe setgroup:vip addperm:factions.admin
					
					Add permissions warp.test and warp.example to Notch, Ben, and Jerry
					/f permissions exec u:notch,ben,jerry addperm:warp.test,warp.example
					
					For group default in world creative add permission worldedit.wand
					/f permission exec g:default w:creative addperm:worldedit.wand
					
					For group default in the world hardcore in the area warzone remove permissions essentials.feed, and essentials.fly
					/f permission exec g:default w:hardcore a:warzone removeperm:-essentials.feed,-essentials.fly
					
				*/
				 
			}
		}
			
	}
}