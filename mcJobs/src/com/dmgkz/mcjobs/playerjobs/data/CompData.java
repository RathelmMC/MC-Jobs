package com.dmgkz.mcjobs.playerjobs.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PaymentCache;
import com.dmgkz.mcjobs.util.EnchantTypeAdv;
import com.dmgkz.mcjobs.util.PotionTypeAdv;

public class CompData {
	private JobsData jobsdata;

	private static ArrayList<CompCache> compcache = new ArrayList<CompCache>();
	
	public CompData(JobsData jobsdata){
		this.jobsdata = jobsdata;
	}
		
	public static ArrayList<CompCache> getCompCache(){
		return compcache;
	}
	
	public boolean compBlock(Material block, Player play, String sAction, ArrayList<PaymentCache> aPayer){
		HashMap<String, ArrayList<Material>> hJobs = this.jobsdata.getMatHash();
		if(hJobs.isEmpty())
			return false;
		
		Iterator<Map.Entry<String, ArrayList<Material>>> it = hJobs.entrySet().iterator();
		
		String tier = null;
		String paytier = null;

		String key = sAction + ".pays";		
		int iTrim = sAction.length() + 5;
		Boolean doesPay = false;

		
		while(it.hasNext()){
			Map.Entry<String, ArrayList<Material>> pair = it.next();
			tier = pair.getKey();

			if(pair.getKey().contains(sAction)){
				if(!pair.getValue().isEmpty()){

					paytier = tier.substring(iTrim, tier.length());
					int i = Integer.parseInt(paytier);
					
					if(pair.getValue().contains(block)){
						doesPay = this.jobsdata.getTierPays().get(key);

						PaymentCache payment = new PaymentCache(play, doesPay, i, this.jobsdata.getBasePay(), this.jobsdata.getName().toLowerCase());
						aPayer.add(payment);

//						McJobsEventSkillUp event = new McJobsEventSkillUp(play, doesPay, paytier, this.jobsdata.getBasePay(), this.jobsdata.getShowEveryTime(), this.jobsdata.getName());							
//						Bukkit.getServer().getPluginManager().callEvent(event);
						return true;						
					}
				}
			}				
		}
		return false;
	}
					
	public boolean compEntity(EntityType entity, Player play, String sAction, ArrayList<PaymentCache> aPayer){
		HashMap<String, ArrayList<EntityType>> hType = this.jobsdata.getEntHash();
		if(hType.isEmpty())
			return false;
		
		Iterator<Map.Entry<String, ArrayList<EntityType>>> it = hType.entrySet().iterator();

		String tier = null;
		String paytier = null;
		
		String key = sAction + ".pays";
		int iTrim = sAction.length() + 5;
		boolean doesPay = false;
		
		while(it.hasNext()){
			Map.Entry<String, ArrayList<EntityType>> pair = it.next();
			tier = pair.getKey();
			
			if(pair.getKey().contains(sAction)){
				if(!pair.getValue().isEmpty()){
				
					paytier = tier.substring(iTrim, tier.length());
					int i = Integer.parseInt(paytier);
					
					if(pair.getValue().contains(entity)){
						doesPay = this.jobsdata.getTierPays().get(key);

						PaymentCache payment = new PaymentCache(play, doesPay, i, this.jobsdata.getBasePay(), this.jobsdata.getName().toLowerCase());
						aPayer.add(payment);
						
//						McJobsEventSkillUp event = new McJobsEventSkillUp(play, doesPay, paytier, this.jobsdata.getBasePay(), this.jobsdata.getShowEveryTime(), this.jobsdata.getName());
//						Bukkit.getServer().getPluginManager().callEvent(event);
						return true;						
					}
				}
			}						
		}

		return false;
	}

	public boolean compCraft(ItemStack item, Player play, String sAction, ArrayList<PaymentCache> aPayer){
		HashMap<String, ArrayList<Material>> hJobs = this.jobsdata.getMatHash();
		if(hJobs.isEmpty())
			return false;
		
		Iterator<Map.Entry<String, ArrayList<Material>>> it = hJobs.entrySet().iterator();
		
		String tier = null;
		String paytier = null;

		String key = sAction + ".pays";		
		int iTrim = sAction.length() + 5;
		boolean doesPay = false;
//		int amount = item.getAmount();
		Material itemMat = item.getType();

		
		while(it.hasNext()){
			Map.Entry<String, ArrayList<Material>> pair = it.next();
			tier = pair.getKey();

			if(pair.getKey().contains(sAction)){
				if(!pair.getValue().isEmpty()){

					paytier = tier.substring(iTrim, tier.length());
					Iterator<Material> itMat = pair.getValue().iterator();
					int i = Integer.parseInt(paytier);
				
					while(itMat.hasNext()){
						Material mComp = itMat.next();

						if(itemMat == mComp){
							doesPay = this.jobsdata.getTierPays().get(key);

							PaymentCache payment = new PaymentCache(play, doesPay, i, this.jobsdata.getBasePay(), this.jobsdata.getName().toLowerCase());
							aPayer.add(payment);
							
//							McJobsEventSkillUp event = new McJobsEventSkillUp(play, doesPay, paytier, this.jobsdata.getBasePay() * amount, this.jobsdata.getShowEveryTime(), this.jobsdata.getName());							
//							Bukkit.getServer().getPluginManager().callEvent(event);
							return true;
						}
					}
				}
			}				
		}
		return false;
	}

	public boolean compPotions(PotionTypeAdv potion, Player play, String sAction, ArrayList<PaymentCache> aPayer){
		HashMap<String, ArrayList<PotionTypeAdv>> hType = this.jobsdata.getPotHash();
		if(hType.isEmpty())
			return false;
		
		Iterator<Map.Entry<String, ArrayList<PotionTypeAdv>>> it = hType.entrySet().iterator();

		String tier = null;
		String paytier = null;
		
		String key = sAction + ".pays";
		int iTrim = sAction.length() + 5;
		boolean doesPay = false;
		
		while(it.hasNext()){
			Map.Entry<String, ArrayList<PotionTypeAdv>> pair = it.next();
			tier = pair.getKey();
			
			if(pair.getKey().contains(sAction)){
				if(!pair.getValue().isEmpty()){
				
					paytier = tier.substring(iTrim, tier.length());
					int i = Integer.parseInt(paytier);
					
					if(pair.getValue().contains(potion)){
						doesPay = this.jobsdata.getTierPays().get(key);

						PaymentCache payment = new PaymentCache(play, doesPay, i, this.jobsdata.getBasePay(), this.jobsdata.getName().toLowerCase());
						aPayer.add(payment);
						
//						McJobsEventSkillUp event = new McJobsEventSkillUp(play, doesPay, paytier, this.jobsdata.getBasePay(), this.jobsdata.getShowEveryTime(), this.jobsdata.getName());
//						Bukkit.getServer().getPluginManager().callEvent(event);
						return true;				
					}
				}
			}						
		}

		return false;
	}

	public boolean compEnchant(Map <Enchantment, Integer> enchantments, Player play, String sAction, ArrayList<PaymentCache> aPayer){
		HashMap<String, ArrayList<EnchantTypeAdv>> hType = this.jobsdata.getEnchantHash();
		if(hType.isEmpty())
			return false;
		
		Iterator<Map.Entry<String, ArrayList<EnchantTypeAdv>>> it = hType.entrySet().iterator();

		String tier = null;
		String paytier = null;
		
		String key = sAction + ".pays";
		int iTrim = sAction.length() + 5;
		boolean doesPay = false;
		boolean bEnchanted = false;
		
		while(it.hasNext()){
			Map.Entry<String, ArrayList<EnchantTypeAdv>> pair = it.next();
			tier = pair.getKey();
			
			if(pair.getKey().contains(sAction)){
				if(!pair.getValue().isEmpty()){
				
					paytier = tier.substring(iTrim, tier.length());
					int i = Integer.parseInt(paytier);
					
					Iterator<Map.Entry<Enchantment, Integer>> its = enchantments.entrySet().iterator();
					
					while(its.hasNext()){
						Entry<Enchantment, Integer> enchantpair = its.next();
						
						EnchantTypeAdv enchant = EnchantTypeAdv.getEnchantAdv(enchantpair.getKey(), enchantpair.getValue());
						
						if(pair.getValue().contains(enchant)){
							doesPay = this.jobsdata.getTierPays().get(key);

							PaymentCache payment = new PaymentCache(play, doesPay, i, this.jobsdata.getBasePay(), this.jobsdata.getName().toLowerCase());
							aPayer.add(payment);

//							McJobsEventSkillUp event = new McJobsEventSkillUp(play, doesPay, paytier, this.jobsdata.getBasePay(), this.jobsdata.getShowEveryTime(), this.jobsdata.getName());
//							Bukkit.getServer().getPluginManager().callEvent(event);
							bEnchanted = true;						
						}
					}
				}
			}						
		}

		return bEnchanted;
	}
	
		
	public HashMap<Integer, ArrayList<Material>> getMatTypeTiers(String type){
		HashMap<Integer, ArrayList<Material>> hType = new HashMap<Integer, ArrayList<Material>>();

		String key = null;
		Integer i = 1;		
		
		while(i < 6){
			key = type + ".tier" + i.toString();
			
			if(this.jobsdata.getMatHash().containsKey(key)){
				ArrayList<Material> alTier = new ArrayList<Material>();		

				alTier.addAll(this.jobsdata.getMatHash().get(key));			
				hType.put(i, alTier);
			}
			i++;
		}
		return hType;
	}

	public HashMap<Integer, ArrayList<EntityType>> getEntTypeTiers(String type){
		HashMap<Integer, ArrayList<EntityType>> hType = new HashMap<Integer, ArrayList<EntityType>>();

		String key = null;
		Integer i = 1;
		
		while(i < 6){
			key = type + ".tier" + i.toString();
			
			if(this.jobsdata.getEntHash().containsKey(key)){
				ArrayList<EntityType> alTier = new ArrayList<EntityType>();		

				alTier.addAll(this.jobsdata.getEntHash().get(key));			
				hType.put(i, alTier);
			}
			i++;
		}
		return hType;
	}

	public HashMap<Integer, ArrayList<PotionTypeAdv>> getPotTypeTiers(String type){
		HashMap<Integer, ArrayList<PotionTypeAdv>> hType = new HashMap<Integer, ArrayList<PotionTypeAdv>>();

		String key = null;
		Integer i = 1;
		
		while(i < 6){
			key = type + ".tier" + i.toString();
			
			if(this.jobsdata.getPotHash().containsKey(key)){
				ArrayList<PotionTypeAdv> alTier = new ArrayList<PotionTypeAdv>();		

				alTier.addAll(this.jobsdata.getPotHash().get(key));			
				hType.put(i, alTier);
			}
			i++;
		}
		return hType;
	}
	
	public HashMap<Integer, ArrayList<EnchantTypeAdv>> getEnchantTypeTiers(String type){
		HashMap<Integer, ArrayList<EnchantTypeAdv>> hType = new HashMap<Integer, ArrayList<EnchantTypeAdv>>();

		String key = null;
		Integer i = 1;
		
		while(i < 6){
			key = type + ".tier" + i.toString();
			
			if(this.jobsdata.getEnchantHash().containsKey(key)){
				ArrayList<EnchantTypeAdv> alTier = new ArrayList<EnchantTypeAdv>();		

				alTier.addAll(this.jobsdata.getEnchantHash().get(key));			
				hType.put(i, alTier);
			}
			i++;
		}
		return hType;
	}

	
	public Boolean isDefault(){
		return this.jobsdata.bDefaultJob;
	}
}
