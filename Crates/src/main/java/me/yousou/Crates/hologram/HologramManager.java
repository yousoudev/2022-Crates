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
	
	public Hologram create(String text, Location loc) {
		Hologram h = new Hologram(text, loc);
		holograms.add(h);
		return h;
	}
	
	public void destroy(Hologram h) {
		if(holograms.contains(h)) {
			h.destroy();
			holograms.remove(h);
		}
	}
}
