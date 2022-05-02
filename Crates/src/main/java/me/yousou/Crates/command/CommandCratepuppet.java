package me.yousou.Crates.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.yousou.Crates.Core;
import me.yousou.Crates.crate.puppet.CratePuppet;

public class CommandCratepuppet implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length==1) {
				Core.getPlugin(Core.class).test.animate();
			}else {
			Player p = (Player)sender;
			Core.getPlugin(Core.class).test = new CratePuppet(p.getLocation());
			p.sendMessage("Created crate puppet");
		}
		}
		return true;
	}

}
