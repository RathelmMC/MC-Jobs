package com.dmgkz.mcjobs.prettytext;

public class AddTextVariables {
	private String str;
	
	public AddTextVariables(String str){
		this.str = str;
	}
	
	public String addVariables(String jobName, String playerName, String generic){
		String sWorking = null;
		
		if(str == null)
			return null;
		
		str = str.replaceAll("%j", jobName);
		str = str.replaceAll("%p", playerName);
		str = str.replaceAll("%g", generic);
		
		sWorking = str;
		
		return sWorking;	
	}
}
