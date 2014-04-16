package com.dmgkz.mcjobs.playerjobs.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import com.dmgkz.mcjobs.util.EnchantTypeAdv;
import com.dmgkz.mcjobs.util.PotionTypeAdv;
import com.dmgkz.mcjobs.util.StringToArrayList;

public class LoadJob {
	private JobsData jobsdata;
	
	public LoadJob(JobsData jobsdata){
		this.jobsdata = jobsdata;
	}

	public void setupJob(ConfigurationSection section){
		Iterator<String> it = section.getKeys(true).iterator();
		String str = "";
		
		@SuppressWarnings("unused")
		Logger log = Bukkit.getServer().getLogger();

	
		while(it.hasNext()){
			String key = it.next();
		
			if(key.equalsIgnoreCase("basepay")){
				this.setBasePay(section.getDouble(key));
			}
		
			if(key.equalsIgnoreCase("description")){
				this.setDesc(section.getString(key));
			}
			
			if(key.equalsIgnoreCase("show_every_time")){
				this.setShowEveryTime(section.getBoolean(key));
			}
			
			if(key.equalsIgnoreCase("exp")){
				this.setEXP(section.getDouble(key));
			}
			
			if(key.equalsIgnoreCase("default")){
				this.setDefault(section.getBoolean(key));
			}
			
			if(key.equalsIgnoreCase("break.pays") || key.equalsIgnoreCase("place.pays") || key.equalsIgnoreCase("defeat.pays") || key.equalsIgnoreCase("craft.pays") || key.equalsIgnoreCase("repair.pays") || key.equalsIgnoreCase("fishing.pays") || key.equalsIgnoreCase("enchant.pays") || key.equalsIgnoreCase("potions.pays")){
				Boolean isPays = section.getBoolean(key);
				this.setCostPay(isPays);
				this.setTierPays(key, isPays);				
			}
		
			if(key.equalsIgnoreCase("break.hide") || key.equalsIgnoreCase("place.hide") || key.equalsIgnoreCase("defeat.hide") || key.equalsIgnoreCase("craft.hide") || key.equalsIgnoreCase("repair.hide") || key.equalsIgnoreCase("fishing.hide") || key.equalsIgnoreCase("potions.hide") || key.equalsIgnoreCase("enchant.hide")){
				boolean isHide = section.getBoolean(key);
				this.setHide(key, isHide);						
			}
			
			if(key.equalsIgnoreCase("break.tier1")  || key.equalsIgnoreCase("break.tier2")  || key.equalsIgnoreCase("break.tier3")  || key.equalsIgnoreCase("break.tier4")  || key.equalsIgnoreCase("break.tier5")
			|| key.equalsIgnoreCase("place.tier1")  || key.equalsIgnoreCase("place.tier2")  || key.equalsIgnoreCase("place.tier3")  || key.equalsIgnoreCase("place.tier4")  || key.equalsIgnoreCase("place.tier5")	
			|| key.equalsIgnoreCase("craft.tier1")  || key.equalsIgnoreCase("craft.tier2")  || key.equalsIgnoreCase("craft.tier3")  || key.equalsIgnoreCase("craft.tier4")  || key.equalsIgnoreCase("craft.tier5")	
			|| key.equalsIgnoreCase("repair.tier1") || key.equalsIgnoreCase("repair.tier2") || key.equalsIgnoreCase("repair.tier3") || key.equalsIgnoreCase("repair.tier4") || key.equalsIgnoreCase("repair.tier5")){
		
					ArrayList<Material> tier = new ArrayList<Material>();
					str = section.getString(key);
					
					tier.addAll(StringToArrayList.getMaterialList(str));
					this.setMat(key, tier);

//					log.info(this.jobsdata.getMatHash().get(key));
//					log.info(this.jobsdata.getMatHash().get(key).get(0).toString());
				}
			
			if(key.equalsIgnoreCase("defeat.tier1")  || key.equalsIgnoreCase("defeat.tier2")  || key.equalsIgnoreCase("defeat.tier3")   || key.equalsIgnoreCase("defeat.tier4")   || key.equalsIgnoreCase("defeat.tier5")
			|| key.equalsIgnoreCase("fishing.tier1") || key.equalsIgnoreCase("fishing.tier2") || key.equalsIgnoreCase("fishing.tier3")  || key.equalsIgnoreCase("fishing.tier4")  || key.equalsIgnoreCase("fishing.tier5")){
				ArrayList<EntityType> tier = new ArrayList<EntityType>();
				str = section.getString(key);
					
				tier.addAll(StringToArrayList.getEntityList(str));
				this.setEntity(key, tier);						
			}

			if(key.equalsIgnoreCase("potions.tier1") || key.equalsIgnoreCase("potions.tier2") || key.equalsIgnoreCase("potions.tier3") || key.equalsIgnoreCase("potions.tier4") || key.equalsIgnoreCase("potions.tier5")){
				ArrayList<PotionTypeAdv> tier = new ArrayList<PotionTypeAdv>();
				str = section.getString(key);
					
				tier.addAll(StringToArrayList.getPotionList(str));
				this.setPotions(key, tier);
			}
			if(key.equalsIgnoreCase("enchant.tier1") || key.equalsIgnoreCase("enchant.tier2") || key.equalsIgnoreCase("enchant.tier3") || key.equalsIgnoreCase("enchant.tier4") || key.equalsIgnoreCase("enchant.tier5")){
				ArrayList<EnchantTypeAdv> tier = new ArrayList<EnchantTypeAdv>();
				str = section.getString(key);
				
				tier.addAll(StringToArrayList.getEnchantList(str));
				this.setEnchants(key, tier);
			}
		}
	}

	public void setBasePay(double pay){
		jobsdata.dBasepay = pay;
	}

	public void setEXP(double exp){
		jobsdata.exp_modifier = exp;
	}
	
	private void setMat(String key, ArrayList<Material> tier){
		this.jobsdata.getMatHash().put(key, tier);
	}

	private void setEntity(String key, ArrayList<EntityType> tier){
		this.jobsdata.getEntHash().put(key, tier);
	}
	
	private void setPotions(String key, ArrayList<PotionTypeAdv> tier){
		this.jobsdata.getPotHash().put(key, tier);
	}
	
	private void setEnchants(String key, ArrayList<EnchantTypeAdv> tier){
		this.jobsdata.getEnchantHash().put(key, tier);
	}
	
	private void setTierPays(String key, Boolean isPays){
		this.jobsdata.getTierPays().put(key, isPays);
	}
	
	public void setName(String name){
		this.jobsdata.sJobName = name;
	}

	public void setDesc(String desc){
		this.jobsdata.sDescription = desc;
	}
	public void setCostPay(boolean isPays){
		if(isPays)
			this.jobsdata.bCP[0] = true;
		else
			this.jobsdata.bCP[1] = true;
	}
	
	public void setShowEveryTime(boolean bShowEveryTime){
		this.jobsdata.bShowEveryTime = bShowEveryTime;
	}
	
	public void setDefault(boolean bDefaultJob){
		this.jobsdata.bDefaultJob = bDefaultJob;
	}
	
	public void setHide(String block, boolean isHide){
		if(block.equalsIgnoreCase("break.hide"))
			this.jobsdata.bShow[0] = isHide;
		else if(block.equalsIgnoreCase("place.hide"))
			this.jobsdata.bShow[1] = isHide;
		else if(block.equalsIgnoreCase("defeat.hide"))
			this.jobsdata.bShow[2] = isHide;
		else if(block.equalsIgnoreCase("craft.hide"))
			this.jobsdata.bShow[3] = isHide;
		else if(block.equalsIgnoreCase("repair.hide"))
			this.jobsdata.bShow[4] = isHide;
		else if(block.equalsIgnoreCase("fishing.hide"))
			this.jobsdata.bShow[5] = isHide;
		else if(block.equalsIgnoreCase("enchant.hide"))
			this.jobsdata.bShow[6] = isHide;
		else if(block.equalsIgnoreCase("potions.hide"))
			this.jobsdata.bShow[7] = isHide;
		else
			return;
	}
}