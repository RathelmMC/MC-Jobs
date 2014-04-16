package com.dmgkz.mcjobs.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class StringToArrayList {

	public static ArrayList<Material> getMaterialList(String str){
		ArrayList<Material> tier = new ArrayList<Material>();
		int list = 0;
		String temp = "";
		
		while(list < str.length()){
			if(((str.charAt(list) == ',' || str.charAt(list) == ' ') && temp != "") || list + 1 == str.length()){
				if(str.charAt(list) != ',' && str.charAt(list) != ' ')
					temp = temp.concat(Character.toString(str.charAt(list)));
				temp = temp.toUpperCase();
				tier.add(Material.getMaterial(temp));
				temp = "";
				list++;
			}
			else{
				temp = temp.concat(Character.toString(str.charAt(list)));
				list++;
			}
		}
		
		return tier;	}

	public static ArrayList<EntityType> getEntityList(String str){
		ArrayList<EntityType> tier = new ArrayList<EntityType>();
		int list = 0;
		String temp = "";
		
		while(list < str.length()){
			if(((str.charAt(list) == ',' || str.charAt(list) == ' ') && temp != "") || list + 1 == str.length()){
				if(str.charAt(list) != ',' && str.charAt(list) != ' ')
					temp = temp.concat(Character.toString(str.charAt(list)));
				temp = temp.toUpperCase();
				tier.add(EntityType.valueOf(temp));
				temp = "";
				list++;
			}
			else{
				temp = temp.concat(Character.toString(str.charAt(list)));
				list++;
			}
		}
		
		return tier;
	}

	public static ArrayList<PotionTypeAdv> getPotionList(String str){
		ArrayList<PotionTypeAdv> tier = new ArrayList<PotionTypeAdv>();
		int list = 0;
		String temp = "";

		while(list < str.length()){
			if(((str.charAt(list) == ',' || str.charAt(list) == ' ') && temp != "") || list + 1 == str.length()){
				if(str.charAt(list) != ',' && str.charAt(list) != ' ')
					temp = temp.concat(Character.toString(str.charAt(list)));
				temp = temp.toUpperCase();
				tier.add(PotionTypeAdv.getPotion(temp));
				temp = "";
				list++;
			}
			else{
				temp = temp.concat(Character.toString(str.charAt(list)));
				list++;
			}
		}
		
		return tier;
	}

	public static ArrayList<EnchantTypeAdv> getEnchantList(String str){
		ArrayList<EnchantTypeAdv> tier = new ArrayList<EnchantTypeAdv>();
		int list = 0;
		String temp = "";

		while(list < str.length()){
			if(((str.charAt(list) == ',' || str.charAt(list) == ' ') && temp != "") || list + 1 == str.length()){
				if(str.charAt(list) != ',' && str.charAt(list) != ' ')
					temp = temp.concat(Character.toString(str.charAt(list)));
				temp = temp.toUpperCase();
				tier.add(EnchantTypeAdv.getEnchantAdv(temp));
				temp = "";
				list++;
			}
			else{
				temp = temp.concat(Character.toString(str.charAt(list)));
				list++;
			}
		}
		
		return tier;
	}

}
