package com.dmgkz.mcjobs.util;

public class UpperCaseFirst {

	public static String toUpperFirst(String str){
		String newString = str;
		char temp = str.charAt(0);
		
		temp = Character.toUpperCase(temp);
		
		newString = Character.toString(temp) + str.substring(1, str.length());
		
		return newString;
	}
}
