package com.dmgkz.mcjobs.playerjobs.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.dmgkz.mcjobs.playerjobs.display.JobsDisplay;
import com.dmgkz.mcjobs.util.EnchantTypeAdv;
import com.dmgkz.mcjobs.util.PotionTypeAdv;


public class JobsData{
	private CompData compare;
	private LoadJob loadjob;
	private JobsDisplay display;
	
	private HashMap<String, ArrayList<Material>>       		hBlocksBPC;
	private HashMap<String, ArrayList<EntityType>>      	hBlocksK;
	private HashMap<String, ArrayList<PotionTypeAdv>>   	hPotions;
	private HashMap<String, ArrayList<EnchantTypeAdv>>		hEnchants;
	private HashMap<String, Boolean> bTierPays;
	
	protected boolean[] bShow;
	protected boolean[] bCP;
	protected boolean   bShowEveryTime;
	protected boolean   bDefaultJob;
	
	protected String sJobName;
	protected String sDescription;
	
	protected double dBasepay;
	protected double exp_modifier;

	public JobsData(){
		compare = new CompData(this);
		loadjob = new LoadJob(this);
		display = new JobsDisplay(this);
		
		hBlocksBPC = new HashMap<String, ArrayList<Material>>();
		hBlocksK   = new HashMap<String, ArrayList<EntityType>>();
		hPotions   = new HashMap<String, ArrayList<PotionTypeAdv>>();
		hEnchants  = new HashMap<String, ArrayList<EnchantTypeAdv>>();
		bTierPays  = new HashMap<String, Boolean>();
		
		bShow = new boolean[8];
		bCP   = new boolean[2];
		Arrays.fill(bShow, false);
		Arrays.fill(bCP, false);
		
		bShowEveryTime = false;
		bDefaultJob    = false;
		
		sJobName     = "no_name";
		sDescription = "There is no description for this job.";
		
		dBasepay = 0.25D;
		exp_modifier = 1D;
	}

	public boolean getShowEveryTime(){
		return this.bShowEveryTime;
	}
	
	public Double getBasePay(){
		return this.dBasepay;
	}
	
	public double getEXP(){
		return this.exp_modifier;
	}
	
	public LoadJob loadJob(){
		return this.loadjob;
	}
	
	public CompData compJob(){
		return this.compare;
	}
		
	public JobsDisplay display(){
		return this.display;
	}
	
	public HashMap<String, ArrayList<Material>> getMatHash(){
		return this.hBlocksBPC;
	}

	public HashMap<String, ArrayList<EntityType>> getEntHash(){
		return this.hBlocksK;
	}
	
	public HashMap<String, ArrayList<PotionTypeAdv>> getPotHash(){
		return this.hPotions;
	}
	
	public HashMap<String, ArrayList<EnchantTypeAdv>> getEnchantHash(){
		return this.hEnchants;
	}
	
	public String getName(){
		return this.sJobName;
	}
	
	public String getDesc(){
		return this.sDescription;
	}
	
	public boolean getCostPay(boolean isPay){
		if(isPay)
			return bCP[0];
		else
			return bCP[1];
	}
	public boolean getShow(int i){
		return bShow[i];
	}

	public HashMap<String, Boolean> getTierPays(){
		return this.bTierPays;
	}
}
