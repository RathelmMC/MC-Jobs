package com.dmgkz.mcjobs.listeners;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PitchJobs;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;


import de.diddiz.LogBlock.QueryParams.BlockChangeType;

public class BlockBreak implements Listener{
	
	private static boolean noPitch = false; 
	
	
	@EventHandler(priority = EventPriority.LOW)
	public void blockBreak(BlockBreakEvent event) {
		if(event.isCancelled())
			return;
		if(event.getBlock().getType() == null)
			return;
		
		Player play = event.getPlayer();		
		Material block = event.getBlock().getType();
		Location loc = event.getBlock().getLocation();
		Integer timer = MCListeners.getTimeInMins();

		Iterator<Map.Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();
		String sJob = null;

		if(MCListeners.isWorldGuard()){
			if(!McJobs.getWorldGuard().canBuild(play, loc))
				return;
		}
		
		if(MCListeners.isTowny()){			
			if(!PlayerCacheUtil.getCachePermission(play, loc, block.getId(), (byte)0, TownyPermission.ActionType.DESTROY))
				return;
		}
		
		if(MCListeners.isMultiWorld()){
			if(!play.hasPermission("mcjobs.world.all") && !play.hasPermission("mcjobs.world." + play.getWorld().getName()))
				return;
		}
		
		if(play.getGameMode() == GameMode.CREATIVE){
			if(!play.hasPermission("mcjobs.paycreative"))
				return;
		}
		
		if(PlayerCache.getJobCount(play.getName()) == 0 && !noPitch){
			if((play.hasPermission("mcjobs.jobs.join") || play.hasPermission("mcjobs.jobs.*") || play.hasPermission("mcjobs.jobs.all") || !MCListeners.isPerms()))
				if(!PlayerCache.getSeenPitch(play.getName()))
					PitchJobs.pitchJobs(play);
		}
		
		while(it.hasNext()){

			Map.Entry<String, PlayerJobs> pair = it.next();
			sJob = pair.getKey();

			if(PlayerCache.hasJob(play.getName(), sJob)){
				
				if(McJobs.getPlugin().isLogBlock()){
					List<Integer> lTypes = Arrays.asList(event.getBlock().getTypeId());
					
					if(McJobs.getPlugin().getBlockLogging().checkLogBlock(lTypes, play.getWorld(), play, event.getBlock().getLocation(), BlockChangeType.DESTROYED, timer))
						return;
				}
				else{
					if(McJobs.getPlugin().getBlockLogging().checkBuiltIn(loc, play, true))
						return;
				}
				
				CompCache comp = new CompCache(sJob, loc, play, block, "break");
				CompData.getCompCache().add(comp);
				
//				if(PlayerJobs.joblist.get(sJob).getData().compJob().compBlock(block, play, "break")){		
//					play.sendMessage("You broke " + ChatColor.DARK_GREEN + block + ChatColor.WHITE + ".");
//					if(McJobs.getPlugin().getBlockLogging().getBuiltIn().containsKey(play.getWorld()))
//						if(McJobs.getPlugin().getBlockLogging().getBuiltIn().get(play.getWorld()))
//							McJobs.getPlugin().getBlockLogging().addPlayer(loc, play, true);
//				}
			}
		}
	}

	public static void setNoPitch(boolean b){
		noPitch = b;
	}
}
