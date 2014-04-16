package com.dmgkz.mcjobs.listeners;

public class MCListeners {
	private static boolean bWG = false;
	private static boolean bTown = false;
	private static boolean bPerms = false;
	private static boolean bPaySpawner = false;
	private static boolean bMultiWorld = false;
	private static int timeinmins = 60;
	private static int spawndist = 8;
	
	public static void setWorldGuard(boolean bWGa){
		bWG = bWGa;
	}
	
	public static void setTowny(boolean bTowny){
		bTown = bTowny;
	}
	
	public static void setPerms(boolean bPerm){
		bPerms = bPerm;
	}
	
	public static void setMultiWorld(boolean bMulti){
		bMultiWorld = bMulti;
	}
	
	public static void setPaySpawner(boolean bPay){
		bPaySpawner = bPay;
	}
	
	public static void setTimeInMins(int time){
		timeinmins = time;
	}
	
	public static void setSpawnDist(int dist){
		spawndist = dist;
	}
	
	protected static boolean isWorldGuard(){
		return bWG;
	}
	
	protected static boolean isTowny(){
		return bTown;
	}

	protected static boolean isMultiWorld(){
		return bMultiWorld;
	}
	
	protected static boolean isPerms(){
		return bPerms;
	}

	protected static boolean isPaySpawner(){
		return bPaySpawner;
	}

	protected static int getTimeInMins(){
		return timeinmins;
	}

	protected static int getSpawnDist(){
		return spawndist;
	}
}
