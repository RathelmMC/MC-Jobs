package com.dmgkz.mcjobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class McJobsEventJobChange extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Boolean join;
	private Boolean leave;
	private Player play;
	private String jobName;
	
	public McJobsEventJobChange(Player play, String jobName, Boolean join, Boolean leave){
		this.join = join;
		this.leave = leave;
		this.play = play;
		this.jobName = jobName;
	}
	
	public Player getPlayer(){
		return this.play;
	}
	
	public String getJob(){
		return this.jobName;
	}
	
	public Boolean getJoin(){
		return this.join;
	}
	
	public Boolean getLeave(){
		return this.leave;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}
}
