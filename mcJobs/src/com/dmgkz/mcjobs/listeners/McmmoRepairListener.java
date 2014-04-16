package com.dmgkz.mcjobs.listeners;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;
import com.gmail.nossr50.events.skills.McMMOPlayerRepairCheckEvent;

public class McmmoRepairListener implements Listener{

	@EventHandler
	public void mcMMOrepair(McMMOPlayerRepairCheckEvent event){
		Player play = event.getPlayer();
		String sJob = null;
		
		Material item = event.getRepairedObject().getType();
		Iterator<Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();


		if(MCListeners.isMultiWorld()){
			if(!play.hasPermission("mcjobs.world.all") && !play.hasPermission("mcjobs.world." + play.getWorld().getName()))
				return;
		}

		while(it.hasNext()){
			Entry<String, PlayerJobs> pair = it.next();
			sJob = pair.getKey();
			
			if(PlayerCache.hasJob(play.getName(), sJob)){				
				CompCache comp = new CompCache(sJob, play.getLocation(), play, item, "repair");
				CompData.getCompCache().add(comp);
				
//				if(PlayerJobs.joblist.get(sJob).getData().compJob().compBlock(item, play, "repair")){					
//				}
			}
		}

	}
}
