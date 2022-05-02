package me.yousou.Crates;

import org.bukkit.plugin.java.JavaPlugin;

import me.yousou.Crates.command.CommandCratepuppet;
import me.yousou.Crates.crate.CrateManager;
import me.yousou.Crates.crate.puppet.CratePuppet;
import me.yousou.Crates.hologram.HologramManager;

public class Core extends JavaPlugin{
	
	public HologramManager hm;
	public CrateManager cm;
	public CratePuppet test = null;
	
	@Override
	public void onEnable() {
		hm = new HologramManager(); 
		cm = new CrateManager();
		saveDefaultConfig();
		getCommand("cratepuppet").setExecutor(new CommandCratepuppet());
	}
	
	@Override
	public void onDisable() {
		hm.destroyAll();
		cm.save();
		if(test!=null) {
			test.destroy();
		}
	}

}
