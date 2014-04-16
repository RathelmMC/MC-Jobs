package com.dmgkz.mcjobs;


import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;


import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.dmgkz.mcjobs.commands.AdminCommand;
import com.dmgkz.mcjobs.commands.JobsCommand;
import com.dmgkz.mcjobs.listeners.Baking;
import com.dmgkz.mcjobs.listeners.BlockBreak;
import com.dmgkz.mcjobs.listeners.BlockPlace;
import com.dmgkz.mcjobs.listeners.Brewing;
import com.dmgkz.mcjobs.listeners.Crafting;
import com.dmgkz.mcjobs.listeners.Enchanting;
import com.dmgkz.mcjobs.listeners.Fishing;
import com.dmgkz.mcjobs.listeners.MCListeners;
import com.dmgkz.mcjobs.listeners.McmmoRepairListener;
import com.dmgkz.mcjobs.listeners.MobKill;
import com.dmgkz.mcjobs.listeners.OnPlayerLogins;
import com.dmgkz.mcjobs.listeners.OnPlayerQuit;
import com.dmgkz.mcjobs.listeners.ShearEvent;
import com.dmgkz.mcjobs.listeners.mcjobs.JobChangeListener;
import com.dmgkz.mcjobs.localization.GetLanguage;
import com.dmgkz.mcjobs.logging.BlockLoggers;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.levels.Leveler;
import com.dmgkz.mcjobs.playerjobs.pay.PayMoney;
import com.dmgkz.mcjobs.playerjobs.pay.PayXP;
import com.dmgkz.mcjobs.scheduler.McJobsFilePrune;
import com.dmgkz.mcjobs.scheduler.McJobsNotify;
import com.dmgkz.mcjobs.scheduler.McJobsComp;
import com.dmgkz.mcjobs.scheduler.McJobsPreComp;
import com.dmgkz.mcjobs.scheduler.McJobsRemovePerm;
import com.dmgkz.mcjobs.util.PlayerUtils;
import com.dmgkz.mcjobs.util.sql.SQLonLoad;
import com.palmergames.bukkit.towny.Towny;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;


public class McJobs extends JavaPlugin {
	
	private static McJobs mcJobs;
	private Logger log;
	private JobsCommand jobscommand;
	private AdminCommand admincommand;
	private Long time = 72000L;
	private Long notify= 72000L;
	private Integer version = 0;
	private String localization = "english";
	
	private boolean bLogBlock = false;
	private boolean bPrune = false;
	private boolean bQuit = false;
	
	private static WorldGuardPlugin wgp = null;
	private static Economy economy = null;
	private static Towny towny = null;
	
	public static final int VERSION = 3100; 
	
	private GetLanguage language;
	private BlockLoggers blocklogger;
	private Metrics metrics;
	
	@Override
	public void onEnable() {		
		mcJobs = this;
		log = this.getLogger();
		language = new GetLanguage(this);
		blocklogger = new BlockLoggers();
				
		jobscommand = new JobsCommand(this);
		admincommand = new AdminCommand(this);
		
		
		getCommand("mcjobs").setExecutor(jobscommand);
		getCommand("mcjobsadmin").setExecutor(admincommand);
				
		getServer().getPluginManager().registerEvents(new OnPlayerLogins(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new JobChangeListener(), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new Crafting(), this);
		getServer().getPluginManager().registerEvents(new MobKill(), this);
		getServer().getPluginManager().registerEvents(new Fishing(), this);
		getServer().getPluginManager().registerEvents(new ShearEvent(), this);
		getServer().getPluginManager().registerEvents(new Baking(), this);
		getServer().getPluginManager().registerEvents(new Enchanting(), this);
		getServer().getPluginManager().registerEvents(new Brewing(), this);
		
		Plugin pmcMMO    = getServer().getPluginManager().getPlugin("mcMMO");
		Plugin pWG       = getServer().getPluginManager().getPlugin("WorldGuard");
		Plugin pLogBlock = getServer().getPluginManager().getPlugin("LogBlock");
		Plugin pTowny	 = getServer().getPluginManager().getPlugin("Towny");
		
		
		try{
			metrics = new Metrics(this);
//			metrics.start();
		}
		catch(Exception e){
			log.info("Metrics unable to start!");
		}
		
		if(pmcMMO != null){
			getServer().getPluginManager().registerEvents(new McmmoRepairListener(), this);
			log.info("mcMMO has been found.");
		}	
		
		if(pLogBlock != null && this.getConfig().getString("advanced.log_mod").equalsIgnoreCase("logblock")){
			this.bLogBlock = true;
			log.info("LogBlock logging found and enabled.");
		}		
		else{
			log.info("Using builtin logging methods.");
		}
				
		if(pWG != null){
			MCListeners.setWorldGuard(true);
			wgp = (WorldGuardPlugin) pWG;
			log.info("WorldGuard found.  Enabling WorldGuard protections.");
		}
		
		if(pTowny != null){
			MCListeners.setTowny(true);
			towny = (Towny) pTowny;
			log.info("Towny found.  Enabling Towny protections.");
		}
		try{		
			loadEconomyBridges();
		}
		catch(Exception e){
			log.info("Unable to load Economy mods.");
			log.info("Using XP economy.");
			
			McJobsComp.setXP(true);
		}
		
		try{
			mcloadconf(this);
		}
		catch(Exception e)
		{
			log.severe("mcloadconf failure.  Your config.yml file is corrupted delete it and let it rebuild.");  
			log.info("Shutting down MC Jobs.");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			bQuit = true;
		}

		if(!bQuit){

			PlayerCache.loadPlayerPerms();
		
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new McJobsRemovePerm(), 1200L, 1200L);
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new McJobsPreComp(), 200L, 200L);

			if(isPrune())
				getServer().getScheduler().scheduleAsyncRepeatingTask(this, new McJobsFilePrune(), 1200L, 1200L);

			log.info("MC Jobs has been enabled!");
		}
	}
	

	public void onDisable() {
		log = this.getLogger();
		
		getServer().getScheduler().cancelTasks(this);
		
		log.info("Canceling Tasks...");
		log.info("MC Jobs has been disabled!");
	}

	public void mcloadconf(Plugin plugin) throws Exception{
		FileConfiguration config = plugin.getConfig();
		File file = new File("./plugins/mcjobs/config.yml");
		
		if(!file.exists()){
			config.options().copyDefaults(true);
			saveConfig();			
		}			

		ConfigurationSection section = config.getConfigurationSection("backend");
		
		if(section.getString("type").equalsIgnoreCase("mysql")){
			String user = section.getString("user");
			String pass = section.getString("pass");
			String url = "jdbc:mysql://" + section.getString("host").toLowerCase() + ":" + section.getString("port") + "/" + section.getString("database");
			
			log.info("Trying connection to: " + url);
			
//			SQLonLoad.loadSQL(user, pass, url);
		}
		
		
		section = config.getConfigurationSection("advanced");
		
		if(section.getKeys(false).contains("version"))
			this.version = section.getInt("version");
		
		if(this.version != VERSION || !section.getKeys(false).contains("version")){
			log.severe("IF YOU'RE UPGRADING FROM MC JOBS 2.8.X or 3.0.X THIS WILL BREAK YOUR DATA'S USER FILES!!!");
			log.severe("THEY WILL HAVE TO START OVER!");
			log.info("Config.yml is out of date.  Delete config.yml to build a new one.");
			log.info("Disabling MC Jobs.");
			
			getServer().getPluginManager().disablePlugin(this);
			bQuit = true;
			
			return;
		}
		
		this.localization = config.getString("advanced.language");
		
		try{
			this.getLanguage().loadLanguage(localization);
		}
		
		catch(Exception e){
			log.info("Failed to load " + localization + ".yml!!!  Defaulting to english.yml");
			this.getLanguage().loadLanguage("english");
		}
		
		this.bPrune = config.getBoolean("advanced.prune");
		
		PlayerJobs.setPercent(config.getInt("percent_cost"));
		
		MCListeners.setPaySpawner(config.getBoolean("advanced.pay_spawners"));
		MCListeners.setMultiWorld(config.getBoolean("advanced.multiWorld"));
		
		BlockBreak.setNoPitch(config.getBoolean("advanced.nopitch"));
		McJobsNotify.setShow(config.getBoolean("show_pay"));
		
		PayMoney.setMaxPay(config.getDouble("max_pay"));
		PayXP.setMaxPay(config.getDouble("max_pay"));
		
		Leveler.setXPMod(config.getDouble("advanced.xp_modifier"));
		Leveler.setPayMod(config.getDouble("advanced.pay_scale"));
		
		JobChangeListener.setTimer(config.getInt("timers.rejoin_interval"));
		PlayerCache.setLastSaveTimer(config.getInt("timers.player_save"));
		PlayerCache.setExpired(config.getInt("timers.delete_cache"));
		
		if(config.getInt("advanced.spawn_distance") > 0)
			MCListeners.setSpawnDist(config.getInt("advanced.spawn_distance"));
				
		if(config.getLong("timers.time_interval") < 1){
			time = 1200L;
			BlockLoggers.setTimer(time);
			MCListeners.setTimeInMins(1);
		}
		else{
			time = config.getLong("timers.time_interval") * 20L * 60L;
			BlockLoggers.setTimer(time);
			MCListeners.setTimeInMins(config.getInt("timers.time_interval"));
		}
		
		if(config.getLong("timers.show_interval") < 1){
			notify = 1200L;
			McJobsNotify.setTime(1);
		}
		else{
			notify = config.getLong("timers.show_interval") * 20L * 60L;
			McJobsNotify.setTime(config.getInt("timers.show_interval"));
		}
		
		File job_file = new File("./plugins/mcjobs/jobs.yml");
		
		if(!job_file.exists()){
			file.createNewFile();
			
			InputStream is = plugin.getResource("jobs.yml");			
			YamlConfiguration job_configure = YamlConfiguration.loadConfiguration(is);
			
			job_configure.save(job_file);
		}

		FileConfiguration job_config = YamlConfiguration.loadConfiguration(job_file);
		ConfigurationSection job_section = job_config.getConfigurationSection("jobs");
		
		Iterator<String> it = job_section.getKeys(false).iterator();
		String joblist = "";
		
		while(it.hasNext()){
			String jobname = it.next();
			PlayerJobs job = new PlayerJobs();
			job_section = job_config.getConfigurationSection("jobs." + jobname);			

			try{
				job.getData().loadJob().setName(jobname);
				job.getData().loadJob().setupJob(job_section);
			
				jobname = jobname.toLowerCase();
			
				PlayerJobs.getJobsList().put(jobname, job);
				
				joblist = joblist.concat(jobname + " ");
			}
			catch(Exception e){
				log.info("Error inside jobs.yml!  Job " + jobname + " failed to load properly!");
			}
		}

		log.info("LOADED JOBS: " + joblist);
		
		section = config.getConfigurationSection("ranks");
		it = section.getKeys(false).iterator();
		
		while(it.hasNext()){
			String rank = it.next();
			int level = config.getInt("ranks." + rank);
			
			Leveler.getRanks().put(level, rank);
		}
		
		section = config.getConfigurationSection("max_jobs");
		it = section.getKeys(false).iterator();
		
		while(it.hasNext()){
			String group = it.next();
			int jobsamount = config.getInt("max_jobs." + group);
			
			PlayerUtils.getMaxDefaults().put(group.toLowerCase(), jobsamount);
		}
		
		if(!PlayerUtils.getMaxDefaults().containsKey("default")){
			log.info("max_jobs corrupted.  No default value found.  Setting default to 3!");
			PlayerUtils.getMaxDefaults().put("default", 3);
		}
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new McJobsNotify(), notify, notify);
	}
	
	private void loadEconomyBridges(){
		Plugin pRegister = getServer().getPluginManager().getPlugin("Register");
		Plugin pVault = getServer().getPluginManager().getPlugin("Vault");
		
		String bridge = this.getConfig().getString("advanced.payment_mod");
		String type = this.getConfig().getString("advanced.payment_type");
		
		if(bridge == null){
			log.info("Bridge value is null.  Setting to none.");
			bridge = "none";
		}
		
		if(type == null){
			log.info("Type value is null.  Setting to xp.");
			type = "xp";
		}
		
		if(pVault != null){
			AdminCommand.setVault(true);
			PlayerUtils.setVault(true);
		}
		
		if(type.equalsIgnoreCase("money") || type.equalsIgnoreCase("both")){
		
			if(bridge.equalsIgnoreCase("register") && pRegister != null){
				log.info("Found " + pRegister.getName() + " " + pRegister.getDescription().getVersion());
				McJobsComp.setRegister(true);
			}
			else if(bridge.equalsIgnoreCase("vault") && pVault != null){
				log.info("Found " + pVault.getName() + " " + pVault.getDescription().getVersion());
				economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
				McJobsComp.setVault(true);
			}
			else if(pRegister != null){
				log.info("Preferred bridge not found.  Using  " + pRegister.getName() + " " + pRegister.getDescription().getVersion());
				McJobsComp.setRegister(true);
			}
			else if(pVault != null){
				log.info("Preferred bridge not found.  Using  " + pVault.getName() + " " + pVault.getDescription().getVersion());
				McJobsComp.setVault(true);
			}
			else{
				log.info("No economy bridge found!  Going to XP economy.");
				McJobsComp.setXP(true);
			}
		}
		else{
			log.info("Using XP economy.");
			McJobsComp.setXP(true);
		}
		
		if(type.equalsIgnoreCase("both")){
			McJobsComp.setXP(true);
		}
	}
	
	public static McJobs getPlugin(){
		return mcJobs;
	}
	
	public static Economy getEconomy(){
		return economy;
	}
				
	public boolean isLogBlock(){
		return this.bLogBlock;		
	}
		
	public static WorldGuardPlugin getWorldGuard(){
		return wgp;
	}
	
	public static Towny getTowny(){
		return towny;
	}
		
	public GetLanguage getLanguage(){
		return this.language;
	}
	
	public BlockLoggers getBlockLogging(){
		return this.blocklogger;
	}
	
	public boolean isPrune(){
		return this.bPrune;
	}
	
	public Integer getVersion(){
		return this.version;
	}
}