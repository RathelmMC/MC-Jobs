package com.dmgkz.mcjobs.scheduler;

import java.util.ArrayList;
import java.util.Iterator;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerdata.CompCache;
import com.dmgkz.mcjobs.playerdata.PaymentCache;
import com.dmgkz.mcjobs.playerdata.PlayerCache;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import com.dmgkz.mcjobs.playerjobs.pay.PayMoney;
import com.dmgkz.mcjobs.playerjobs.pay.PayXP;

public class McJobsComp implements Runnable{
	private ArrayList<CompCache> aComp;
	private ArrayList<PaymentCache> aPayer;
	private static boolean bVault;
	private static boolean bRegister;
	private static boolean bXP;
	
	public McJobsComp(ArrayList<CompCache> aComp){
		this.aComp = aComp;
		this.aPayer = new ArrayList<PaymentCache>();
	}
	
	@Override
	public void run() {
		Iterator<CompCache> it = aComp.iterator();
		
		while(it.hasNext()){
			CompCache comp = it.next();
			
			if(comp.getAction().equalsIgnoreCase("break")){
				if(PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compBlock(comp.getMaterial(), comp.getPlayer(), comp.getAction(), aPayer)){
					if(McJobs.getPlugin().getBlockLogging().getBuiltIn().containsKey(comp.getPlayer().getWorld()))
						if(McJobs.getPlugin().getBlockLogging().getBuiltIn().get(comp.getPlayer().getWorld()))
							McJobs.getPlugin().getBlockLogging().addPlayer(comp.getLocation(), comp.getPlayer(), true);	
				}
			}
			else if(comp.getAction().equalsIgnoreCase("place")){
				if(PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compBlock(comp.getMaterial(), comp.getPlayer(), comp.getAction(), aPayer)){
					if(McJobs.getPlugin().getBlockLogging().getBuiltIn().containsKey(comp.getPlayer().getWorld()))
						if(McJobs.getPlugin().getBlockLogging().getBuiltIn().get(comp.getPlayer().getWorld()))
							McJobs.getPlugin().getBlockLogging().addPlayer(comp.getLocation(), comp.getPlayer(), false);
				}				
			}
			else if(comp.getAction().equalsIgnoreCase("defeat"))
				PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compEntity(comp.getEntity(), comp.getPlayer(), comp.getAction(), aPayer);
			else if(comp.getAction().equalsIgnoreCase("fishing"))
				PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compEntity(comp.getEntity(), comp.getPlayer(), comp.getAction(), aPayer);
			else if(comp.getAction().equalsIgnoreCase("potions"))
				PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compPotions(comp.getPotion(), comp.getPlayer(), comp.getAction(), aPayer);
			else if(comp.getAction().equalsIgnoreCase("enchant"))
				PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compEnchant(comp.getEnchants(), comp.getPlayer(), comp.getAction(), aPayer);
			else if(comp.getAction().equalsIgnoreCase("craft")){
				PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compBlock(comp.getMaterial(), comp.getPlayer(), comp.getAction(), aPayer);
//					Crafting.getCraft().remove(comp.getPlayer());
			}
			else if(comp.getAction().equalsIgnoreCase("repair")){
				PlayerJobs.getJobsList().get(comp.getJob()).getData().compJob().compBlock(comp.getMaterial(), comp.getPlayer(), comp.getAction(), aPayer);
//					Crafting.getRepair().remove(comp.getPlayer());

			}
		}
		
		Iterator<PaymentCache> its = aPayer.iterator();
		
		while(its.hasNext()){
			PaymentCache payment = its.next();
			String str = "";

			
			if(payment.getPayed()){
				if(bVault)
					str = PayMoney.payVault(payment.getPlayer(), payment.getPaymentTier(), payment.getBasePay(), payment.getJobName());
				if(bRegister)
					str = PayMoney.payRegister(payment.getPlayer(), payment.getPaymentTier(), payment.getBasePay(), payment.getJobName());
				if(bXP)
					str = PayXP.payXP(payment.getPlayer(), payment.getPaymentTier(), payment.getBasePay(), payment.getJobName());
			}
			else{
				if(bVault)
					str = PayMoney.chargeVault(payment.getPlayer(), payment.getPaymentTier(), payment.getBasePay(), payment.getJobName());
				if(bRegister)
					str = PayMoney.chargeRegister(payment.getPlayer(), payment.getPaymentTier(), payment.getBasePay(), payment.getJobName());
				if(bXP)
					str = PayXP.chargeXP(payment.getPlayer(), payment.getPaymentTier(), payment.getBasePay(), payment.getJobName());				
			}
			
			double xpPayment = payment.getPaymentTier() * PlayerJobs.getJobsList().get(payment.getJobName()).getData().getEXP();
			PlayerCache.addExp(payment.getPlayer().getName(), payment.getJobName().toLowerCase(), xpPayment);

			if(str != ""){
				if(PlayerCache.getShowEveryTime(payment.getPlayer().getName(), payment.getJobName()))
					payment.getPlayer().sendMessage(str);
			}
		}

		aPayer.clear();

		if(bVault)
			PayMoney.makeEconomyCall(true);
		if(bRegister)
			PayMoney.makeEconomyCall(false);
	}

	public static void setVault(boolean b){
		bVault = b;
	}

	public static void setRegister(boolean b){
		bRegister = b;
	}
	
	public static void setXP(boolean b){
		bXP = b;
	}
}
