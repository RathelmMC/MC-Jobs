package com.dmgkz.mcjobs.scheduler;

import java.util.ConcurrentModificationException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.logging.BlockLoggers;


public class McJobsLogNotifier implements Runnable{
	private BlockLoggers placer;
	private Player play;
	private Location loc;
	private Boolean isBreak;
	
	public McJobsLogNotifier(BlockLoggers placer, Location loc, Player play, Boolean isBreak){
		this.placer = placer;
		this.play = play;
		this.loc = loc;
		this.isBreak = isBreak;
	}

	@Override
	public void run() {
		try{
			this.placer.removePlayer(loc, play, isBreak);
		}
		catch(ConcurrentModificationException e){
			this.placer.removePlayer(loc, play, isBreak);
		}
	}
}
