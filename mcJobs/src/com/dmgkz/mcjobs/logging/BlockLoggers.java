package com.dmgkz.mcjobs.logging;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.scheduler.McJobsLogNotifier;

import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.QueryParams.BlockChangeType;

public class BlockLoggers {
	private ArrayList<String> noLogging;
	private HashMap<Location, ArrayList<Player>> hPlayerBreakBlock;
	private HashMap<Location, ArrayList<Player>> hPlayerPlaceBlock;
	private HashMap<World, Boolean> hBuiltInWorld;
	private static long timer;
 
	public BlockLoggers(){
		noLogging         = new ArrayList<String>();
		hPlayerBreakBlock = new HashMap<Location, ArrayList<Player>>();
		hPlayerPlaceBlock = new HashMap<Location, ArrayList<Player>>();
		hBuiltInWorld     = new HashMap<World, Boolean>(); 
	}
	
	public static void setTimer(long time){
		timer = time;
	}
	
	public Boolean checkLogBlock(List<Integer> lTypes, World wWorld, Player player, Location loc, BlockChangeType bct, Integer timer){
		LogBlock logblock  = (LogBlock) Bukkit.getServer().getPluginManager().getPlugin("LogBlock");
		QueryParams params = new QueryParams(logblock);
		String sWorld = wWorld.getName();
		String sPlayer = player.getName();
		Logger log = McJobs.getPlugin().getLogger();
//		List<Block> bTypes = new ArrayList<Block>();
		
		if(noLogging.contains(sWorld)){
			if(bct == BlockChangeType.DESTROYED){
				if(checkBuiltIn(loc, player, true))
					return true;
				else return false;
			}
			else{
				if(checkBuiltIn(loc, player, false))
					return true;
				else return false;				
			}
		}

//		for(Integer temp : lTypes ){
//			bTypes.add();
//		}
			
		try {
			params.setPlayer(sPlayer);
			params.bct   = bct;
			params.limit = -1;
			params.since = timer;
			params.world = wWorld;
			params.loc   = loc;
//			params.types = bTypes;
		
			params.needDate   = true;
//			params.needType   = true;
			params.needPlayer = true;
			params.needCoords = true;

			try {
				for (BlockChange bc : logblock.getBlockChanges(params)){
					if(bc.getLocation().equals(loc))
						return true;
					}
				}
			catch (SQLException e) {
				e.printStackTrace();
				return false;
			}					
		}
		catch(NullPointerException e){
			log.severe("LogBlock logging is turned off for world: " + sWorld + ".  Defaulting to built in logger.");						
			noLogging.add(sWorld);

			if(bct == BlockChangeType.DESTROYED){
				if(checkBuiltIn(loc, player, true))
					return true;
				else return false;
			}
			else{
				if(checkBuiltIn(loc, player, false))
					return true;
				else return false;				
			}		
		}
		
		return false;
	}

	public Boolean checkHawkEye(Player play, Vector loc, Boolean isBreak){
		return false;
	}
	
	public Boolean checkBuiltIn(Location loc, Player play, Boolean isBreak){
		if(!this.getBuiltIn().containsKey(play.getWorld()))
			this.getBuiltIn().put(play.getWorld(), true);

		if(isBreak){
			if(this.hPlayerBreakBlock.containsKey(loc))
				if(this.hPlayerBreakBlock.get(loc).contains(play)){
					return true;
				}
				else{
					return false;
				}
			else{
				return false;
			}
		}
		else{
			if(this.hPlayerPlaceBlock.containsKey(loc))
				if(this.hPlayerPlaceBlock.get(loc).contains(play)){
					return true;
				}
				else{
					return false;
				}
			else{
				return false;
			}
		}
	}

	public void addPlayer(Location loc, Player play, Boolean isBreak){
		ArrayList<Player> aPlayers = new ArrayList<Player>();
		if(isBreak){
			if(this.hPlayerBreakBlock.containsKey(loc)){
				aPlayers.addAll(this.hPlayerBreakBlock.get(loc));
				aPlayers.add(play);
			
				this.hPlayerBreakBlock.put(loc, aPlayers);
			}
			else{
				aPlayers.add(play);
				this.hPlayerBreakBlock.put(loc, aPlayers);
			}
		}
		else{
			if(this.hPlayerPlaceBlock.containsKey(loc)){
				aPlayers.addAll(this.hPlayerPlaceBlock.get(loc));
				aPlayers.add(play);
			
				this.hPlayerPlaceBlock.put(loc, aPlayers);
			}
			else{
				aPlayers.add(play);
				this.hPlayerPlaceBlock.put(loc, aPlayers);
			}
		}
				
		Bukkit.getScheduler().scheduleAsyncDelayedTask(McJobs.getPlugin(), new McJobsLogNotifier(this, loc, play, isBreak), timer);;
	}

	public void removePlayer(Location loc, Player play, Boolean isBreak){
		if(isBreak){
			if(this.hPlayerBreakBlock.containsKey(loc))
				if(this.hPlayerBreakBlock.get(loc).contains(play))
					this.hPlayerBreakBlock.get(loc).remove(play);
		}
		else{
			if(this.hPlayerPlaceBlock.containsKey(loc))
				if(this.hPlayerPlaceBlock.get(loc).contains(play))
					this.hPlayerPlaceBlock.get(loc).remove(play);
		}
	}

	public HashMap<World, Boolean> getBuiltIn(){
		return this.hBuiltInWorld;
	}
}
