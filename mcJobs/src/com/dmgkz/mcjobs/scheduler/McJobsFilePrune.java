package com.dmgkz.mcjobs.scheduler;

import java.io.File;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.PlayerCache;

public class McJobsFilePrune implements Runnable{

	@Override
	public void run() {
		File directory = new File("./plugins/mcjobs/data");

		if(!directory.exists())
			return;
		
		File[] listoffiles = directory.listFiles();
		
		for(int i = 0; i < listoffiles.length ; i++){
			String name = listoffiles[i].getName();
			
			if(name.substring(name.length() - 3, name.length()).equalsIgnoreCase("dat")){
				name = name.substring(0, name.length() - 4);
				
				if(PlayerCache.isCacheOld(name)){
					McJobs.getPlugin().getLogger().info(name + ".dat is old and will be deleted.");
					
					PlayerCache.removePlayerCache(name);
					listoffiles[i].delete();
				}
			}
		}
	}
}
