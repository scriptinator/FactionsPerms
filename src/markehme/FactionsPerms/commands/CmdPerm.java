package markehme.FactionsPerms.commands;

import markehme.FactionsPerms.FactionsPerms;
import markehme.FactionsPerms.FactionsPermsAPI;

import org.bukkit.ChatColor;

import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.cmd.req.ReqFactionsEnabled;

public class CmdPerm extends FCommand {
	public CmdPerm() {
		this.aliases.add( "permissions" );
		
		this.errorOnToManyArgs = false;
		
		this.addRequirements( ReqFactionsEnabled.get() );
		
		this.setHelp( "manage FactionsPerms" );
		
	}

	@Override
	public void perform() {
		// Fetch FactionsPerms API
		FactionsPermsAPI api = new FactionsPermsAPI();
		
		// I'll admit everything in here is done badly and I will re-do it to be much clearner - in the future. 
		
		if(args.size() == 0) {
			msg(ChatColor.RED + "Invalid subcommand!");
			msg(ChatColor.WHITE + "Subcommands:");
			msg(ChatColor.WHITE + " - /f permissions reload");
			msg(ChatColor.WHITE + " - /f permissions ihave <permission>");
			return;
		}
		
		if(args.size() == 1) {
			if(args.get(0).equals("reload")) {
				
				if(!api.hasPermission(usender.getName(), "factionsperms.reload") && !usender.isConsole()) {
					msg(ChatColor.RED + "You do not have the required permission to run this command.");
					return;
				}
				
				FactionsPerms.doReload();
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
			
			msg(ChatColor.DARK_RED + "Unknown subcommand!");
			
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
			
			msg(ChatColor.DARK_RED + "Unknown subcommand!");
		}
			
	}
}