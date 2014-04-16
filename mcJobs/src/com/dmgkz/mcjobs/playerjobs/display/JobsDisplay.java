package com.dmgkz.mcjobs.playerjobs.display;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.localization.GetLanguage;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.data.JobsData;
import com.dmgkz.mcjobs.playerjobs.levels.Leveler;
import com.dmgkz.mcjobs.prettytext.PrettyText;
import com.dmgkz.mcjobs.util.EnchantTypeAdv;
import com.dmgkz.mcjobs.util.PotionTypeAdv;

public class JobsDisplay {
	private JobsData jobsdata;
	private GetLanguage modText = McJobs.getPlugin().getLanguage();

	
	public JobsDisplay(JobsData jobsdata){
		this.jobsdata = jobsdata;
	}
	public void showJob(Player play){
		String sJob = null;
		String sCost = "";
		String sPays = "";
		String sJobName = this.jobsdata.getName();
		String payscale = this.jobsdata.getBasePay().toString();
		PrettyText text = new PrettyText();
		
		if(PlayerCache.hasJob(play.getName(), sJobName.toLowerCase())){
			DecimalFormat df = new DecimalFormat("###,###.####");
			Double i = this.jobsdata.getBasePay() * Leveler.getMultiplier(PlayerCache.getJobLevel(play.getName(), sJobName.toLowerCase()));
			payscale = df.format(i);
		}
		
		sJob = sJobName.toUpperCase();

		if(sJob.length() < 12)
			while(sJob.length() < 12){
				sJob = sJob.concat(" ");
			}
		else if(sJob.length() > 12)
			sJob = sJob.substring(0, 12);

		if(PlayerCache.hasJob(play.getName(), sJobName.toLowerCase()))
			sJob = sJob.concat(" " + modText.getJobDisplay("employed").addVariables(sJobName, play.getName(), ""));
		else if(this.jobsdata.compJob().isDefault())
			sJob = sJob.concat(" " + modText.getJobDisplay("default").addVariables(sJobName, play.getName(), ""));
		else
			sJob = sJob.concat(" " + modText.getJobDisplay("unemployed").addVariables(sJobName, play.getName(), ""));
		
		sJob = sJob.concat(PrettyText.addSpaces(modText.getSpaces("display")) + ChatColor.GREEN + modText.getJobDisplay("basepay").addVariables(sJobName, play.getName(), "") + " " + payscale);
		
		if(this.jobsdata.getCostPay(false))
			sCost = ChatColor.RED + modText.getJobDisplay("charge").addVariables(sJobName, play.getName(), "");
		else
			sCost = PrettyText.addSpaces(modText.getSpaces("chargelen"));
		
		if(this.jobsdata.getCostPay(true))
			sPays = ChatColor.GREEN + modText.getJobDisplay("pay").addVariables(sJobName, play.getName(), "");
		else
			sPays = "";
		
		if(this.jobsdata.getCostPay(true) && this.jobsdata.getCostPay(false))
			sJob = sJob.concat(" - " + ChatColor.RED + modText.getJobDisplay("charge").addVariables(sJobName, play.getName(), "") + ChatColor.DARK_GRAY + "/" + ChatColor.GREEN + modText.getJobDisplay("pay").addVariables(sJobName, play.getName(), ""));
		else
			sJob = sJob.concat(" - " + sCost + sPays);

		play.sendMessage(ChatColor.DARK_AQUA + sJob);

		if(PlayerCache.hasJob(play.getName(), sJobName.toLowerCase())){
			String level = PlayerCache.getJobLevel(play.getName(), sJobName.toLowerCase()).toString();
			String xp = PlayerCache.getJobExpDisplay(play.getName(), sJobName.toLowerCase());
			String xpNeeded = Leveler.getXPtoLevelDisplay(PlayerCache.getJobLevel(play.getName(), sJobName.toLowerCase()));
			String rank = PlayerCache.getJobRank(play.getName(), sJobName.toLowerCase());
			
			play.sendMessage(ChatColor.DARK_AQUA + modText.getJobDisplay("level").addVariables(sJobName, play.getName(), "") + ChatColor.DARK_GREEN + ": " + level + PrettyText.addSpaces(modText.getSpaces("displaytwo")) + 
				         	 ChatColor.DARK_AQUA + modText.getJobDisplay("rank").addVariables(sJobName, play.getName(), "") + ChatColor.DARK_GREEN + ": " + rank + PrettyText.addSpaces(modText.getSpaces("displaythree")) +
							 ChatColor.DARK_AQUA + modText.getJobDisplay("exp").addVariables(sJobName, play.getName(), "") + ChatColor.DARK_GREEN + ": " + xp + "/" + xpNeeded);
		}
		
		sJob = ChatColor.GRAY + this.jobsdata.getDesc();		
		text.formatPlayerText(sJob, play);

		if(!this.jobsdata.compJob().getMatTypeTiers("break").isEmpty() && !this.getHide("break")){
			HashMap<Integer, ArrayList<Material>> hType = new HashMap<Integer, ArrayList<Material>>();
			hType = this.jobsdata.compJob().getMatTypeTiers("break");
			Iterator<Map.Entry<Integer, ArrayList<Material>>> it = hType.entrySet().iterator();

			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("break").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");

			this.buildMatTiers(it, play);
		}

		if(!this.jobsdata.compJob().getMatTypeTiers("place").isEmpty() && !this.getHide("place")){
			HashMap<Integer, ArrayList<Material>> hType = new HashMap<Integer, ArrayList<Material>>();
			hType = this.jobsdata.compJob().getMatTypeTiers("place");
			Iterator<Map.Entry<Integer, ArrayList<Material>>> it = hType.entrySet().iterator();
			
			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("place").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildMatTiers(it, play);
		}
		
		if(!this.jobsdata.compJob().getEntTypeTiers("defeat").isEmpty() && !this.getHide("defeat")){
			HashMap<Integer, ArrayList<EntityType>> hType = new HashMap<Integer, ArrayList<EntityType>>();
			hType = this.jobsdata.compJob().getEntTypeTiers("defeat");
			Iterator<Map.Entry<Integer, ArrayList<EntityType>>> it = hType.entrySet().iterator();

			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("defeat").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildEntTiers(it, play);

		}
		if(!this.jobsdata.compJob().getEntTypeTiers("fishing").isEmpty() && !this.getHide("fishing")){
			HashMap<Integer, ArrayList<EntityType>> hType = new HashMap<Integer, ArrayList<EntityType>>();
			hType = this.jobsdata.compJob().getEntTypeTiers("fishing");
			Iterator<Map.Entry<Integer, ArrayList<EntityType>>> it = hType.entrySet().iterator();

			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("fishing").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildEntTiers(it, play);

		}
		if(!this.jobsdata.compJob().getMatTypeTiers("craft").isEmpty() && !this.getHide("craft")){
			HashMap<Integer, ArrayList<Material>> hType = new HashMap<Integer, ArrayList<Material>>();
			hType = this.jobsdata.compJob().getMatTypeTiers("craft");
			Iterator<Map.Entry<Integer, ArrayList<Material>>> it = hType.entrySet().iterator();
			
			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("craft").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildMatTiers(it, play);
		}
		if(!this.jobsdata.compJob().getMatTypeTiers("repair").isEmpty() && !this.getHide("repair")){
			HashMap<Integer, ArrayList<Material>> hType = new HashMap<Integer, ArrayList<Material>>();
			hType = this.jobsdata.compJob().getMatTypeTiers("repair");
			Iterator<Map.Entry<Integer, ArrayList<Material>>> it = hType.entrySet().iterator();
			
			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("repair").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildMatTiers(it, play);
		}
		if(!this.jobsdata.compJob().getPotTypeTiers("potions").isEmpty() && !this.getHide("potions")){
			HashMap<Integer, ArrayList<PotionTypeAdv>> hType = new HashMap<Integer, ArrayList<PotionTypeAdv>>();
			hType = this.jobsdata.compJob().getPotTypeTiers("potions");
			Iterator<Map.Entry<Integer, ArrayList<PotionTypeAdv>>> it = hType.entrySet().iterator();
			
			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("potions").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildPotTiers(it, play);
		}
		if(!this.jobsdata.compJob().getEnchantTypeTiers("enchant").isEmpty() && !this.getHide("enchant")){
			HashMap<Integer, ArrayList<EnchantTypeAdv>> hType = new HashMap<Integer, ArrayList<EnchantTypeAdv>>();
			hType = this.jobsdata.compJob().getEnchantTypeTiers("enchant");
			Iterator<Map.Entry<Integer, ArrayList<EnchantTypeAdv>>> it = hType.entrySet().iterator();
			
			play.sendMessage(ChatColor.DARK_GRAY + "----------------------------------------------------");
			play.sendMessage(ChatColor.YELLOW + modText.getJobDisplay("enchant").addVariables(sJobName, play.getName(), ""));
			play.sendMessage("");
			
			this.buildEnchantTiers(it, play);
		}
	}
	
	private void buildMatTiers(Iterator<Map.Entry<Integer, ArrayList<Material>>> it, Player play){
		PrettyText text = new PrettyText();
		
		while(it.hasNext()){
			Map.Entry<Integer, ArrayList<Material>> pair = it.next();
			Integer tier = 0;
			String data = null;
			String str = null;
			
			tier = pair.getKey();
			data = text.formatMaterialTiers(pair.getValue(), ChatColor.DARK_GREEN);
			str = ChatColor.GOLD + modText.getJobDisplay("tier").addVariables("", play.getName(), "") + tier.toString() + ": " + ChatColor.DARK_GREEN + data + ChatColor.GRAY + "." + ChatColor.DARK_GREEN;
			
			text.formatPlayerText(str, play);
		}

	}

	private void buildEntTiers(Iterator<Map.Entry<Integer, ArrayList<EntityType>>> it, Player play){
		PrettyText text = new PrettyText();
		
		while(it.hasNext()){
			Map.Entry<Integer, ArrayList<EntityType>> pair = it.next();
			Integer tier = 0;
			String data = null;
			String str = null;
			
			tier = pair.getKey();
			data = text.formatEntityTiers(pair.getValue(), ChatColor.DARK_GREEN);
			str = ChatColor.GOLD + modText.getJobDisplay("tier").addVariables("", play.getName(), "") + tier.toString() + ": " + ChatColor.DARK_GREEN + data + ChatColor.GRAY + "." + ChatColor.DARK_GREEN;
			
			text.formatPlayerText(str, play);
		}

	}

	private void buildPotTiers(Iterator<Map.Entry<Integer, ArrayList<PotionTypeAdv>>> it, Player play){
		PrettyText text = new PrettyText();
		
		while(it.hasNext()){
			Map.Entry<Integer, ArrayList<PotionTypeAdv>> pair = it.next();
			Integer tier = 0;
			String data = null;
			String str = null;
			
			tier = pair.getKey();
			data = text.formatPotionTiers(pair.getValue(), ChatColor.DARK_GREEN);
			str = ChatColor.GOLD + modText.getJobDisplay("tier").addVariables("", play.getName(), "") + tier.toString() + ": " + ChatColor.DARK_GREEN + data + ChatColor.GRAY + "." + ChatColor.DARK_GREEN;
			
			text.formatPlayerText(str, play);
		}

	}

	private void buildEnchantTiers(Iterator<Map.Entry<Integer, ArrayList<EnchantTypeAdv>>> it, Player play){
		PrettyText text = new PrettyText();
		
		while(it.hasNext()){
			Map.Entry<Integer, ArrayList<EnchantTypeAdv>> pair = it.next();
			Integer tier = 0;
			String data = null;
			String str = null;
			
			tier = pair.getKey();
			data = text.formatEnchantTiers(pair.getValue(), ChatColor.DARK_GREEN);
			str = ChatColor.GOLD + modText.getJobDisplay("tier").addVariables("", play.getName(), "") + tier.toString() + ": " + ChatColor.DARK_GREEN + data + ChatColor.GRAY + "." + ChatColor.DARK_GREEN;
			
			text.formatPlayerText(str, play);
		}

	}

	public Boolean getHide(String block){
		if(block.equalsIgnoreCase("break"))
			return this.jobsdata.getShow(0);
		else if(block.equalsIgnoreCase("place"))
			return this.jobsdata.getShow(1);
		else if(block.equalsIgnoreCase("defeat"))
			return this.jobsdata.getShow(2);
		else if(block.equalsIgnoreCase("craft"))
			return this.jobsdata.getShow(3);
		else if(block.equalsIgnoreCase("repair"))
			return this.jobsdata.getShow(4);
		else if(block.equalsIgnoreCase("fishing"))
			return this.jobsdata.getShow(5);
		else if(block.equalsIgnoreCase("enchant"))
			return this.jobsdata.getShow(6);
		else if(block.equalsIgnoreCase("potions"))
			return this.jobsdata.getShow(7);
		else return false;
	}
}
