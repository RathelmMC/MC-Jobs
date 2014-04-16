package com.dmgkz.mcjobs.logging;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;


public class BlockSpawners {
	static public Boolean isSpawnerNearby(Location loc, int length){
		World world = loc.getWorld();

		int x_start = loc.getBlockX() - length;
		int y_start = loc.getBlockY() - length;
		int z_start = loc.getBlockZ() - length;
		int operate = 2 * length + 1;
				

		for(int x = x_start ; x < (x_start + operate) ; x++){
			for(int y = y_start ; y < (y_start + operate) ; y++){
				for(int z = z_start ; z < (z_start + operate) ; z++){
					Block blockToCheck = world.getBlockAt(x, y, z);
					
					if(blockToCheck.getType() == Material.MOB_SPAWNER)
						return true;
				}
			}
		}
		
		return false;
	}
}
