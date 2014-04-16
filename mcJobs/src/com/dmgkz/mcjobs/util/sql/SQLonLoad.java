package com.dmgkz.mcjobs.util.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import com.dmgkz.mcjobs.McJobs;



public class SQLonLoad {
	private static String user = "";
	private static String pass = "";
	private static String url = "";
	
	public static void loadSQL(String user, String pass, String url){
		SQLonLoad.user = user;
		SQLonLoad.pass = pass;
		SQLonLoad.url = url;

		Connection conn = getConnection();
		Bukkit.getLogger().info(conn.toString());


		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			String url = "jdbc:mysql://localhost:3306/minesql";
			
			Statement stmt = conn.createStatement();
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "mcjobs_jobs", null);

			if(!rs.next()){
				Bukkit.getLogger().info("Test database does not exist creating!");
				stmt.executeUpdate("CREATE TABLE mcjobs_jobs(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id)," +
									"player_name VARCHAR(30) NOT NULL," +         			// Player's name
									"job_name VARCHAR(30) NOT NULL," +            		 	// Job's name
									"show_pay BOOLEAN NOT NULL DEFAULT '0'," +					// Show payment every time
									"experience DOUBLE PRECISION NOT NULL DEFAULT '0'," +		// Experience points
									"level SMALLINT UNSIGNED NOT NULL DEFAULT '0'," +			// Level in job
									"rank VARCHAR(30) NOT NULL DEFAULT 'Novice')");			// Rank in job defaults to Novice
			}
			else{
				Bukkit.getLogger().info("Database exists!");
			}
			
			Bukkit.getLogger().info(conn.toString());
			Bukkit.getLogger().info("MYSQL connection established!");
		}
		catch(Exception e){
			Bukkit.getLogger().severe("Unable to load MYSQL driver!  Or Connection Failed!");
		}

		try {
			conn.close();
		} catch (SQLException e) {
			Bukkit.getLogger().severe("Unable to close connection!");
		}
	}

	public static Connection getConnection(){
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			McJobs.getPlugin().getLogger().severe("SQL Connection failed!");
		}
		return conn;
	}
}
