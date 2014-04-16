package com.dmgkz.mcjobs.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;

public class Crafting implements Listener{

	private static HashMap<Player, Material> hCraft = new HashMap<Player, Material>();
	private static HashMap<Player, Material> hRepair = new HashMap<Player, Material>();
	
	@EventHandler(priority = EventPriority.LOW)
	public void craftEvent(CraftItemEvent event){
		if(event.isCancelled())
			return;
		
		HumanEntity crafter = event.getWhoClicked();
		Player play = (Player) crafter;
		
//		play.sendMessage("You getCurrentItem crafted: " + event.getCurrentItem().toString());
	    Material item = event.getCurrentItem().getType();
	    	    	

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
				if(hRepair.containsKey(play)){
					
					if(hRepair.get(play).equals(item)){
						CompCache comp = new CompCache(sJob, play.getLocation(), play, item, "repair");
						CompData.getCompCache().add(comp);
						
//						if(PlayerJobs.joblist.get(sJob).getData().compJob().compBlock(item, play, "repair")){
//							play.sendMessage("You repaired " + ChatColor.DARK_GREEN + item + ChatColor.WHITE + ".");
//							hRepair.remove(crafter);
//						}					
					}
				}
				if(hCraft.containsKey(play)){
					
					if(hCraft.get(play).equals(item)){
						CompCache comp = new CompCache(sJob, play.getLocation(), play, item, "craft");
						CompData.getCompCache().add(comp);
						
//						if(PlayerJobs.joblist.get(sJob).getData().compJob().compBlock(item, play, "craft")){
//							play.sendMessage("You crafted " + ChatColor.DARK_GREEN + item + ChatColor.WHITE + ".");
//							hCraft.remove(crafter);
//							}
					}
				}
			}
		}

		hRepair.remove(play);
		hCraft.remove(play);
	}
		@EventHandler(priority = EventPriority.LOW)
		public void preCraftEvent(PrepareItemCraftEvent event){
			if(event.getViewers() == null)
				return;
			if(event.getRecipe() == null)
				return;
			
			List<HumanEntity> playlist = event.getViewers();
			Iterator<HumanEntity> it = playlist.iterator();
			Player play = null;
			
			Material mat = event.getRecipe().getResult().getType();
			
			if(event.isRepair()){
							
				while(it.hasNext()){
					HumanEntity he = it.next();
					if(he.getName() != null){
						play = (Player) he;
						hRepair.put(play, mat);
					}
				}
			}
			else{
				
				while(it.hasNext()){
					HumanEntity he = it.next();
					if(he.getName() != null){
						play = (Player) he;
						hCraft.put(play, mat);
					}
				}
			}
		}

		@EventHandler(priority = EventPriority.LOW)
		public void invCloseEvent(InventoryCloseEvent event)
		{
			Player play = (Player) event.getPlayer();
			
			if(hRepair.containsKey(play))
				hRepair.remove(play);
			
			if(hCraft.containsKey(play))
				hCraft.remove(play);
		}

		public static HashMap<Player, Material> getRepair(){
			return hRepair;
		}

		public static HashMap<Player, Material> getCraft(){
			return hCraft;
		}

}
