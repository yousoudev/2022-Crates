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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import me.yousou.Crates.Core;
import me.yousou.Crates.crate.CrateManager;
import me.yousou.Crates.hologram.Hologram;

public class CratePuppet implements Listener{

	private ArmorStand as;
	private boolean active = false;
	private ItemStack item = null;
	private CrateManager cm;
	
	public CratePuppet(Location loc, CrateManager cm) {
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
		this.cm = cm;
	}
	
	public void destroy() {
		as.setHealth(0);
		as.remove();
	}
	
	public void animate(Player p) {
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
						reward(p);
					}
				}
			}.runTaskTimer(Core.getPlugin(Core.class), 2L, 2L);
		}
	}
	
	private void reward(Player p) {
		active = false;
		as.setLeftArmPose(new EulerAngle(0, 0, 0));
		as.setRightArmPose(new EulerAngle(-0.5, -0.5, 0));
	    Block b = as.getLocation().getBlock().getRelative(as.getFacing());
	    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_PLACE, 1F, 1F);
		b.setType(Material.CHEST);
		as.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
		Chest c = (Chest)b.getState();
		Location loc = new Location(c.getWorld(), c.getX()+.5, c.getY(), c.getZ()+.5);
		b.getWorld().spawnParticle(Particle.NAUTILUS, loc, 100, 0, 1, 0);
		BlockData chest = b.getBlockData();
		 if (chest instanceof Directional) {
		        ((Directional) chest).setFacing(as.getFacing());
		        b.setBlockData(chest);
		    }
		 
		 CrateReward cr = cm.getRandom();
		 new BukkitRunnable() {
			 int state = 0;
			 Hologram h;
			 Item ifs;
			 public void run() {
				 state++;
				 if(state==18) {
					 b.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 100, 0, 1, 0);
				 }
				 if(state==20) {
					 c.open();
					 ItemStack ite = new ItemStack(cr.getItem());
					 item = ite;
					 ifs = c.getWorld().dropItem(new Location(c.getWorld(), c.getX()+.5, c.getY()+1, c.getZ()+.5), ite);
					 ifs.setVelocity(new Vector(0,-1,0));
					 h= Core.getPlugin(Core.class).hm.create(cr.getText(), new Location(c.getWorld(), c.getX()+.5, c.getY()-0.7, c.getZ()+.5));
					 p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
				 }
				 if(state>21&&state<31) {
					 double pitch = 0.8-0.08*(state-21);
					 as.setHeadPose(new EulerAngle(pitch, 0, 0));
				 }
				 if(state==80) {
				 c.close();
				 ifs.remove();
				 item = null;
				 Core.getPlugin(Core.class).hm.destroy(h);
				 for(String cmd : cr.getCommands()) {
					 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", p.getName()));
				 }
				 }
				 if(state==100) {
					 this.cancel();
					 b.getWorld().spawnParticle(Particle.FLASH, loc, 100, 0, 1, 0);
					 p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_BREAK, 1F, 1F);
					 b.setType(Material.AIR);
					 active=false;
					 as.getEquipment().setItemInMainHand(new ItemStack(Material.CHEST));
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
