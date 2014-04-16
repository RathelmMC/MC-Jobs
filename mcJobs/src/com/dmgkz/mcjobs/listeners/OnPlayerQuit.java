package com.dmgkz.mcjobs.listeners;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.PlayerCache;

public class OnPlayerQuit implements Listener{

	@EventHandler(priority = EventPriority.NORMAL)
	public void onQuitting(PlayerQuitEvent event){
		Player play = event.getPlayer();
		File file = new File("./plugins/mcjobs/data/" + play.getName() + ".dat");
				
		if(file.exists()){
			if(PlayerCache.isCacheOld(play.getName())){
				file.delete();
				PlayerCache.removePlayerCache(play.getName());
			}
			else{
				McJobs.getPlugin().getLogger().info("Saving " + play.getName() + " file");
				PlayerCache.savePlayerCache(play.getName());
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onKicked(PlayerKickEvent event){
		Player play = event.getPlayer();
		File file = new File("./plugins/mcjobs/data/" + play.getName() + ".dat");
				
		if(file.exists()){
			if(PlayerCache.isCacheOld(play.getName())){
				file.delete();
				PlayerCache.removePlayerCache(play.getName());
			}
			else{
				McJobs.getPlugin().getLogger().info("Saving " + play.getName() + " file");
				PlayerCache.savePlayerCache(play.getName());
			}
		}
	}
}
