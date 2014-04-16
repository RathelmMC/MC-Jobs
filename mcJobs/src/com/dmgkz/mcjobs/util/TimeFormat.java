package com.dmgkz.mcjobs.util;


import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.localization.GetLanguage;

public class TimeFormat {
	private static GetLanguage modText = McJobs.getPlugin().getLanguage();
	
	public static String getFormatedTime(Integer time){
		String str = "";
		
		if(time < 1){
			str = "1 " + modText.getJobNotify("minute").addVariables("", "", "");
			return str;
		}
		
		while(time > 0){
			if(time < 60){
				if(time == 1)
					str = str.concat(time.toString() + " " + modText.getJobNotify("minute").addVariables("", "", ""));
				else
					str = str.concat(time.toString() + " " + modText.getJobNotify("minutes").addVariables("", "", ""));
				time = 0;
			}
			else if(time >= 60 && time < 1440){
				Integer hour = 0;
				while(time >= 60){
					hour++;
					time = time - 60;
				}
				if(hour == 1)
					str = str.concat(hour.toString() + " " + modText.getJobNotify("hour").addVariables("", "", ""));
				else
					str = str.concat(hour.toString() + " " + modText.getJobNotify("hours").addVariables("", "", ""));
				if(time > 0)
					str = str.concat(" " + modText.getJobNotify("and").addVariables("", "", "") + " ");
			}
			else if(time >= 1440 && time <= 10080){
				Integer day = 0;
				while(time >= 1440){
					day++;
					time = time - 1440;
				}
				if(day == 1)
					str = str.concat(day.toString() + " " + modText.getJobNotify("day").addVariables("", "", ""));
				else
					str = str.concat(day.toString() + " " + modText.getJobNotify("days").addVariables("", "", ""));
				if(time >= 60)
					str = str.concat(" " + modText.getJobNotify("and").addVariables("", "", "") + " ");
			}
			else if(time >= 10080 && time <= 302400){
				Integer week = 0;
				while(time >= 10080){
					week++;
					time = time - 10080;
				}
				if(week == 1)
					str = str.concat(week.toString() + " " + modText.getJobNotify("week").addVariables("", "", ""));
				else
					str = str.concat(week.toString() + " " + modText.getJobNotify("weeks").addVariables("", "", ""));
				if(time >= 1440)
					str = str.concat(" " + modText.getJobNotify("and").addVariables("", "", "") + " ");
			}
			else if(time >= 302400){
				Integer month = 0;
				while(time >= 302400){
					month++;
					time = time - 302400;
				}
				if(month == 1)
					str = month.toString() + " " + modText.getJobNotify("month").addVariables("", "", "");
				else
					str = month.toString() + " " + modText.getJobNotify("months").addVariables("", "", "");
				if(time >= 1440)
					str = str.concat(" " + modText.getJobNotify("and").addVariables("", "", "") + " ");
			}
		}
		return str;
	}
}
