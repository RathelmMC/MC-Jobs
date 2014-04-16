package com.dmgkz.mcjobs.scheduler;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerjobs.data.CompData;

public class McJobsPreComp implements Runnable{

	@Override
	public void run() {
		ArrayList<CompCache> copycomp = new ArrayList<CompCache>();
		
		copycomp.addAll(CompData.getCompCache());
		CompData.getCompCache().clear();
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(McJobs.getPlugin(), new McJobsComp(copycomp));
	}
}
