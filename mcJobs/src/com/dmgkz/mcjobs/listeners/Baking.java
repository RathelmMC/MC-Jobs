package com.dmgkz.mcjobs.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;

public class Baking implements Listener {
	private HashMap<InventoryHolder, Player> hFurnaces = new HashMap<InventoryHolder, Player>();
	@SuppressWarnings("unused")
	private Logger log = Bukkit.getServer().getLogger();
	
	@EventHandler(priority = EventPriority.LOW)
	public void getFurnaceCook(InventoryClickEvent event){
		 SlotType entFurn = event.getSlotType();
		 Integer slotID = event.getSlot();
		 InventoryHolder furnace;
		 
		 if(entFurn == SlotType.CONTAINER && slotID == 0 && event.getInventory().getName().equalsIgnoreCase("container.furnace")){
			 Player play = (Player) event.getWhoClicked();
			 ItemStack itemPlaced = event.getCursor();

			 furnace = event.getInventory().getHolder();

			 if(itemPlaced.getType() != Material.AIR){
				 this.hFurnaces.put(furnace, play);				 
			 }
		 }
	}

	@EventHandler(priority = EventPriority.LOW)
	public void furnaceBurn(FurnaceSmeltEvent event){
		Block bfurnace = event.getBlock();
		Furnace furnace = (Furnace) bfurnace.getState();
		
		InventoryHolder key = furnace.getInventory().getHolder();
		
		if(this.hFurnaces.containsKey(key)){
			Iterator<Map.Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();
			String sJob = null;
			Player play = this.hFurnaces.get(key);
			
			if(!play.isOnline())
				return;

			if(MCListeners.isMultiWorld()){
				if(!play.hasPermission("mcjobs.world.all") && !play.hasPermission("mcjobs.world." + play.getWorld().getName()))
					return;
			}

			Material food = event.getResult().getType();
			
			while(it.hasNext()){

				Map.Entry<String, PlayerJobs> pair = it.next();
				sJob = pair.getKey();

				if(PlayerCache.hasJob(play.getName(), sJob)){

					CompCache comp = new CompCache(sJob, play.getLocation(), play, food, "craft");
					CompData.getCompCache().add(comp);

//					if(PlayerJobs.joblist.get(sJob).getData().compJob().compCraft(food, play, "craft")){		
//						play.sendMessage("You cooked " + ChatColor.DARK_GREEN + amount.toString() + " " + food.toString() + ChatColor.WHITE + ".");
//					}					
				}
			}
		}	
	}
}
