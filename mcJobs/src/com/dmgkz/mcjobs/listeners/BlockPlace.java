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
import org.bukkit.event.block.BlockPlaceEvent;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;

import de.diddiz.LogBlock.QueryParams.BlockChangeType;

public class BlockPlace implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void blockPlace(BlockPlaceEvent event) {
		if(event.isCancelled())
			return;
		if(!event.canBuild())
			return;
		if(event.getBlock().getType() == null)
			return;
		
		Player play = event.getPlayer();
		Material block = event.getBlock().getType();
		Material replaced = event.getBlockReplacedState().getType();
		Location loc = event.getBlock().getLocation();
		Integer timer = MCListeners.getTimeInMins();
				

		if((block == Material.DIODE_BLOCK_OFF || block == Material.REDSTONE_TORCH_OFF || block == Material.RAILS || block == Material.DETECTOR_RAIL || block == Material.POWERED_RAIL) 
		&& (replaced == Material.WATER || replaced == Material.STATIONARY_WATER || replaced == Material.LAVA || replaced == Material.STATIONARY_LAVA))
			return;
		
		if(MCListeners.isWorldGuard()){
			if(!McJobs.getWorldGuard().canBuild(play, loc))
				return;
		}

		if(MCListeners.isTowny()){		
			if(!PlayerCacheUtil.getCachePermission(play, loc, block.getId(), (byte)0, TownyPermission.ActionType.BUILD))
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
		
		Iterator<Map.Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();
		String sJob = null;

		while(it.hasNext()){

			Map.Entry<String, PlayerJobs> pair = it.next();
			sJob = pair.getKey();

			if(PlayerCache.hasJob(play.getName(), sJob)){

				if(McJobs.getPlugin().isLogBlock()){
					List<Integer> lTypes = Arrays.asList(event.getBlock().getTypeId());
					
					if(McJobs.getPlugin().getBlockLogging().checkLogBlock(lTypes, play.getWorld(), play, event.getBlock().getLocation(), BlockChangeType.CREATED, timer))
						return;
				}
				else{
					if(McJobs.getPlugin().getBlockLogging().checkBuiltIn(loc, play, false))
						return;
				}

				CompCache comp = new CompCache(sJob, loc, play, block, "place");
				CompData.getCompCache().add(comp);

//				if(PlayerJobs.joblist.get(sJob).getData().compJob().compBlock(block, play, "place")){					
//					play.sendMessage("You placed " + ChatColor.DARK_GREEN + block + ChatColor.WHITE + ".");
//					if(McJobs.getPlugin().getBlockLogging().getBuiltIn().containsKey(play.getWorld()))
//						if(McJobs.getPlugin().getBlockLogging().getBuiltIn().get(play.getWorld()))
//							McJobs.getPlugin().getBlockLogging().addPlayer(loc, play, false);
//				}
			}
		}
	}
}
