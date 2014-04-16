package com.dmgkz.mcjobs.playerjobs.levels;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Leveler {
	static private HashMap<Integer, String> hRanks = new HashMap<Integer, String>();
	static private double xpmod;
	static private double paymod;
	
	
	static public HashMap<Integer, String> getRanks(){
		return hRanks;
	}
	
	static public void setXPMod(double i){
		if(i > 0)
			xpmod = i;
		else
			xpmod = 1;
	}
	
	static public void setPayMod(double i){
		if(i > 0)
			paymod = i;
		else
			paymod = 1;
	}
	
	static public String getRank(int level){
		int i = level;

		while(i > 0){
			if(Leveler.hRanks.containsKey(i))
				return Leveler.hRanks.get(i);
			i--;
		}

		return "novice";
	}
	
	static public double getXPtoLevel(int level){
		double xpNeeded;
		
		xpNeeded = (2 * (level * level) + 10 * level - 3) * xpmod;
		
		return xpNeeded;
	}
	
	static public String getXPtoLevelDisplay(int level){
		double xpNeeded;
		String value;
		DecimalFormat df = new DecimalFormat("#,###,###,##0");
		
		
		xpNeeded = (2 * (level * level) + 10 * level - 3) * xpmod;
		value = df.format(xpNeeded);
		
		return value;
	}
	
	static public double getMultiplier(int level){
		double multi = 1;
		
		if(level <= 20)
			multi = 0.75 * 0.1 * level * paymod;
		
		if(level > 20)
			multi = 0.75 * ((0.0085 * level + 2) * paymod);
		
		return multi;
	}
}
