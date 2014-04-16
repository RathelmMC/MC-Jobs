package com.dmgkz.mcjobs.listeners.mcjobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.dmgkz.mcjobs.events.McJobsEventJobChange;
import com.dmgkz.mcjobs.playerdata.PlayerCache;


public class JobChangeListener implements Listener{

	static private Integer timer;
	
	@EventHandler(priority = EventPriority.LOW)
	public void mcLeaveJob(McJobsEventJobChange event){
		
		if(event.getLeave()){
			String job = event.getJob();
			String player = event.getPlayer().getName();

			PlayerCache.addReJoinTimer(player, job, timer);
		}
	}

	static public void setTimer(int i){
		if(i > 0)
			timer = i;
		else
			timer = 1;
	}
}