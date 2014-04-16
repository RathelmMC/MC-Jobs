package com.dmgkz.mcjobs.playerjobs;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.PlayerCache;

public class PitchJobs {

	public static void pitchJobs(Player play){
		play.sendMessage(ChatColor.DARK_GREEN + McJobs.getPlugin().getLanguage().getPitch("line0").addVariables("", play.getName(), "") + ChatColor.DARK_AQUA + " MC Jobs.");
		play.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		play.sendMessage(ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPitch("line1").addVariables("", play.getName(), ""));
		play.sendMessage(ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPitch("line2").addVariables("", play.getName(), ""));
		play.sendMessage(ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPitch("line3").addVariables("", play.getName(), ""));

		PlayerCache.setSeenPitch(play.getName(), true);
		PlayerCache.savePlayerCache(play.getName());
	}
}
