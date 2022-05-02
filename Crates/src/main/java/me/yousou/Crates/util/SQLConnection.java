package me.yousou.Crates.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import me.yousou.Crates.Core;

public class SQLConnection {

	private Core instance = Core.getPlugin(Core.class);
	private Connection conn;
	
	public SQLConnection(String database, String port, String username, String password, String host) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			instance.getLogger().info("MySQL Connection has been established");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				refreshConnection();
			}
		}.runTaskTimer(instance, 60000L, 60000L);
	}
	
	private void refreshConnection() {
		PreparedStatement st = null;
		ResultSet valid = null;
		try
		{
		st = conn.prepareStatement("SELECT 1 FROM Dual");
		valid = st.executeQuery();
		if (valid.next())
		return;
		} catch (SQLException e2)
		{
		System.out.println("Connection is idle or terminated. Reconnecting...");
		} finally
		{
			
		}
		}
	
	public void executeStatement(String query) {
		PreparedStatement stat = prepareStatement(query);
		try {
			stat.executeUpdate();
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		refreshConnection();
		try {
			PreparedStatement stat = (PreparedStatement) conn.prepareStatement(query);
			return stat;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}