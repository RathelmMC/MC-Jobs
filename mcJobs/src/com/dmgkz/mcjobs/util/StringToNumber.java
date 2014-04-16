package com.dmgkz.mcjobs.util;

public class StringToNumber {
	static public boolean isPositiveNumber(String str){

		for(char c : str.toCharArray()){
			if(!Character.isDigit(c))
				return false;
		}
		
		return true;
	}
}
