package com.dmgkz.mcjobs.prettytext;

//import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.util.EnchantTypeAdv;
import com.dmgkz.mcjobs.util.PotionTypeAdv;

public class PrettyText {

	public void formatPlayerText(String str, Player play){
		// 64 is the length of a line.
		int LENGTH = 59;
		
		ArrayList<String> sFormat = new ArrayList<String>();
		String sTempHolder = "";
		String sLine = "";
		String pCC = "";
		
		char cc = ChatColor.COLOR_CHAR;

		int num = 0;
		int stopper = 0;
		int ulet = 0;
		int unew = 0;
		int maxlen = LENGTH;

		while(num < str.length()){
			while(str.charAt(num) != ' '){
				if(str.charAt(num) == cc){
					maxlen = maxlen + 2;
					pCC = Character.toString(cc) + Character.toString(str.charAt(num + 1));
				}
				if(str.charAt(num) == 'i' || str.charAt(num) == 'l' || str.charAt(num) == 't' || str.charAt(num) == ',' || str.charAt(num) == '.' || str.charAt(num) == ':')
					unew++;
				sTempHolder = sTempHolder.concat(Character.toString(str.charAt(num)));
				num++;
				if(!(num < str.length()))
					break;
			}
			ulet = unew + ulet;
			while(ulet >= 6 && stopper < 2){
				if(ulet >= 6){
					maxlen = maxlen + 2;
					ulet = ulet - 6;
				}
				stopper++;
			}
			stopper = 0;

			if(sTempHolder.length() + sLine.length() < maxlen - 1){
				sLine = sLine.concat(sTempHolder + " ");
				sTempHolder = "";
			}
			else if(sTempHolder.length() + sLine.length() == maxlen || sTempHolder.length() + sLine.length() == maxlen - 1){
				sLine = sLine.concat(sTempHolder);
				if(sLine.endsWith(" "))
					sLine = sLine.substring(0, sLine.length() - 1);
				sFormat.add(sLine);
				sTempHolder = pCC + "";
				sLine = "";
				maxlen = LENGTH;
				ulet = 0;
			}
			else{
				if(sLine.endsWith(" "))
					sLine = sLine.substring(0, sLine.length() - 1);
				sFormat.add(sLine);
				sLine = "";
				sLine = sLine.concat(pCC + sTempHolder + " ");
				sTempHolder = pCC + "";
				maxlen = LENGTH;
				ulet = unew;
			}
			num++;
			unew = 0;
		}
		if(!sLine.isEmpty())
			sFormat.add(sLine);

		this.PrintText(sFormat, play);
	}

	
	public String formatMaterialTiers(ArrayList<Material> material, ChatColor cc){
		String str = "";
		String end =  ChatColor.GRAY + ","+ cc + " ";
		Iterator<Material> it = material.iterator();

		while(it.hasNext()){
			Material test = it.next();

			if(!str.contains(McJobs.getPlugin().getLanguage().getMaterial(test.toString())))
				str = str.concat(McJobs.getPlugin().getLanguage().getMaterial(test.toString()) + end);
			
		}
		str = str.substring(0, str.length() - 6);
		str = str + cc;
		return str;
	}
	
	public String formatEntityTiers(ArrayList<EntityType> entity, ChatColor cc){
		String str = "";
		String end =  ChatColor.GRAY + ","+ cc + " ";
		Iterator<EntityType> it = entity.iterator();
		
		while(it.hasNext()){
			EntityType test = it.next();
			
			str = str.concat(McJobs.getPlugin().getLanguage().getEntity(test.toString()) + end);
		}

		str = str.substring(0, str.length() - 6);
		str = str + cc;
		return str;
	}

	public String formatPotionTiers(ArrayList<PotionTypeAdv> potion, ChatColor cc){
		String str = "";
		String end =  ChatColor.GRAY + ","+ cc + " ";
		Iterator<PotionTypeAdv> it = potion.iterator();
		
		while(it.hasNext()){
			PotionTypeAdv test = it.next();
			
			str = str.concat(McJobs.getPlugin().getLanguage().getPotion(test.toString()) + end);
		}

		str = str.substring(0, str.length() - 6);
		str = str + cc;
		return str;
	}
	
	public String formatEnchantTiers(ArrayList<EnchantTypeAdv> enchant, ChatColor cc){
		String str = "";
		String end =  ChatColor.GRAY + ","+ cc + " ";
		Iterator<EnchantTypeAdv> it = enchant.iterator();
		
		while(it.hasNext()){
			EnchantTypeAdv test = it.next();
			
			str = str.concat(McJobs.getPlugin().getLanguage().getEnchant(test.toString()) + end);
		}

		str = str.substring(0, str.length() - 6);
		str = str + cc;
		return str;
	}
	
	private void PrintText(ArrayList<String> str, Player play) {
		Iterator<String> it = str.iterator();

		while(it.hasNext()){
			play.sendMessage(it.next().toString());
		}
	}

	public static String colorText(String str){
		String sWorking = null;
		
		if(str == null)
			return null;
		
		sWorking = str.replaceAll("&([0-9a-f])", ChatColor.COLOR_CHAR + "$1");
				
		return sWorking;
	}
	
	public static String addSpaces(int i){
		String str = "";
		
		for(int it = 0; it < i; it++){
			str = str.concat(" ");
		}
		
		return str;
	}
}

