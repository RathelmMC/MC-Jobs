package com.dmgkz.mcjobs.commands;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.events.McJobsEventJobChange;
import com.dmgkz.mcjobs.localization.GetLanguage;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.prettytext.PrettyText;
import com.dmgkz.mcjobs.util.StringToNumber;
import com.dmgkz.mcjobs.util.TimeFormat;

public class JobsCommand implements CommandExecutor{

	private McJobs mcPlugin;
	private GetLanguage modText = McJobs.getPlugin().getLanguage();
	private DecimalFormat df = new DecimalFormat("#,##0");
	
	public JobsCommand(McJobs plugin) {
		this.mcPlugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean usePerms = true;
		usePerms = mcPlugin.getConfig().getBoolean("advanced.usePerms");
		
		if(!command.getName().equalsIgnoreCase("mcjobs")) {
			sender.sendMessage("JobsCommand failure!");
			return false;
		}

		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + modText.getJobCommand("playeronly").addVariables("", "", ""));
			return true;
		}

		Player play = (Player) sender;
		
		if(args.length == 0){
			String version = mcPlugin.getDescription().getVersion();
			play.sendMessage(ChatColor.DARK_RED + "MC Jobs by " + ChatColor.GOLD + "RathelmMC v" + ChatColor.GREEN + version);
			return false;
		}
		else if(args.length == 1 && args[0].equalsIgnoreCase("list")){
			if(!(play.hasPermission("mcjobs.jobs.list") || play.hasPermission("mcjobs.jobs.all")) && usePerms){
				play.sendMessage(ChatColor.RED + modText.getJobCommand("permission").addVariables("", play.getName(), ""));
				return true;
			}
			
			this.displayList(play);			
			return true;
		}
		
		else if((args.length == 1 && args[0].equalsIgnoreCase("help")) || (args.length == 2 && args[0].equalsIgnoreCase("help"))){
			if(!(play.hasPermission("mcjobs.jobs.list")|| play.hasPermission("mcjobs.jobs.all")) && usePerms){
				play.sendMessage(ChatColor.RED + modText.getJobCommand("permission").addVariables("", play.getName(), ""));
				return true;
			}

			String page;
			
			if(args.length == 1)
				page = "1";
			else
				page = args[1];

			this.showHelp(play, page);
			return true;
		}
		
		else if(args.length == 1 && !(args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("help"))){ 
			if(!(play.hasPermission("mcjobs.jobs.info") || play.hasPermission("mcjobs.jobs.all")) && usePerms){ 
				play.sendMessage(ChatColor.RED + modText.getJobCommand("permission").addVariables("", play.getName(), ""));
				return true;
			}
			
			this.jobInfo(play, args[0]);			
			return true;
		}
		
		else if(args.length == 2 && args[0].equalsIgnoreCase("show")){
			if(PlayerCache.hasJob(play.getName(), args[1].toLowerCase())){
				PlayerCache.setShowEveryTime(play.getName(), args[1].toLowerCase(), true);
				play.sendMessage(ChatColor.GRAY + modText.getJobCommand("show").addVariables(args[1], play.getName(), ""));				
			}
			else
				play.sendMessage(ChatColor.RED + modText.getJobLeave("donthave").addVariables(args[1], play.getName(), ""));

			return true;
		}
		
		else if(args.length == 2 && args[0].equalsIgnoreCase("hide")){
			if(PlayerCache.hasJob(play.getName(), args[1].toLowerCase())){
				PlayerCache.setShowEveryTime(play.getName(), args[1].toLowerCase(), false);
				play.sendMessage(ChatColor.GRAY + modText.getJobCommand("hide").addVariables(args[1], play.getName(), ""));				
			}
			else
				play.sendMessage(ChatColor.RED + modText.getJobLeave("donthave").addVariables(args[1], play.getName(), ""));

			return true;
		}
		
		else if(args.length == 2 && args[0].equalsIgnoreCase("join")){
			if(!(play.hasPermission("mcjobs.jobs.join") || play.hasPermission("mcjobs.jobs.all")) && usePerms){
				play.sendMessage(ChatColor.RED + modText.getJobCommand("permission").addVariables("", play.getName(), ""));
				return true;
			}

			this.jobJoin(play, args[1]);
			return true;
		}

		else if(args.length == 2 && args[0].equalsIgnoreCase("leave")){
			if(!(play.hasPermission("mcjobs.jobs.leave") || play.hasPermission("mcjobs.jobs.all")) && usePerms){
				play.sendMessage(ChatColor.RED + modText.getJobCommand("permission").addVariables("", play.getName(), ""));
				return true;
			}
			
			this.leaveJob(play, args[1]);
			return true;
		}

		else if(args.length > 2){
			play.sendMessage(ChatColor.RED + modText.getJobCommand("args").addVariables("", play.getName(), ""));
			return true;
		}

		else 
			return false;			
	}

	private void displayList(Player play){
		Iterator<Map.Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();
		String str = ChatColor.DARK_GREEN + modText.getJobList("available").addVariables("", play.getName(), "") + " ";
		Integer iJob = 0;
		Integer iMax = PlayerCache.getAllowedJobCount(play.getName());
		PrettyText text = new PrettyText();
						
		while(it.hasNext()){
			Map.Entry<String, PlayerJobs> keys = it.next();
			String job = keys.getValue().getData().getName();
			
			if(PlayerCache.hasJob(play.getName(), keys.getKey()) && !keys.getValue().getData().compJob().isDefault()){
				str = str.concat(ChatColor.RED + job);
				iJob++;
			}
			else if(!PlayerCache.isJoinable(play.getName(), keys.getKey())){
				str = str.concat(ChatColor.DARK_GRAY + job);
			}
			else if((play.hasPermission("mcjobs.jobsavail." + keys.getKey()) || play.hasPermission("mcjobs.jobsavail.all") || !(McJobs.getPlugin().getConfig().getBoolean("advanced.usePerms"))) && !keys.getValue().getData().compJob().isDefault())
				str = str.concat(ChatColor.GOLD + job);
			else if(keys.getValue().getData().compJob().isDefault())
				str = str.concat(ChatColor.DARK_AQUA + job);
			else
				str = str.concat(ChatColor.DARK_GRAY + job);
			
			if(it.hasNext())
				str = str.concat(ChatColor.GRAY + ", ");
			else
				str = str.concat(ChatColor.GRAY + ".");
		}
		play.sendMessage(ChatColor.DARK_GREEN + modText.getJobList("jobsin").addVariables("", play.getName(), "") + PrettyText.addSpaces(modText.getSpaces("jobslist")) + ChatColor.RED + modText.getJobList("jobs").addVariables("", play.getName(), "") + " "
				                              + iJob.toString() + ChatColor.DARK_GRAY + "/" + ChatColor.RED + iMax.toString());
		play.sendMessage(ChatColor.DARK_GREEN + modText.getJobList("nojob").addVariables("", play.getName(), ""));
		play.sendMessage(ChatColor.DARK_GREEN + modText.getJobList("defaultjob").addVariables("", play.getName(), ""));
		play.sendMessage(ChatColor.DARK_GREEN + modText.getJobList("specific").addVariables("", play.getName(), ""));
		play.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------------------");
		
		text.formatPlayerText(str, play);

	}

	private void jobInfo(Player play, String job){
		job = job.toLowerCase();
		if(PlayerJobs.getJobsList().get(job) == null){
			play.sendMessage(ChatColor.RED + modText.getJobCommand("exist").addVariables(job, play.getName(), ""));
		}
		else
			PlayerJobs.getJobsList().get(job).getData().display().showJob(play);				
	}

	private void jobJoin(Player play, String job){
		job = job.toLowerCase();
		String sPlay = play.getName();

		if(PlayerJobs.getJobsList().get(job) == null){
			play.sendMessage(ChatColor.RED + modText.getJobCommand("exist").addVariables(job, sPlay, ""));
			return;					
		}

		if(PlayerCache.hasJob(sPlay, job)){
			play.sendMessage(ChatColor.RED + modText.getJobJoin("have").addVariables(job, sPlay, ""));
			return;
		}

		if(!(play.hasPermission("mcjobs.jobsavail." + job) || play.hasPermission("mcjobs.jobsavail.all")) && McJobs.getPlugin().getConfig().getBoolean("advanced.usePerms")){
			play.sendMessage(ChatColor.GRAY + modText.getJobJoin("jobperm").addVariables(job, sPlay, ""));

			return;
		}
		
		if(!PlayerCache.isJoinable(sPlay, job)){
			play.sendMessage(ChatColor.GRAY + modText.getJobJoin("jobperm").addVariables(job, sPlay, ""));

			Integer time = PlayerCache.getRejoinTime(sPlay, job);
			
			if(time == null){
				play.sendMessage("ERROR: time is null!");
				return;
			}
			else if(time < 0)
				time = 1;
			
			String timed = TimeFormat.getFormatedTime(time);
			PrettyText text = new PrettyText();
			String str = ChatColor.GRAY + modText.getJobJoin("timer").addVariables(job, sPlay, timed);
					
			text.formatPlayerText(str, play);
					
			return;
		}
		
		if(PlayerCache.getJobCount(play.getName()) < PlayerCache.getAllowedJobCount(play.getName())){
			PlayerCache.addJob(play.getName(), job);
			
			play.sendMessage(ChatColor.GRAY + modText.getJobJoin("join").addVariables(job, play.getName(), ""));

			McJobsEventJobChange event = new McJobsEventJobChange(play, job, true, false);
			Bukkit.getServer().getPluginManager().callEvent(event);
		}
		else{
			play.sendMessage(ChatColor.GRAY + modText.getJobJoin("toomany").addVariables(job, play.getName(), ""));
			play.sendMessage(ChatColor.YELLOW + modText.getJobJoin("command").addVariables(job, play.getName(), ""));
			return;
		}

		PlayerCache.savePlayerCache(sPlay);
	}

	private void leaveJob(Player play, String job){
		job = job.toLowerCase();
		String sPlay = play.getName();
		
		if(PlayerJobs.getJobsList().get(job) == null){
			play.sendMessage(ChatColor.RED + modText.getJobCommand("exist").addVariables(job, play.getName(), ""));
			return;
		}

		if(PlayerCache.hasJob(sPlay, job)){
			if(PlayerJobs.getJobsList().get(job).getData().compJob().isDefault() && !play.hasPermission("mcjobs.admin.leavedefault")){
				play.sendMessage(ChatColor.RED + modText.getJobLeave("leavedefault").addVariables(job, sPlay, ""));
				return;				
			}
			else{
				PlayerCache.removeJob(sPlay, job);
				play.sendMessage(ChatColor.GRAY + modText.getJobLeave("quit").addVariables(job, play.getName(), ""));

				McJobsEventJobChange event = new McJobsEventJobChange(play, job, false, true);
				Bukkit.getServer().getPluginManager().callEvent(event);
			}
		}
		else{
			play.sendMessage(ChatColor.RED + modText.getJobLeave("donthave").addVariables(job, play.getName(), ""));
			return;
		}
		
		PlayerCache.savePlayerCache(sPlay);
	}

	private void showHelp(Player play, String page){
		if(!StringToNumber.isPositiveNumber(page)){
			play.sendMessage(modText.getJobHelp("nohelp").addVariables("", play.getName(), page));
			return;
		}
		
		Integer nextPage = Integer.parseInt(page);
		
		if(nextPage > modText.getSpaces("numhelp") || nextPage < 1){
			play.sendMessage(modText.getJobHelp("nohelp").addVariables("", play.getName(), page));
			return;			
		}
		
		nextPage++;
		
		play.sendMessage(ChatColor.GOLD + "                            ---~~ " + ChatColor.DARK_AQUA + "MC Jobs " + ChatColor.GOLD + "~~---");
		play.sendMessage(ChatColor.GRAY + modText.getJobHelp("page").addVariables("", play.getName(), "") +" " + page + "                   " + ChatColor.BLUE + "Created by: RathelmMC");
		play.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------------------");
		play.sendMessage("");

		for(Integer iSubPage = 1; iSubPage < 13 ; iSubPage++){
			if(modText.getJobHelp(page + ".line" + iSubPage.toString()).addVariables("", play.getName(), nextPage.toString()) != null)
				play.sendMessage(modText.getJobHelp(page + ".line" + iSubPage.toString()).addVariables(df.format(PlayerCache.getAllowedJobCount(play.getName())), play.getName(), nextPage.toString()));
			else
				play.sendMessage("");
		}
		
		play.sendMessage("");
		if(page.equals(modText.getSpaces("numhelp").toString()))
			play.sendMessage(modText.getJobHelp("finish").addVariables("", play.getName(), ""));
		else
			play.sendMessage(modText.getJobHelp("endofpage").addVariables("", play.getName(), nextPage.toString()) + " " + modText.getJobHelp("command").addVariables("", play.getName(), nextPage.toString()));

	}
}
