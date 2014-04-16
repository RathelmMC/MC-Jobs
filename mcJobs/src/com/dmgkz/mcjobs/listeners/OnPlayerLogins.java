package com.dmgkz.mcjobs.listeners;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.prettytext.PrettyText;

public class OnPlayerLogins implements Listener{

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player play = event.getPlayer();
		Integer version = McJobs.getPlugin().getVersion();
		
		PlayerCache.verifyPlayerCache(play.getName());
		
		if((play.hasPermission("mcjobs.admin") || play.isOp())){
/*			if(McJobs.getPlugin().isPayXP() && (McJobs.getPlugin().getPayScale().equalsIgnoreCase("low") || McJobs.getPlugin().getPayScale().equalsIgnoreCase("normal"))){
				PrettyText text = new PrettyText();
				String str = McJobs.getPlugin().getLanguage().getAdminLogin("toolow").addVariables("", play.getName(), "");
				text.formatPlayerText(str, play);				
			} */
			
			if(version != McJobs.VERSION || version == null){
				PrettyText text = new PrettyText();
				String str = McJobs.getPlugin().getLanguage().getAdminLogin("outofdate").addVariables("", play.getName(), "");
				text.formatPlayerText(str, play);
			}
		}
		
		if(!play.hasPermission("mcjobs.admin.leavedefault")){
			Iterator<Entry<String, PlayerJobs>> it = PlayerJobs.getJobsList().entrySet().iterator();

			while(it.hasNext()){
				Entry<String, PlayerJobs> pair = it.next();
				
				if(pair.getValue().getData().compJob().isDefault()){
					PlayerCache.addJob(play.getName(), pair.getKey());
				}
			}
		}			
	}
}
