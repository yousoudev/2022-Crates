package me.yousou.Crates.crate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.yousou.Crates.Core;

public class CrateData {

	private HashMap<UUID, Integer> crates = new HashMap<UUID, Integer>();
	private File data = new File(Core.getPlugin(Core.class).getDataFolder(), "data.yml");
	
	public CrateData() {
		if(data.exists()) {
			FileConfiguration conf = YamlConfiguration.loadConfiguration(data);
			for(String uuid : conf.getKeys(false)) {
				crates.put(UUID.fromString(uuid), conf.getInt(uuid));
			}
		}
	}
	
	public void save() {
		if(!data.exists()) {
			
			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileWriter(data));
				for(Entry<UUID, Integer> crate : crates.entrySet()) {
					pw.println(crate.getKey().toString() + ": " + crate.getValue());
				}
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else {
			
			FileConfiguration conf = YamlConfiguration.loadConfiguration(data);
			for(Entry<UUID, Integer> crate : crates.entrySet()) {
				conf.set(crate.getKey().toString(), crate.getValue());
			}
			try {
				conf.save(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void setCrates(UUID uuid, int amount) {
		crates.put(uuid, amount);
	}
}
