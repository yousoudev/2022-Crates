package me.yousou.Crates.crate.puppet;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import me.yousou.Crates.Core;
import me.yousou.Crates.hologram.Hologram;

public class CratePuppet implements Listener{

	private ArmorStand as;
	private Hologram h;
	private boolean active = false;
	private ItemStack item = null;
	
	public CratePuppet(Location loc) {
		Bukkit.getPluginManager().registerEvents(this, Core.getPlugin(Core.class));
		as = (ArmorStand)loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		
		as.setInvulnerable(true);
		as.setArms(true);
		as.getEquipment().setHelmet(new ItemStack(Material.PLAYER_HEAD));
		as.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		as.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		as.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
		as.getEquipment().setItemInMainHand(new ItemStack(Material.CHEST));
		
		as.setLeftArmPose(new EulerAngle(0, 0, 0));
		as.setRightArmPose(new EulerAngle(-0.5, -0.5, 0));
		
	}
	
	public void destroy() {
		as.setHealth(0);
		as.remove();
	}
	
	public void animate() {
		if(!active) {
			active = true;
			new BukkitRunnable() {
				int state = 0;
				public void run() {
					state++;
					if(state<11) {
						double x = -0.5-(0.2*state);
						double y = -0.5+(0.1*state);
						as.setRightArmPose(new EulerAngle(x, y, 0));
					}else if(state>20&&state<31) {
						double x = -2.5+(0.2*(state-20));
						double y = 0.5-(0.1*(state-20));
						as.setRightArmPose(new EulerAngle(x, y, 0));
						if(state > 22) {
							double pitch = 0.1*(state-22);
							as.setHeadPose(new EulerAngle(pitch, 0, 0));
						}
					}
					if(state==30) {
						this.cancel();
						reward();
					}
				}
			}.runTaskTimer(Core.getPlugin(Core.class), 2L, 2L);
		}
	}
	
	private void reward() {
		active = false;
		as.setLeftArmPose(new EulerAngle(0, 0, 0));
		as.setRightArmPose(new EulerAngle(-0.5, -0.5, 0));
		as.setHeadPose(new EulerAngle(0, 0, 0));
		//double pitch = 0.8-0/0.8*(state-20);
	    Block b = as.getLocation().getBlock().getRelative(as.getFacing());
	    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_PLACE, 1F, 1F);
		b.setType(Material.CHEST);
		Chest c = (Chest)b.getState();
		b.getWorld().spawnParticle(Particle.NAUTILUS, c.getLocation(), 100, 0, 1, 0);
		BlockData chest = b.getBlockData();
		 if (chest instanceof Directional) {
		        ((Directional) chest).setFacing(as.getFacing());
		        b.setBlockData(chest);
		    }
		 
		 new BukkitRunnable() {
			 int state = 0;
			 Item ifs;
			 public void run() {
				 state++;
				 if(state==18) {
					 b.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, c.getLocation(), 100, 0, 1, 0);
				 }
				 if(state==20) {
					 c.open();
					 ItemStack ite = new ItemStack(Material.DIAMOND_SWORD);
					 item = ite;
					 ifs = c.getWorld().dropItem(new Location(c.getWorld(), c.getX(), c.getY()+1, c.getZ()), ite);
					 ifs.setVelocity(new Vector(0,-1,0));
					 Core.getPlugin(Core.class).hm.create("§21x §aDiamond Sword", new Location(c.getWorld(), c.getX(), c.getY()+1.5, c.getZ()));
				 }else if(state==60) {
				 this.cancel();
				 ifs.remove();
				 item = null;
				 }
			 }
		 }.runTaskTimer(Core.getPlugin(Core.class), 2L, 2L);
	}

	
	@EventHandler
	public void onItem(EntityPickupItemEvent e) {
		if(item != null) {
			if(e.getItem().getItemStack().equals(item)) {
				e.setCancelled(true);
			}
		}
	}

}
