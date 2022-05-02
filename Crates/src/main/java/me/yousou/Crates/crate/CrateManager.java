package me.yousou.Crates.crate;

public class CrateManager {

	private CrateData cd;
	
	public CrateManager() {
		cd = new CrateData();
	}
	
	public void save() {
		cd.save();
	}
}
