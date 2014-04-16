package com.dmgkz.mcjobs.listeners;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;

public class Fishing implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void fishEvent(PlayerFishEvent event){
		Player play = event.getPlayer();
		EntityType fish;

		if(event.getCaught() != null)
			fish = event.getCaught().getType();
		else
			return;

		if(MCListeners.isMultiWorld()){
			if(!play.hasPermission("mcjobs.world.all") && !play.hasPermission("mcjobs.world." + play.getWorld().getName()))
				return;
		}
		
		Iterator<Map.Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();
		String sJob = null;
		

		while(it.hasNext()){
			Map.Entry<String, PlayerJobs> pair = it.next();
			sJob = pair.getKey();

			if(PlayerCache.hasJob(play.getName(), sJob)){
				CompCache comp = new CompCache(sJob, play.getLocation(), play, fish, "fishing");
				CompData.getCompCache().add(comp);

//				if(PlayerJobs.joblist.get(sJob).getData().compJob().compEntity(fish, play, "fishing")){
//					play.sendMessage("You killed him!");
//				} 
			} 
		}
	}
}
