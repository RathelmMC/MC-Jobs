package com.dmgkz.mcjobs.scheduler;

import java.util.Iterator;

import com.dmgkz.mcjobs.playerdata.PlayerCache;


public class McJobsRemovePerm implements Runnable {
		
	@Override
	public void run() {
		Iterator<String> it = PlayerCache.getPlayerPerms().iterator();
		
		while(it.hasNext()){
			String player = it.next();

			if(PlayerCache.decrementTimer(player)){
				it.remove();
				PlayerCache.savePlayerPerms();
			}
		}
	}
}
