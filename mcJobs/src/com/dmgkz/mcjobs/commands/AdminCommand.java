package com.dmgkz.mcjobs.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.localization.GetLanguage;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.levels.Leveler;
import com.dmgkz.mcjobs.prettytext.PrettyText;
import com.dmgkz.mcjobs.scheduler.McJobsFilePrune;
import com.dmgkz.mcjobs.scheduler.McJobsPreComp;
import com.dmgkz.mcjobs.scheduler.McJobsRemovePerm;
import com.dmgkz.mcjobs.util.StringToNumber;

public class AdminCommand implements CommandExecutor{

	private static boolean bVault = false;
	
	private McJobs mcPlugin;
	private GetLanguage modText = McJobs.getPlugin().getLanguage();
	private PrettyText text = new PrettyText();
	private String str;
	
	public AdminCommand(McJobs plugin) {
		this.mcPlugin = plugin;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!command.getName().equalsIgnoreCase("mcjobsadmin")){
			sender.sendMessage("Critical failure!");
			return true;
		}
		Player playsend = null;
		
		if(sender instanceof Player){
			playsend = (Player) sender;
			if(!playsend.hasPermission("mcjobs.admin")){
				str = ChatColor.RED + modText.getAdminCommand("permission").addVariables("", playsend.getName(), label);
				text.formatPlayerText(str, playsend);
				return true;
			}
		}
		
		if(args.length == 0){
			String version = mcPlugin.getDescription().getVersion();
			sender.sendMessage(ChatColor.DARK_RED + "MC Jobs by " + ChatColor.GOLD + "RathelmMC v" + ChatColor.GREEN + version);
			return false;				
		}
		else if(args[0].equalsIgnoreCase("reload") && args.length == 1){
			sender.getServer().getPluginManager().getPlugin(mcPlugin.getName()).reloadConfig();

			PlayerJobs.getJobsList().clear();
			Leveler.getRanks().clear();
			
			McJobs.getPlugin().getServer().getScheduler().cancelTasks(McJobs.getPlugin());

			try{
				mcPlugin.mcloadconf(mcPlugin);
			}
			catch(Exception e){
				if(sender instanceof Player){
					str = ChatColor.RED + modText.getAdminCommand("failedreload").addVariables("", sender.getName(), label);
					text.formatPlayerText(str, (Player) sender);
				}
				else
					sender.sendMessage(ChatColor.RED + modText.getAdminCommand("failedreload").addVariables("", sender.getName(), label));
			}
			
			PlayerCache.loadPlayerPerms();
			
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(mcPlugin, new McJobsRemovePerm(), 1200L, 1200L);
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(mcPlugin, new McJobsPreComp(), 200L, 200L);
			
			if(mcPlugin.isPrune())
				Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(mcPlugin, new McJobsFilePrune(), 1200L, 1200L);
			
			if(sender instanceof Player){
				str = ChatColor.GRAY + modText.getAdminCommand("succeedreload").addVariables("", sender.getName(), label);
				text.formatPlayerText(str, (Player) sender);
			}
			else
				sender.sendMessage(ChatColor.GRAY + modText.getAdminCommand("succeedreload").addVariables("", sender.getName(), label));

			return true;
		}
		else if(args[0].equalsIgnoreCase("defaults") && args.length == 1){
			if(playsend != null){
				if(!playsend.hasPermission("mcjobs.admin.defaults")){
					str = ChatColor.RED + modText.getAdminCommand("permission").addVariables("", playsend.getName(), label);
					text.formatPlayerText(str, playsend);
					return true;
				}						
			}
			sender.getServer().getPluginManager().getPlugin(mcPlugin.getName()).getConfig().options().copyDefaults(true);
			mcPlugin.saveConfig();

			if(sender instanceof Player){
				str = ChatColor.GRAY + modText.getAdminCommand("defaults").addVariables("", sender.getName(), label);
				text.formatPlayerText(str, (Player) sender);
			}
			else
				sender.sendMessage(ChatColor.GRAY + modText.getAdminCommand("defaults").addVariables("", sender.getName(), label));
			return true;
		}
		
		else if(args[0].equalsIgnoreCase("addlevel")){
			if(args.length != 4){
				if(sender instanceof Player){
					str = ChatColor.RED + modText.getAdminAdd("args").addVariables("", sender.getName(), label);
					text.formatPlayerText(str, (Player) sender);					
				}
				else
					sender.sendMessage(ChatColor.RED + modText.getAdminAdd("args").addVariables("", sender.getName(), label));
				return true;
			}
			
			if(!getPlayers(args[1], sender).isEmpty()){
				this.addLevels(getPlayers(args[1], sender), args[2], args[3], sender);
			}

			return true;
		}
		
		else if(args[0].equalsIgnoreCase("addxp")){
			if(args.length != 4){
				if(sender instanceof Player){
					str = ChatColor.RED + modText.getAdminAdd("args").addVariables("", sender.getName(), label);
					text.formatPlayerText(str, (Player) sender);					
				}
				else
					sender.sendMessage(ChatColor.RED + modText.getAdminAdd("args").addVariables("", sender.getName(), label));
				return true;
			}
			
			if(!getPlayers(args[1], sender).isEmpty()){
				this.addExp(getPlayers(args[1], sender), args[2], args[3], sender);
			}

			return true;
		}
		
		else if(args[0].equalsIgnoreCase("add")){
			if(args.length != 3){
				if(sender instanceof Player){
					str = ChatColor.RED + modText.getAdminAdd("args").addVariables("", sender.getName(), label);
					text.formatPlayerText(str, (Player) sender);
				}
				else
					sender.sendMessage(ChatColor.RED + modText.getAdminAdd("args").addVariables("", sender.getName(), label));
				return true;
			}
						
			if(!getPlayers(args[1], sender).isEmpty()){			
				this.addJob(getPlayers(args[1], sender), args[2], sender);
			}
				
			return true;
		}				

		else if(args[0].equalsIgnoreCase("remove")){
			if(args.length != 3){
				if(sender instanceof Player){
					str = ChatColor.RED + modText.getAdminRemove("args").addVariables("", sender.getName(), label);
					text.formatPlayerText(str, (Player) sender);
				}
				else
					sender.sendMessage(ChatColor.RED + modText.getAdminRemove("args").addVariables("", sender.getName(), label));
				return true;
			}

			if(!getPlayers(args[1], sender).isEmpty()){			
				this.removeJob(getPlayers(args[1], sender), args[2], sender);
			}
		
			return true;
		}

		else if(args[0].equalsIgnoreCase("list")){			
			if(args.length != 2 && args.length != 3){
				sender.sendMessage(modText.getAdminList("args").addVariables("", sender.getName(), ""));
				return true;
			}
			
			ArrayList<String> players = new ArrayList<String>();
			Integer pageNum;
			String[] sList;
			Player play = null;
			
			if(sender instanceof Player)
				play = (Player)sender;
			
			if(args.length == 2){
				pageNum = 1;
			}
			else{
				if(StringToNumber.isPositiveNumber(args[2]))
					pageNum = Integer.parseInt(args[2]);
				else{
					if(sender instanceof Player){
						String s;
						PrettyText t = new PrettyText();
						s = modText.getAdminList("wrongpage").addVariables("", sender.getName(), args[2]);
						t.formatPlayerText(s, play);
					}
					else
						sender.sendMessage(modText.getAdminList("wrongpage").addVariables("", sender.getName(), args[2]));
					
					pageNum = 1;
				}
			}
			
			players = getPlayers(args[1], sender);
			
			if(!players.isEmpty()){
				
				sList = showList(players, pageNum);
				
				PrettyText text = new PrettyText();
				for(String s : sList){
					if(play != null){
						text.formatPlayerText(s, play); 
					}
					else{
						sender.sendMessage(s);
					}
				}
			}
			return true;
		}
		
		else if(args.length > 3){
			if(sender instanceof Player){
				str = ChatColor.RED + modText.getAdminCommand("args").addVariables("", sender.getName(), label);
				text.formatPlayerText(str, (Player) sender);
			}
			else
				sender.sendMessage(ChatColor.RED + modText.getAdminCommand("args").addVariables("", sender.getName(), label));
			return true;
		}
		
		return false;
	}
	
	private void addLevels(ArrayList<String> players, String job, String level, CommandSender sender){
		job = job.toLowerCase();
		int levels = 1;
		
		if(StringToNumber.isPositiveNumber(level))
			levels = Integer.parseInt(level);
		
		if(PlayerJobs.getJobsList().get(job) == null){
			sender.sendMessage(ChatColor.RED + modText.getAdminCommand("exist").addVariables(job, "", sender.getName()));
			return;
		}
		else{
			Iterator<String> it = players.iterator();
			
			while(it.hasNext()){
				String play = it.next();
				
				if(play == null){
					sender.sendMessage(ChatColor.RED + modText.getAdminAdd("offline").addVariables(job, "", sender.getName()));
					return;
				}
				
				if(!PlayerCache.hasJob(play, job)){
					sender.sendMessage(ChatColor.RED + modText.getExperience("nojob").addVariables(job, play, level));
					continue;
				}
				
				if(PlayerCache.addLevels(play, job, levels)){
					sender.sendMessage(ChatColor.GRAY + modText.getExperience("added_lvl").addVariables(job, play, level));
					
					if(Bukkit.getPlayer(play) != null){
						PrettyText text = new PrettyText();
						String str = ChatColor.GRAY + modText.getExperience("padded_lvl").addVariables(job, play, level);
						text.formatPlayerText(str, Bukkit.getPlayer(play));
					}					
				}
			}
		}
	}

	private void addExp(ArrayList<String> players, String job, String level, CommandSender sender){
		job = job.toLowerCase();
		double experience = 1;
		
		if(StringToNumber.isPositiveNumber(level))
			experience = Double.parseDouble(level);
		
		if(PlayerJobs.getJobsList().get(job) == null){
			sender.sendMessage(ChatColor.RED + modText.getAdminCommand("exist").addVariables(job, "", sender.getName()));
			return;
		}
		else{
			Iterator<String> it = players.iterator();
			
			while(it.hasNext()){
				String play = it.next();
				
				if(play == null){
					sender.sendMessage(ChatColor.RED + modText.getAdminAdd("offline").addVariables(job, "", sender.getName()));
					return;
				}

				if(!PlayerCache.hasJob(play, job)){
					sender.sendMessage(ChatColor.RED + modText.getExperience("nojob").addVariables(job, play, level));
					continue;
				}

				if(PlayerCache.addExp(play, job, experience)){
					sender.sendMessage(ChatColor.GRAY + modText.getExperience("added_xp").addVariables(job, play, level));
					
					if(Bukkit.getPlayer(play) != null){
						PrettyText text = new PrettyText();
						String str = ChatColor.GRAY + modText.getExperience("padded_xp").addVariables(job, play, level);
						text.formatPlayerText(str, Bukkit.getPlayer(play));
					}					
				}
			}
		}
	}

	private void addJob(ArrayList<String> players, String job, CommandSender sender){
		job = job.toLowerCase();
		
		if(PlayerJobs.getJobsList().get(job) == null){
			sender.sendMessage(ChatColor.RED + modText.getAdminCommand("exist").addVariables(job, "", sender.getName()));
			return;
		}
		else{
			Iterator<String> it = players.iterator();
			
			while(it.hasNext()){
				String play = it.next();
				
				if(play == null){
					sender.sendMessage(ChatColor.RED + modText.getAdminAdd("offline").addVariables(job, "", sender.getName()));
					return;
				}
				
				if(PlayerCache.hasJob(play, job)){
					sender.sendMessage(ChatColor.GRAY + modText.getAdminAdd("hasjob").addVariables(job, play, sender.getName()));
				}
				else{				
					PlayerCache.addJob(play, job);

					sender.sendMessage(ChatColor.GRAY + modText.getAdminAdd("added").addVariables(job, play, sender.getName()));
					
					if(Bukkit.getPlayer(play) != null){
						PrettyText text = new PrettyText();
						String str = ChatColor.GRAY + modText.getAdminAdd("padded").addVariables(job, play, sender.getName());
						text.formatPlayerText(str, Bukkit.getPlayer(play));
					}
				}
			}
		}
	}

	private void removeJob(ArrayList<String> players, String job, CommandSender sender){
		job = job.toLowerCase();
		Iterator<String> it = players.iterator();

		if(PlayerJobs.getJobsList().get(job) == null){
			sender.sendMessage(ChatColor.RED + modText.getAdminCommand("exist").addVariables(job, sender.getName(), ""));
			return;
		}

		if(sender instanceof Player){
			if(PlayerJobs.getJobsList().get(job).getData().compJob().isDefault() && !sender.hasPermission("mcjobs.admin.leavedefault")){
				PrettyText t = new PrettyText();
				String s = ChatColor.RED + modText.getAdminRemove("nodefault").addVariables(job, sender.getName(), "");
				t.formatPlayerText(s, (Player)sender);
				return;
			}				
		}
		
		while(it.hasNext()){
			String play = it.next();

			if(play == null){
				sender.sendMessage(ChatColor.RED + modText.getAdminAdd("offline").addVariables(job, "", sender.getName()));
				return;
			}

			if(!PlayerCache.hasJob(play, job)){
				sender.sendMessage(ChatColor.RED + modText.getAdminRemove("nojob").addVariables(job, play, sender.getName()));
			}
			else{
				PlayerCache.removeJob(play, job);
				
				sender.sendMessage(ChatColor.GRAY + modText.getAdminRemove("removed").addVariables(job, play, sender.getName()));

				if(Bukkit.getPlayer(play) != null){
					PrettyText text = new PrettyText();
					String str = ChatColor.GRAY + modText.getAdminRemove("premoved").addVariables(job, play, sender.getName());
					text.formatPlayerText(str, Bukkit.getPlayer(play));
				}
			}
		}					
	}

	private String[] showList(ArrayList<String> players, Integer pageNum){
		Iterator<String> it = players.iterator();
		String[] str = new String[players.size()];
		String[] modStr;
		Integer i = 0;

		
		while(it.hasNext()){
			String play = it.next();
			String playJobList = "";
			
			Iterator<Map.Entry<String, PlayerJobs>> its = PlayerJobs.getJobsList().entrySet().iterator();
			while(its.hasNext()){
				Map.Entry<String, PlayerJobs> pair = its.next();
					
				if(PlayerCache.hasJob(play, pair.getKey())){
					playJobList = playJobList.concat(" " + ChatColor.GOLD + pair.getKey() + ChatColor.GRAY + ",");
				}
			}
			if(playJobList.isEmpty())
				playJobList = modText.getAdminList("nojobs").addVariables("", play, "");
			else{
				playJobList = playJobList.substring(0, (playJobList.length() - 1));
				playJobList = playJobList.concat(".");
			}
			
			str[i] = modText.getAdminList("playerlist").addVariables("", play, playJobList);
			i++;
		}
		
		Integer iLength = str.length;
		Integer allowedPages = 1;
		Integer page = pageNum;
			
		while(iLength > 10){ 
			iLength = iLength - 10;
			allowedPages++;
		}
		
		if(page > allowedPages)
			page = 1;
		
		modStr = new String[iLength];
		for(int x = 0 ; x < modStr.length ; x++){
			modStr[x] = str[(10 * (page - 1)) + x];
		}
		
		return str;
	}

	private ArrayList<String> getPlayers(String args, CommandSender sender){
		List<Player> aOnline              = new ArrayList<Player>();
		ArrayList<String> players         = new ArrayList<String>();

		aOnline = (List<Player>) Arrays.asList(Bukkit.getOnlinePlayers());			

		if(PlayerCache.playerExists(args))
			players.add(args);
				
		if(bVault){
			Permission permission = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class).getProvider();
			List<String> lGroups = new ArrayList<String>();
			lGroups = Arrays.asList(permission.getGroups());
			
			if(lGroups.contains(args)){
				Iterator<Player> it = aOnline.iterator();

				while(it.hasNext()){
					Player play = it.next();
					String[] playerGroups = permission.getPlayerGroups(play);
					List<String> lPlayerGroups = new ArrayList<String>();
					
					if(playerGroups != null){
						lPlayerGroups = Arrays.asList(playerGroups);
					
						if(lPlayerGroups.contains(args))
							players.add(play.getName());
					}
				}

				if(players.isEmpty()){
					sender.sendMessage(ChatColor.GRAY + modText.getAdminAdd("empty").addVariables("", args, sender.getName()));
					return players;
				}
			}

			if(players.isEmpty()){
				sender.sendMessage(ChatColor.GRAY + modText.getAdminAdd("offline").addVariables("", args, sender.getName()));
				return players;
			}
		}
		
		if(players.isEmpty()){
			sender.sendMessage(ChatColor.RED + modText.getAdminAdd("novault").addVariables("", args, sender.getName()));
			return players;
		}

		return players;
	}

	public static void setVault(boolean b){
		bVault = b;
	}	
}
