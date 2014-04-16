package com.dmgkz.mcjobs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;


public class PlayerUtils {
	private static boolean bVault = false;
	private static boolean bFailed = false;
	private static HashMap<String, Integer> maxdefaults = new HashMap<String, Integer>();
	
	public static int getAllowed(String player){
		int i = maxdefaults.get("default");
		
		if(bVault){
			if(bFailed)
				return i;

			try{
				Permission permission = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class).getProvider();
				List<String> lGroups = new ArrayList<String>();
				Player play;
			
				if(Bukkit.getPlayer(player) != null)
					play = Bukkit.getPlayer(player);
				else
					return i;
			
				if(permission.getPlayerGroups(play) != null)
					lGroups = Arrays.asList(permission.getPlayerGroups(play));
				else 
					return i;
			
				Iterator<String> it = lGroups.iterator();
				while(it.hasNext()){
					String group = it.next().toLowerCase();

					if(maxdefaults.containsKey(group)){
						if(i < maxdefaults.get(group))
							i = maxdefaults.get(group);
					}
				}
			}
			catch(Exception e){
				McJobs.getPlugin().getLogger().info("Your permission mod does not support player groups.  Using default max jobs only.");
				bFailed = true;
			}
			
		}
		return i;
	}

	public static int getAllowed(){
		return maxdefaults.get("default");
	}
	
	public static void setVault(boolean b){
		bVault = b;
	}

	public static HashMap<String, Integer> getMaxDefaults(){
		return maxdefaults;
	}
}
