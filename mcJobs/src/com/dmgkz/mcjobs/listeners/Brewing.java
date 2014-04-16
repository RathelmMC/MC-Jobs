package com.dmgkz.mcjobs.listeners;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.data.CompData;
import com.dmgkz.mcjobs.util.PotionTypeAdv;

public class Brewing implements Listener{
	private HashMap<InventoryHolder, Player> hBrewStands = new HashMap<InventoryHolder, Player>();
	private Logger log = McJobs.getPlugin().getLogger();
	
	@EventHandler(priority = EventPriority.LOW)
	public void brewing(BrewEvent event){
		InventoryHolder brewStand = event.getContents().getHolder();
		Player play = null;
		
		if(event.isCancelled())
			return;
		
		if(hBrewStands.containsKey(brewStand))
			play = hBrewStands.get(brewStand);
		else
			return;
		
		if(!play.isOnline())
			return;
		
		if(MCListeners.isMultiWorld()){
			if(!play.hasPermission("mcjobs.world.all") && !play.hasPermission("mcjobs.world." + play.getWorld().getName()))
				return;
		}
		
		Iterator<Map.Entry<String, PlayerJobs>> itJobs = PlayerJobs.getJobsList().entrySet().iterator();
		String sJob = null;

		while(itJobs.hasNext()){
			Map.Entry<String, PlayerJobs> pair = itJobs.next();
			sJob = pair.getKey();

			
			ListIterator<ItemStack> it = event.getContents().iterator();
			ItemStack item;

			ItemStack ingred = event.getContents().getIngredient();
		
			while(it.hasNext()){
				item = it.next();

				if(item != null && item.getData().getItemTypeId() == 373){
					Short sPotion = item.getDurability();
					PotionTypeAdv potion = PotionTypeAdv.getMakeResults(sPotion, ingred.getType());

					if(McJobs.getPlugin().getConfig().getBoolean("advanced.debug")){
						if(potion == null){
							play.sendMessage("This potion does not exist: " + sPotion.toString() + " " + ingred.getType().toString()); 
						}
						else
							play.sendMessage("PotionType = " + sPotion.toString() + " ingredient = " + ingred.getType().toString() + " Result = " + potion.toString());
					}
					
					try{
						if(!PotionTypeAdv.isHigherTier(potion, PotionTypeAdv.getPotion(sPotion)))
							return;
					}
					catch(NullPointerException e){
						log.info("Potion Short " + sPotion.toString() + " is not in PotionTypeAdv ENUM.  isHigherTier failed.");
					}
					
					if(PlayerCache.hasJob(play.getName(), sJob)){

						CompCache comp = new CompCache(sJob, play.getLocation(), play, potion, "potions");
						CompData.getCompCache().add(comp);

//						if(PlayerJobs.joblist.get(sJob).getData().compJob().compPotions(potion, play, "potions")){
//							play.sendMessage("You brewed " + ChatColor.DARK_GREEN + potion.toString() + ChatColor.WHITE + ".");
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.LOW)
	public void getBrewStand(InventoryClickEvent event){
		Player play = (Player) event.getWhoClicked();
		
		if(event.isCancelled())
			return;
				
		if(event.getSlotType() == SlotType.CRAFTING && event.getSlot() == 3 && event.getInventory().getName().equalsIgnoreCase("container.brewing")){
			InventoryHolder brewStand = event.getInventory().getHolder();
			
			hBrewStands.put(brewStand, play);

		}
	}
}
