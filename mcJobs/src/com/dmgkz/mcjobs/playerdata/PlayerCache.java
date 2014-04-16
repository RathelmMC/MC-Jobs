package com.dmgkz.mcjobs.playerdata;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.localization.GetLanguage;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.levels.Leveler;
import com.dmgkz.mcjobs.util.IOsaver;
import com.dmgkz.mcjobs.util.PlayerUtils;

public class PlayerCache implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2012090401L;
	private static int lastsave_timer = 15;
	private static int expired_timer = 3;

	private static HashMap<String, PlayerCache> playercache = new HashMap<String, PlayerCache>();
	private static ArrayList<String>            playerperms = new ArrayList<String>();
	
	private ArrayList<String>playerJobs;
	private HashMap<String, Integer> rejoinJobs;
	private HashMap<String, Boolean> showEveryTime;
	private HashMap<String, Double> jobexp;
	private HashMap<String, Integer> joblevel;
	private HashMap<String, String> jobrank;
	private int lastSave;
	private double earnedIncome;
	private int jobCount;
	private int allowedJobs;
	private boolean seenPitch;
	private Date dateModified;
	
	public PlayerCache(){
		playerJobs    = new ArrayList<String>();
		rejoinJobs    = new HashMap<String, Integer>();
		showEveryTime = new HashMap<String, Boolean>();
		jobexp        = new HashMap<String, Double>();
		joblevel      = new HashMap<String, Integer>();
		jobrank       = new HashMap<String, String>();
		lastSave = 0;
		earnedIncome = 0.0D;
		jobCount = 0;
		allowedJobs = PlayerUtils.getAllowed();
		seenPitch = false;
		dateModified = new Date();
	}


	public static boolean hasJob(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);

		if(checkPlayer.playerJobs.contains(job))
			return true;
		else return false;
	}
		
	public static int getJobCount(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		return checkPlayer.jobCount;
	}
	
	public static int getAllowedJobCount(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		return checkPlayer.allowedJobs;
	}
	
	public static ArrayList<String> getPlayerJobs(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		return checkPlayer.playerJobs;
	}
	
	public static boolean addJob(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.playerJobs.contains(job))
			return false;
		else{
			checkPlayer.playerJobs.add(job);

			if(!PlayerJobs.getJobsList().get(job).getData().compJob().isDefault())
				checkPlayer.jobCount = checkPlayer.jobCount + 1;
			
			checkPlayer.showEveryTime.put(job, PlayerJobs.getJobsList().get(job).getData().getShowEveryTime());
			checkPlayer.joblevel.put(job, 1);
			checkPlayer.jobexp.put(job, 0D);
			checkPlayer.jobrank.put(job, Leveler.getRank(1));
			
			savePlayerCache(player);
			
			return true;
		}
	}
	
	public static boolean removeJob(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.playerJobs.contains(job)){
			checkPlayer.playerJobs.remove(job);

			if(!PlayerJobs.getJobsList().get(job).getData().compJob().isDefault())
				checkPlayer.jobCount = checkPlayer.jobCount - 1;
			
			checkPlayer.showEveryTime.remove(job);
			checkPlayer.jobexp.remove(job);
			checkPlayer.joblevel.remove(job);
			checkPlayer.jobrank.remove(job);
			
			if(Bukkit.getPlayer(player) != null)
				Bukkit.getPlayer(player).sendMessage(McJobs.getPlugin().getLanguage().getExperience("reset").addVariables(job, player, ""));		
			
			savePlayerCache(player);
			
			return true;
		}
		else return false;
	}
	
	public static void verifyPlayerCache(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		Iterator<String> it = checkPlayer.playerJobs.iterator();
		int temp = 0;
		
		while(it.hasNext()){
			String job = it.next();
			
			if(PlayerJobs.getJobsList().containsKey(job)){
				if(!PlayerJobs.getJobsList().get(job).getData().compJob().isDefault())
					temp++;
			}
			else
				it.remove();
		}
		
		checkPlayer.jobCount = temp;
		checkPlayer.allowedJobs = PlayerUtils.getAllowed(player);
		
		if(!checkPlayer.rejoinJobs.isEmpty() && !playerperms.contains(player)){
			playerperms.add(player);
			savePlayerPerms();
			}
	}
	
	public static String getJobRank(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.jobrank.containsKey(job)){
			return checkPlayer.jobrank.get(job);
		}
		else
			return "";
	}
	
	public static boolean addLevels(String player, String job, int levels){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.playerJobs.contains(job.toLowerCase())){
			int job_level = checkPlayer.joblevel.get(job.toLowerCase());
			job_level = job_level + levels;
			
			checkPlayer.joblevel.put(job.toLowerCase(), job_level);
			return true;
		}
		
		return false;
	}
	
	public static Integer getJobLevel(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.joblevel.containsKey(job)){
			return checkPlayer.joblevel.get(job);
		}
		else
			return 0;
	}
	
	public static double getJobExp(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.jobexp.containsKey(job)){
			return checkPlayer.jobexp.get(job);
		}
		else
			return 0L;		
	}
	
	public static String getJobExpDisplay(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		String value = "0";
		
		if(checkPlayer.jobexp.containsKey(job)){
			DecimalFormat df = new DecimalFormat("#,###,###,##0.##");
			
			value = df.format(checkPlayer.jobexp.get(job));
		}
		
		return value;
	}
	
	public static boolean addExp(String player, String job, double amount){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.jobexp.containsKey(job)){
			double value = checkPlayer.jobexp.get(job) + amount;
			Integer level = checkPlayer.joblevel.get(job);
			String rank = "";
			
			
			if(Leveler.getXPtoLevel(level) <= value){
				while(Leveler.getXPtoLevel(level) <= value){
					value = (double) (value - Leveler.getXPtoLevel(level));

					level = level + 1;
					rank = Leveler.getRank(level);					
				
					checkPlayer.jobexp.put(job, value);
					checkPlayer.joblevel.put(job, level);					
					

					if(!checkPlayer.jobrank.get(job).equalsIgnoreCase(rank)){
						checkPlayer.jobrank.put(job, rank);
						
						if(Bukkit.getPlayer(player) != null)
							Bukkit.getPlayer(player).sendMessage(McJobs.getPlugin().getLanguage().getExperience("rank").addVariables(job, player, rank));

					}
					
					if(Bukkit.getPlayer(player) != null)
						Bukkit.getPlayer(player).sendMessage(McJobs.getPlugin().getLanguage().getExperience("level").addVariables(job, player, level.toString()));
				}

				savePlayerCache(player);
			}
			else
				checkPlayer.jobexp.put(job, value);
				
			return true;
		}
		return false;
	}
	
	public static boolean addReJoinTimer(String player, String job, int iTimer){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(!checkPlayer.rejoinJobs.containsKey(job)){
			checkPlayer.rejoinJobs.put(job, iTimer);
			
			if(!playerperms.contains(player)){
				playerperms.add(player);
				savePlayerPerms();
			}
		}
		else return false;
		
		return true;
	}
	
	public static boolean decrementTimer(String player){
		GetLanguage modText = McJobs.getPlugin().getLanguage();

		PlayerCache checkPlayer = getPlayerCache(player);
		boolean removePlayer = false;
		
		Iterator<Entry<String, Integer>> it = checkPlayer.rejoinJobs.entrySet().iterator();
		
		if(it.hasNext())
			checkPlayer.lastSave = checkPlayer.lastSave + 1;
		
		while(it.hasNext()){
			Entry<String, Integer> pair = it.next();
			int i = pair.getValue();
			i--;
			
			if(i < 1){
				if(Bukkit.getPlayer(player) != null){
					Player play = Bukkit.getPlayer(player);
					play.sendMessage(modText.getJobCommand("rejoin").addVariables(pair.getKey(), player, ""));
				}

				it.remove();
				PlayerCache.savePlayerCache(player);
			}
			else
				pair.setValue(i);	
		}

		if(checkPlayer.rejoinJobs.isEmpty())
			removePlayer = true;
		
		if(checkPlayer.lastSave > lastsave_timer){
			checkPlayer.lastSave = 0;
			PlayerCache.savePlayerCache(player);
		}
		
		return removePlayer;
	}

	public static boolean removeRejoinTimer(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.rejoinJobs.containsKey(job)){
			checkPlayer.rejoinJobs.remove(job);
			
			if(checkPlayer.rejoinJobs.isEmpty()){
				playerperms.remove(player);
				savePlayerPerms();
			}
			return true;
		}
		else return false;
	}
	
	public static ArrayList<String> getPlayerPerms(){
		return playerperms;
	}
	
	public static int getRejoinTime(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.rejoinJobs.containsKey(job))
			return checkPlayer.rejoinJobs.get(job);
		
		else return 0;
	}
	
	public static boolean isJoinable(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.rejoinJobs.containsKey(job))
			return false;
		
		else return true;
	}
	
	public static void setShowEveryTime(String player, String job, boolean value){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		checkPlayer.showEveryTime.put(job, value);
	}
	
	public static boolean getShowEveryTime(String player, String job){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		if(checkPlayer.showEveryTime.containsKey(job))
			return checkPlayer.showEveryTime.get(job);
		
		else return false;		
	}
	
	public static void setEarnedIncome(String player, double value){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		checkPlayer.earnedIncome = value;
	}
	
	public static double getEarnedIncome(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		return checkPlayer.earnedIncome;
	}
	
	public static boolean getSeenPitch(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		return checkPlayer.seenPitch;
	}
	
	public static void setSeenPitch(String player, boolean b){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		checkPlayer.seenPitch = b;
	}
	
	public static int getLastSave(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		return checkPlayer.lastSave;
	}
	
	public static void setLastSave(String player, int time){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		checkPlayer.lastSave = time;		
	}
	
	public static boolean savePlayerCache(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		
		checkPlayer.dateModified = new Date();
		
		try {
			IOsaver.saveFile(checkPlayer, "./plugins/mcjobs/data/" + player + ".dat");
		} catch (Exception e) {
			McJobs.getPlugin().getLogger().info("Unable to save " + player + ".dat");
			return false;
		}
		
		return true;
	}
	
	public static boolean playerExists(String player){
		if(playercache.containsKey(player))
			return true;
		
		File file = new File("./plugins/mcjobs/data/" + player + ".dat");
		
		if(file.exists())
			return true;
		
		return false;
	}
	
	public static boolean loadPlayerCache(String player){
		Logger log = McJobs.getPlugin().getLogger();
		try {
			PlayerCache checkPlayer = (PlayerCache) IOsaver.getFile("./plugins/mcjobs/data/" + player + ".dat");

			playercache.put(player, checkPlayer);
			return true;
		} 
		catch (Exception e) {
			log.info("Unable to load " + player + ".dat...  Creating file!");
			File file = new File("./plugins/mcjobs/data/" + player + ".dat");
			File dir = new File("./plugins/mcjobs/data/");
			
			if(!dir.exists()){
				log.info("Creating player data directory...");
				dir.mkdir();
			}
			try {
				file.createNewFile();
			} catch (IOException e1) {
				log.severe("Unable to create " + player + ".dat!!!");
		}
			
			return false;
		}
	}
	
	private static PlayerCache getPlayerCache(String name){
		if(playercache.containsKey(name)){
			return playercache.get(name);
		}
		else{
			if(loadPlayerCache(name)){
				return playercache.get(name);
			}
			else{
				PlayerCache newPlayer = new PlayerCache();
				playercache.put(name, newPlayer);
				return newPlayer;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadPlayerPerms(){
		File file = new File("./plugins/mcjobs/perms.db");
		Logger log = McJobs.getPlugin().getLogger();

		if(!file.exists())
			try {
				file.createNewFile();
				savePlayerPerms();	
			} 
			catch (IOException e) {
				log.info("Unable to create perms.db!");
			}
		else
			try {
				playerperms = (ArrayList<String>) IOsaver.getFile("./plugins/mcjobs/perms.db");
			} catch (Exception e) {
				log.info("Unable to load perms.db!");
			}
	}
	public static void savePlayerPerms(){
		Logger log = McJobs.getPlugin().getLogger();
		
		try {
			IOsaver.saveFile(playerperms, "./plugins/mcjobs/perms.db");
		} catch (Exception e) {
			log.info("Unable to save perms.db!");
		}
	}

	public static void removePlayerCache(String player){
		if(playercache.containsKey(player))
			playercache.remove(player);
	}
	
	public static boolean isCacheOld(String player){
		PlayerCache checkPlayer = getPlayerCache(player);
		boolean deletecache;
		
		if(checkPlayer.playerJobs.isEmpty()){
			deletecache = true;

			return deletecache;
		}
		
		if(Bukkit.getPlayer(player) != null){
			deletecache = false;
			
			return deletecache;
		}
		
		deletecache = false;
		
		Iterator<String> it = checkPlayer.playerJobs.iterator();
		
		while(it.hasNext()){
			String job = it.next();
			
			if(checkPlayer.joblevel.get(job) == 1)
				deletecache = true;
			
			if(checkPlayer.jobexp.get(job) > 0){
				deletecache = false;
				break;
			}
		}
		
		Date now = new Date();
		Date modified;
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(checkPlayer.dateModified);
		cal.add(Calendar.DATE, expired_timer);
		
		modified = cal.getTime();
		
		if(modified.before(now)){
			deletecache = true;
		}
		
		return deletecache;
	}
	
	public static void setLastSaveTimer(int i){
		if(i > 0)
			lastsave_timer = i;
		else
			lastsave_timer = 15;
	}

	public static void setExpired(int i){
		if(i > 0)
			expired_timer = i;
		else
			expired_timer = 90;
	}
}
