package com.dmgkz.mcjobs.playerdata;

import org.bukkit.entity.Player;

public class PaymentCache {
	private Player play;
	private boolean isPayed;
	private int paymentTier;
	private double basepay;
	private String jobName;
	
	public PaymentCache(Player play, boolean isPayed, int paymentTier, double basepay, String jobName){
		this.play = play;
		this.isPayed = isPayed;
		this.paymentTier = paymentTier;
		this.basepay = basepay;
		this.jobName = jobName;
	}

	public Player getPlayer(){
		return this.play;
	}
	
	public boolean getPayed(){
		return isPayed;
	}

	public int getPaymentTier(){
		return this.paymentTier;
	}

	public double getBasePay(){
		return this.basepay;
	}

	public String getJobName(){
		return this.jobName;
	}
}
