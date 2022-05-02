package me.yousou.Crates.hologram;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class HologramManager {

	private List<Hologram> holograms = new ArrayList<>();
	
	public void destroyAll() {
		for(Hologram h : holograms) {
			h.destroy();
		}
	}
	
	public void destroy(Location loc) {
		for(Hologram h : holograms) {
			if(h.getLocation().equals(loc)) {
				h.destroy();
			}
		}
	}
	
	public void create(String text, Location loc) {
		holograms.add(new Hologram(text, loc));
	}
}
