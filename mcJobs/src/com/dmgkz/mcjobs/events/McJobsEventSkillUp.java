package com.dmgkz.mcjobs.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class McJobsEventSkillUp extends Event{
	private static final HandlerList handlers = new HandlerList();
	private Player play;
	private Boolean doesPay;
	private Boolean bShowEveryTime;
	private String tier;
	private String sName;
	private Double basepay;
	private boolean bCancel;
	
	public McJobsEventSkillUp(Player play, Boolean doesPay, String tier, Double basepay, Boolean bShowEveryTime, String sName){
		this.play = play;
		this.doesPay = doesPay;
		this.tier = tier;
		this.basepay = basepay;
		this.bShowEveryTime = bShowEveryTime;
		this.sName = sName;
	}
	
	public Boolean getDoesPay(){
		return this.doesPay;
	}
	
	public Boolean getShowEveryTime(){
		return this.bShowEveryTime;
	}
	
	public Player getPlayer(){
		return this.play;
	}
	
	public String getTier(){
		return this.tier;
	}
	
	public Double getBasePay(){
		return this.basepay;
	}	
	
	public String getJobName(){
		return this.sName;
	}
	
	public boolean isCancelled(){
		return bCancel;
	}
	
	public void setCancel(boolean bCancel){
		this.bCancel = bCancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList(){
		return handlers;
	}
}
