package com.dmgkz.mcjobs.playerdata;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.dmgkz.mcjobs.util.PotionTypeAdv;

public class CompCache {
	private String job;
	private String action;
	private Location loc;
	private Player player;
	private Material block;
	private EntityType entity;
	private PotionTypeAdv potion;
	private Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
	private ItemStack items;
	
	public CompCache(String sJob, Location loc, Player play, Material block, String action){
		this.job = sJob;
		this.loc = loc;
		this.player = play;
		this.block = block;
		this.action = action;		
	}

	public CompCache(String sJob, Location loc, Player play, EntityType mob, String action){
		this.job = sJob;
		this.loc = loc;
		this.player = play;
		this.entity = mob;
		this.action = action;		
	}

	public CompCache(String sJob, Location loc, Player play, PotionTypeAdv potion, String action){
		this.job = sJob;
		this.loc = loc;
		this.player = play;
		this.potion = potion;
		this.action = action;		
	}

	public CompCache(String sJob, Location loc, Player play, Map<Enchantment, Integer> enchantments, String action){
		this.job = sJob;
		this.loc = loc;
		this.player = play;
		this.enchantments.putAll(enchantments);
		this.action = action;		
	}

	public CompCache(String sJob, Location loc, Player play, ItemStack items, String action){
		this.job = sJob;
		this.loc = loc;
		this.player = play;
		this.items = items;
		this.action = action;		
	}

	public String getJob(){
		return job;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public Player getPlayer(){
		return player;
	}

	public Material getMaterial(){
		return block;
	}

	public EntityType getEntity(){
		return entity;
	}
	
	public PotionTypeAdv getPotion(){
		return potion;
	}
	
	public Map<Enchantment, Integer> getEnchants(){
		return enchantments;
	}
	
	public ItemStack getItems(){
		return items;
	}
	
	public String getAction(){
		return action;
	}
}
