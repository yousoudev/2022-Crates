package me.yousou.Crates.crate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;

import me.yousou.Crates.crate.puppet.CrateReward;

public class CrateManager {

	private CrateData cd;
	private List<CrateReward> cr = new ArrayList<>();
	private Random r = new Random();
	
	public CrateManager() {
		cd = new CrateData();
		
		cr.add(new CrateReward(10, new ArrayList<>(Arrays.asList("give {player} diamond_sword 1")), Material.DIAMOND_SWORD, "§21x §aDiamond Sword"));
		cr.add(new CrateReward(90, new ArrayList<>(Arrays.asList("tell {player} test", "tell {player} test2")), Material.NAME_TAG, "§41x §cTest"));
	}
	
	public void save() {
		cd.save();
	}
	
	public CrateReward getRandom() {
		int i = r.nextInt(100);
		int curr = 0;
		CrateReward chosen = null;
		for(CrateReward c : cr) {
			if(i>curr&&i<c.getChance()+curr&&chosen==null) {
				chosen = c;
				curr+=c.getChance();
			}
		}
		return chosen;
	}
}
