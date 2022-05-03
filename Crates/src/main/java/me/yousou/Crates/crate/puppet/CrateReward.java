package me.yousou.Crates.crate.puppet;

import java.util.List;

import org.bukkit.Material;

public class CrateReward {

	private int chance;
	private List<String> commands;
	private Material item;
	private String text;
	
	public CrateReward(int chance, List<String> commands, Material item, String text) {
		this.chance = chance;
		this.commands = commands;
		this.item = item;
		this.text = text;
	}

	public int getChance() {
		return chance;
	}

	public List<String> getCommands() {
		return commands;
	}

	public Material getItem() {
		return item;
	}

	public String getText() {
		return text;
	}
	
	
}
