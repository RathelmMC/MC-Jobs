package com.dmgkz.mcjobs.playerjobs;

import java.util.HashMap;


import com.dmgkz.mcjobs.playerjobs.data.JobsData;


public class PlayerJobs {

    private static Integer percentCost = 100;
	
	private static HashMap<String, PlayerJobs> joblist = new HashMap<String, PlayerJobs>();
   
    private JobsData data;
    
    public PlayerJobs(){
    	data = new JobsData();
    }

    public static HashMap<String, PlayerJobs> getJobsList(){
    	return joblist;
    }
    
    public JobsData getData(){
    	return this.data;
    }

	public static void setPercent(int i){
		PlayerJobs.percentCost = i;
	}
	
	public static Integer getPercent(){
		return PlayerJobs.percentCost;
	}
}