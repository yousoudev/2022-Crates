package me.yousou.Crates.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

	private ArmorStand as;
	
	public Hologram(String text, Location loc) {
		as = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		
		as.setInvisible(true);
		as.setInvulnerable(true);
		as.setGravity(false);
		as.setBasePlate(false);
		as.setRemoveWhenFarAway(false);
		as.setCustomName(text);
		as.setCustomNameVisible(true);
	}
	
	public ArmorStand getArmorStand(){
		return as;
	}
	
	public Location getLocation() {
		return as.getLocation();
	}
	
	public void setText(String text) {
		as.setCustomName(text);
	}
	
	public void destroy() {
		as.setHealth(0);
		as.remove();
	}
}
