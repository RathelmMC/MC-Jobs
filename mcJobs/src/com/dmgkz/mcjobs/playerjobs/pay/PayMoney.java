package com.dmgkz.mcjobs.playerjobs.pay;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.levels.Leveler;
import com.dmgkz.mcjobs.prettytext.PrettyText;
import com.nijikokun.register.payment.Methods;

public class PayMoney {
	private static double maxPay = -1;
	private static DecimalFormat df = new DecimalFormat("#,##0.0#");
	private static HashMap<String, Double>payCache = new HashMap<String, Double>();
	
	public static String payVault(Player play, int tier, double basepay, String job){
		double payAmount = 0.0;
		double iTimes = tier;
		double totalPay = PlayerCache.getEarnedIncome(play.getName());
		
		job = job.toLowerCase();

		String sSingCur = "";
		String sPlurCur = "";
		String str = "";
		
		payAmount = basepay * iTimes * Leveler.getMultiplier(PlayerCache.getJobLevel(play.getName(), job));
		 
		if(overLimit(payAmount, totalPay, play))
			return str;

		if(totalPay + payAmount >= maxPay && maxPay > 0)
			payAmount = maxPay - totalPay + 1;

		totalPay = totalPay + payAmount;
		PlayerCache.setEarnedIncome(play.getName(), totalPay);
			
//		if(!McJobs.getEconomy().hasAccount(play.getName())){
//			McJobs.getEconomy().createPlayerAccount(play.getName());
//		}
		
		if(payCache.containsKey(play.getName())){
			double temp = payCache.get(play.getName());
			temp = temp + payAmount;
			payCache.put(play.getName(), temp);
		}
		else
			payCache.put(play.getName(), payAmount);
		
//		McJobs.getEconomy().depositPlayer(play.getName(), payAmount);
		
		if(McJobs.getEconomy().currencyNameSingular() != "")
			sSingCur = McJobs.getEconomy().currencyNameSingular();
		else
			sSingCur = McJobs.getPlugin().getLanguage().getPayment("currency_single").addVariables("", "", "");

		if(McJobs.getEconomy().currencyNamePlural() != "")
			sPlurCur = McJobs.getEconomy().currencyNamePlural();
		else
			sPlurCur = McJobs.getPlugin().getLanguage().getPayment("currency_plural").addVariables("", "", "");

		if(payAmount == 1){
				str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("pay").addVariables(job, sSingCur, df.format(payAmount));
		}
		else if(payAmount != 1){
				str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("pay").addVariables(job, sPlurCur, df.format(payAmount));
		}
		
		return str;
	}

	public static String payRegister(Player play, int tier, double basepay, String job){
		double payAmount = 0.0;
		double iTimes = tier;
		double totalPay = PlayerCache.getEarnedIncome(play.getName());

		job = job.toLowerCase();
		
		String sSingCur = McJobs.getPlugin().getLanguage().getPayment("currency_single").addVariables("", "", "");
		String sPlurCur = McJobs.getPlugin().getLanguage().getPayment("currency_plural").addVariables("", "", "");
		String str = "";

		payAmount = basepay * iTimes * Leveler.getMultiplier(PlayerCache.getJobLevel(play.getName(), job));
		 
		if(overLimit(payAmount, totalPay, play))
			return str;
		
		if(totalPay + payAmount >= maxPay && maxPay > 0)
			payAmount = maxPay - totalPay + 1;

		totalPay = totalPay + payAmount;		
		PlayerCache.setEarnedIncome(play.getName(), totalPay);
			
//		if(Methods.getMethod().getAccount(play.getName()) == null){
//			Methods.getMethod().createAccount(play.getName());
//		}

		if(payCache.containsKey(play.getName())){
			double temp = payCache.get(play.getName());
			temp = temp + payAmount;
			payCache.put(play.getName(), temp);
		}
		else
			payCache.put(play.getName(), payAmount);
		
//		Methods.getMethod().getAccount(play.getName()).add(payAmount);

		if(payAmount == 1){
			str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("pay").addVariables(job, sSingCur, df.format(payAmount));
		}
		else if(payAmount != 1){
			str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("pay").addVariables(job, sPlurCur, df.format(payAmount));
		}		

		return str;
	}
	
	public static String chargeVault(Player play, int tier, double basepay, String job){
		double payAmount = 0.0;
		double iTimes = tier;
		double totalPay = PlayerCache.getEarnedIncome(play.getName());
		
		job = job.toLowerCase();
		
		String sSingCur = "";
		String sPlurCur = "";
		String str = "";

		payAmount = basepay * iTimes * Leveler.getMultiplier(PlayerCache.getJobLevel(play.getName(), job));
		payAmount = payAmount * PlayerJobs.getPercent() / 100.0;

		
		totalPay = totalPay - payAmount;
		PlayerCache.setEarnedIncome(play.getName(), totalPay);
		
//		if(!McJobs.getEconomy().hasAccount(play.getName())){
//			McJobs.getEconomy().createPlayerAccount(play.getName());
//		}

		if(payCache.containsKey(play.getName())){
			double temp = payCache.get(play.getName());
			temp = temp - payAmount;
			payCache.put(play.getName(), temp);
		}
		else{
			double temp = -1 * payAmount;
			payCache.put(play.getName(), temp);
		}

//		McJobs.getEconomy().withdrawPlayer(play.getName(), payAmount);

		if(McJobs.getEconomy().currencyNameSingular() != "")
			sSingCur = McJobs.getEconomy().currencyNameSingular();
		else
			sSingCur = McJobs.getPlugin().getLanguage().getPayment("currency_single").addVariables("", "", "");

		if(McJobs.getEconomy().currencyNamePlural() != "")
			sPlurCur = McJobs.getEconomy().currencyNamePlural();
		else
			sPlurCur = McJobs.getPlugin().getLanguage().getPayment("currency_plural").addVariables("", "", "");

		if(payAmount == 1){
			str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("charge").addVariables(job, sSingCur, df.format(payAmount));
		}
		else if(payAmount != 1){
			str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("charge").addVariables(job, sPlurCur, df.format(payAmount));
		}
		
		return str;
	}

	public static String chargeRegister(Player play, int tier, double basepay, String job){
		double payAmount = 0.0;
		double iTimes = tier;
		double totalPay = PlayerCache.getEarnedIncome(play.getName());

		job = job.toLowerCase();
		
		String sSingCur = McJobs.getPlugin().getLanguage().getPayment("currency_single").addVariables("", "", "");
		String sPlurCur = McJobs.getPlugin().getLanguage().getPayment("currency_plural").addVariables("", "", "");
		String str = "";
		
		payAmount = basepay * iTimes * Leveler.getMultiplier(PlayerCache.getJobLevel(play.getName(), job));
		payAmount = payAmount * PlayerJobs.getPercent() / 100.0;		 

		
		totalPay = totalPay - payAmount;
		PlayerCache.setEarnedIncome(play.getName(), totalPay);
						
//		if(Methods.getMethod().getAccount(play.getName()) == null){
//			Methods.getMethod().createAccount(play.getName());
//		}

		if(payCache.containsKey(play.getName())){
			double temp = payCache.get(play.getName());
			temp = temp - payAmount;
			payCache.put(play.getName(), temp);
		}
		else
			payCache.put(play.getName(), -1 * payAmount);

		
//		Methods.getMethod().getAccount(play.getName()).subtract(payAmount);

		if(payAmount == 1){
			str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("charge").addVariables(job, sSingCur, df.format(payAmount));
		}
		else if(payAmount != 1){
			str = ChatColor.GREEN + McJobs.getPlugin().getLanguage().getPayment("charge").addVariables(job, sPlurCur, df.format(payAmount));
		}
		
		return str;
	}
	
	public static void setMaxPay(double d){
		maxPay = d;
	}
	
	private static boolean overLimit(double payamount, double playertotal, Player player){
		if(maxPay <= 0)
			return false;
		
		if(playertotal > maxPay)
			return true;
		
		if(payamount + playertotal > maxPay){
			PrettyText text = new PrettyText();
			String str = McJobs.getPlugin().getLanguage().getJobNotify("overpay").addVariables("", player.getName(), "");

			text.formatPlayerText(str, player);
			return false;
		}		
		
		return false;
	}

	public static void makeEconomyCall(boolean bVault){
		if(bVault){
			Iterator<Entry<String, Double>> it = payCache.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Double> pair = it.next();
				
				if(pair.getValue() > 0)
					McJobs.getEconomy().depositPlayer(pair.getKey(), pair.getValue());
				else if(pair.getValue() < 0){
					double temp = pair.getValue() * -1;
					McJobs.getEconomy().withdrawPlayer(pair.getKey(), temp);
				}
				
				it.remove();
			}
		}
		if(!bVault){
			Iterator<Entry<String, Double>> it = payCache.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Double> pair = it.next();
				
				if(pair.getValue() > 0)
					Methods.getMethod().getAccount(pair.getKey()).add(pair.getValue());
				else if(pair.getValue() < 0){
					double temp = pair.getValue() * -1;
					Methods.getMethod().getAccount(pair.getKey()).subtract(temp);
				}
				it.remove();
			}
			
		}	
	}
}
