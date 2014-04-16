package com.dmgkz.mcjobs.listeners;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.dmgkz.mcjobs.logging.BlockSpawners;
import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;

public class MobKill implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void mobDeath(EntityDeathEvent event){
		int length = MCListeners.getSpawnDist();
		LivingEntity mob = event.getEntity();	 
		Player play = null;
		 
		if(mob.getKiller() instanceof Player)
			 play = mob.getKiller();
		else
			return;
		
		if(BlockSpawners.isSpawnerNearby(mob.getLocation(), length) && !MCListeners.isPaySpawner()){
//			play.sendMessage("Spawner nearby, not paying.");
			return;
		}
		
//		if(!PlayerJobs.hasJobs(play) && !PlayerJobs.hasDefaultJobs(play))
//			return;

		Iterator<Map.Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();
		String sJob = null;
		

		while(it.hasNext()){
			Map.Entry<String, PlayerJobs> pair = it.next();
			sJob = pair.getKey();

			if(PlayerCache.hasJob(play.getName(), sJob)){
				CompCache comp = new CompCache(sJob, mob.getLocation(), play, mob.getType(), "defeat");
				CompData.getCompCache().add(comp);
				
//				if(PlayerJobs.joblist.get(sJob).getData().compJob().compEntity(mob.getType(), play, "defeat")){
//					play.sendMessage("You killed him!");
//				}
			}
		}
	}
}
