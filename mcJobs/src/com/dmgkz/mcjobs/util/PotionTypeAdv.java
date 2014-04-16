package com.dmgkz.mcjobs.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;


public enum PotionTypeAdv {
	WATER("water",0, 0),       
	AWKWARD("awkward", 16, 1),        
	THICK("thick", 32, 1),           
	MUNDANE("mundane", 8192, 1),
	MUNDANE_EXT("mundane_ext", 64, 1),             
	REGEN("regen", 8193, 2),    
	REGEN_EXT("regen_ext", 8257, 3),    
	REGEN_TWO("regen_two", 8225, 3),     
	REGEN_TWO_EXT("regen_two_ext", 8289, 4),     
	REGEN_SPL("regen_spl", 16385, 3), 
	REGEN_EXT_SPL("regen_ext_spl", 16449, 4),
	REGEN_TWO_SPL("regen_two_spl", 16481, 4),
	SWIFT("swift", 8194, 2),    
	SWIFT_EXT("swift_ext", 8258, 3),    
	SWIFT_TWO("swift_two", 8226, 3),     
	SWIFT_TWO_EXT("swift_two_ext", 8290, 4),     
	SWIFT_SPL("swift_spl", 16386, 3),
	SWIFT_EXT_SPL("swift_ext_spl", 16450, 4),
	SWIFT_TWO_SPL("swift_two_spl", 16482, 4),
	FIRE_RES("fire_res", 8195, 2), 
	FIRE_RES_EXT("fire_res_ext", 8259, 3), 
	FIRE_RES_SPL("fire_res_spl", 16387, 3), 
	FIRE_RES_EXT_SPL("fire_res_ext_spl", 16483, 4),
	POISON("poison", 8196, 2),   
	POISON_EXT("poison_ext", 8260, 3),   
	POISON_TWO("poison_two", 8228, 3),    
	POISON_TWO_EXT("poison_two_ext", 8292, 4),    
	POISON_SPL("poison_spl", 16388, 3),
	POISON_EXT_SPL("poison_ext_spl", 16452, 4),
	POISON_TWO_SPL("poison_two_spl", 16484, 4),
	HEALTH("health", 8197, 2),   
	HEALTH_TWO("health_two", 8229, 3),   
	HEALTH_SPL("health_spl", 16389, 3),   
	HEALTH_TWO_SPL("health_two_spl", 16421, 4),
	WEAK("weak", 8200, 2),     
	WEAK_EXT("weak_ext", 8264, 3),     
	WEAK_TWO("weak_two", 8232, 3),      
	WEAK_SPL("weak_spl", 16392, 3),
	WEAK_EXT_SPL("weak_ext_spl", 16456, 4),
	WEAK_TWO_SPL("weak_two_spl", 16424, 4),
	STRENGTH("strength", 8201, 2), 
	STRENGTH_EXT("strength_ext", 8265, 3), 
	STRENGTH_TWO("strength_two", 8233, 3),  
	STRENGTH_TWO_EXT("strength_two_ext", 8297, 4),  
	STRENGTH_SPL("strength_spl", 16393, 3),
	STRENGTH_EXT_SPL("strength_ext_spl", 16457, 4),
	STRENGTH_TWO_SPL("strength_two_spl", 16425, 4),
	SLOW("slow", 8202, 3),     
	SLOW_EXT("slow_ext", 8266, 4),     
	SLOW_TWO("slow_two", 8234, 4),      
	SLOW_SPL("slow_spl", 16394, 4),
	SLOW_EXT_SPL("slow_ext_spl", 16458, 5),
	SLOW_TWO_SPL("slow_two_spl", 16426, 5),
	HARM("harm", 8204, 3),     
	HARM_TWO("harm_two", 8236, 4),     
	HARM_SPL("harm_spl", 16396, 4),     
	HARM_TWO_SPL("harm_two_spl", 16428, 5),
	REV_FIRE_RES("rev_fire_res", 8227, 3),
	REV_FIRE_RES_SPL("rev_fire_res_spl", 16419, 4),
	REV_SLOW("rev_slow", 8234, 4),
	REV_SLOW_SPL("rev_slow_spl", 16426, 5),
	REV_HEALTH("rev_health", 8261, 3),
	REV_HEALTH_SPL("rev_health_spl", 16453, 4),
	REV_HARM("rev_harm", 8268, 4),
	REV_HARM_SPL("rev_harm_spl", 16460, 5),
	REV_WEAK("rev_weak", 8232, 3),
	REV_WEAK_SPL("rev_weak_spl", 16424, 4);
	
	
	private final Short typeID;
	private final String name;
	private final Short tier;
	private static final Map<String, PotionTypeAdv> NAME_MAP = new HashMap<String, PotionTypeAdv>();
	private static final Map<Short, PotionTypeAdv> ID_MAP = new HashMap<Short, PotionTypeAdv>();
	private static final Map<PotionTypeAdv, Short> TIER = new HashMap<PotionTypeAdv, Short>();
	
	static {
		for(PotionTypeAdv type : values()){
			if(type.name != null){
				NAME_MAP.put(type.name.toLowerCase(), type);
			}
			if(type.typeID >= 0){
				ID_MAP.put(type.typeID, type);
			}
			if(type.tier >= 0){
				TIER.put(type, type.tier);
			}
		}		
	}
	
	PotionTypeAdv(String name, Integer value, Integer tier){
		this.typeID = value.shortValue();
		this.name = name;
		this.tier = tier.shortValue();
	}
	
	public Short getPotionValue(){
		return this.typeID;
	}
	
	public static PotionTypeAdv getPotion(String str){
		PotionTypeAdv type = null;

		if(NAME_MAP.containsKey(str.toLowerCase())){
			type = NAME_MAP.get(str.toLowerCase());
		}
				
		return type;
	}

	public static PotionTypeAdv getPotion(Short value){
		PotionTypeAdv type = null;

		if(ID_MAP.containsKey(value)){
			type = ID_MAP.get(value);
		}
		
		return type;
	}

	public Short getTier(){
		Short value = TIER.get(this);
		
		return value;
	}
	
	public static Boolean isHigherTier(PotionTypeAdv potOne, PotionTypeAdv potTwo){
		if(potOne == null || potTwo == null)
			return false;
					
		if(potOne.getTier() > potTwo.getTier())
			return true;
		
		else return false;
	}
	
	public static PotionTypeAdv getMakeResults(Short potionType, Material material){
		PotionTypeAdv newPotion = null;
		
		if(material == Material.NETHER_STALK){
			if(potionType == 0){
				return PotionTypeAdv.AWKWARD;
			}
		}
		else if(material == Material.GLOWSTONE_DUST){
			if(potionType == 0)
				return PotionTypeAdv.THICK;
			else if(potionType == 8259)
				return PotionTypeAdv.REV_FIRE_RES;
			else if(potionType == 8194)
				return PotionTypeAdv.SWIFT_TWO;
			else if(potionType == 8197)
				return PotionTypeAdv.HEALTH_TWO;
			else if(potionType == 8261)
				return PotionTypeAdv.HEALTH_TWO;
			else if(potionType == 8196)
				return PotionTypeAdv.POISON_TWO;
			else if(potionType == 8193)
				return PotionTypeAdv.REGEN_TWO;
			else if(potionType == 8201)
				return PotionTypeAdv.STRENGTH_TWO;
			else if(potionType == 8266)
				return PotionTypeAdv.REV_SLOW;
			else if(potionType == 8258)
				return PotionTypeAdv.SWIFT_TWO;
			else if(potionType == 8204)
				return PotionTypeAdv.HARM_TWO;
			else if(potionType == 8268)
				return PotionTypeAdv.HARM_TWO;
			else if(potionType == 8260)
				return PotionTypeAdv.POISON_TWO;
			else if(potionType == 8257)
				return PotionTypeAdv.REGEN_TWO;
			else if(potionType == 8264)
				return PotionTypeAdv.REV_WEAK;
			else if(potionType == 8265)
				return PotionTypeAdv.STRENGTH_TWO;
			else if(potionType == 16483)
				return PotionTypeAdv.REV_FIRE_RES_SPL;
			else if(potionType == 16458)
				return PotionTypeAdv.REV_SLOW_SPL;
			else if(potionType == 16386)
				return PotionTypeAdv.SWIFT_TWO_SPL;
			else if(potionType == 16450)
				return PotionTypeAdv.SWIFT_TWO_SPL;
			else if(potionType == 16389)
				return PotionTypeAdv.HEALTH_TWO_SPL;
			else if(potionType == 16453)
				return PotionTypeAdv.HEALTH_TWO_SPL;
			else if(potionType == 16396)
				return PotionTypeAdv.HARM_TWO_SPL;
			else if(potionType == 16460)
				return PotionTypeAdv.HARM_TWO_SPL;
			else if(potionType ==16388)
				return PotionTypeAdv.POISON_TWO_SPL;
			else if(potionType == 16452)
				return PotionTypeAdv.POISON_TWO_SPL;
			else if(potionType == 16385)
				return PotionTypeAdv.REGEN_TWO_SPL;
			else if(potionType == 16393)
				return PotionTypeAdv.STRENGTH_TWO_SPL;
			else if(potionType == 16449)
				return PotionTypeAdv.REGEN_TWO_SPL;
			else if(potionType == 16456)
				return PotionTypeAdv.REV_WEAK_SPL;
			else if(potionType == 16457)
				return PotionTypeAdv.STRENGTH_TWO_SPL;
		}
		else if(material == Material.FERMENTED_SPIDER_EYE){
			if(potionType == 0)
				return PotionTypeAdv.WEAK;
			else if(potionType == 8192)
				return PotionTypeAdv.WEAK;
			else if(potionType == 16)
				return PotionTypeAdv.WEAK;
			else if(potionType == 32)
				return PotionTypeAdv.WEAK;
			else if(potionType == 64)
				return PotionTypeAdv.WEAK_EXT;
			else if(potionType == 8195)
				return PotionTypeAdv.SLOW;
			else if(potionType == 8227)
				return PotionTypeAdv.SLOW;
			else if(potionType == 16387)
				return PotionTypeAdv.SLOW_SPL;
			else if(potionType == 16419)
				return PotionTypeAdv.SLOW_SPL;
			else if(potionType == 8194)
				return PotionTypeAdv.SLOW;
			else if(potionType == 16386)
				return PotionTypeAdv.SLOW_SPL;
			else if(potionType == 8197)
				return PotionTypeAdv.HARM;
			else if(potionType == 8261)
				return PotionTypeAdv.HARM;
			else if(potionType == 16389)
				return PotionTypeAdv.HARM_SPL;
			else if(potionType == 16453)
				return PotionTypeAdv.HARM_SPL;
			else if(potionType == 8196)
				return PotionTypeAdv.HARM;
			else if(potionType == 16388)
				return PotionTypeAdv.HARM_SPL;
			else if(potionType == 8193)
				return PotionTypeAdv.WEAK;
			else if(potionType == 16385)
				return PotionTypeAdv.WEAK_SPL;
			else if(potionType == 8201)
				return PotionTypeAdv.WEAK;
			else if(potionType == 16393)
				return PotionTypeAdv.WEAK_SPL;
			else if(potionType == 8226)
				return PotionTypeAdv.SLOW;
			else if(potionType == 16482)
				return PotionTypeAdv.SLOW_SPL;
			else if(potionType == 8259)
				return PotionTypeAdv.SLOW_EXT;
			else if(potionType == 16483)
				return PotionTypeAdv.SLOW_EXT_SPL;
			else if(potionType == 8258)
				return PotionTypeAdv.SLOW_EXT;
			else if(potionType == 16450)
				return PotionTypeAdv.SLOW_EXT_SPL;
			else if(potionType == 8229)
				return PotionTypeAdv.HARM_TWO;
			else if(potionType == 16421)
				return PotionTypeAdv.HARM_TWO_SPL;
			else if(potionType == 8260)
				return PotionTypeAdv.HARM;
			else if(potionType == 16452)
				return PotionTypeAdv.HARM_SPL;
			else if(potionType == 8228)
				return PotionTypeAdv.HARM_TWO;
			else if(potionType == 16484)
				return PotionTypeAdv.HARM_TWO_SPL;
			else if(potionType == 8257)
				return PotionTypeAdv.WEAK_EXT;
			else if(potionType == 16449)
				return PotionTypeAdv.WEAK_EXT_SPL;
			else if(potionType == 8225)
				return PotionTypeAdv.WEAK;
			else if(potionType == 16481)
				return PotionTypeAdv.WEAK_SPL;
			else if(potionType == 8233)
				return PotionTypeAdv.WEAK;
			else if(potionType == 16425)
				return PotionTypeAdv.WEAK_SPL;
			else if(potionType == 8265)
				return PotionTypeAdv.WEAK_EXT;
			else if(potionType == 16457)
				return PotionTypeAdv.WEAK_EXT_SPL;
		}
		else if(material == Material.REDSTONE){
			if(potionType == 0)
				return PotionTypeAdv.MUNDANE_EXT;
			else if(potionType == 8195)
				return PotionTypeAdv.FIRE_RES_EXT;
			else if(potionType == 8227)
				return PotionTypeAdv.FIRE_RES_EXT;
			else if(potionType == 8194)
				return PotionTypeAdv.SWIFT_EXT;
			else if(potionType == 8229)
				return PotionTypeAdv.REV_HEALTH;
			else if(potionType == 8196)
				return PotionTypeAdv.POISON_EXT;
			else if(potionType == 8193)
				return PotionTypeAdv.REGEN_EXT;
			else if(potionType == 8201)
				return PotionTypeAdv.STRENGTH_EXT;
			else if(potionType == 8202)
				return PotionTypeAdv.SLOW_EXT;
			else if(potionType == 8234)
				return PotionTypeAdv.SLOW_EXT;
			else if(potionType == 8226)
				return PotionTypeAdv.SWIFT_EXT;
			else if(potionType == 8236)
				return PotionTypeAdv.REV_HARM;
			else if(potionType == 8228)
				return PotionTypeAdv.POISON_EXT;
			else if(potionType == 8225)
				return PotionTypeAdv.REGEN_EXT;
			else if(potionType == 8200)
				return PotionTypeAdv.WEAK_EXT;
			else if(potionType == 8233)
				return PotionTypeAdv.STRENGTH_EXT;
			else if(potionType == 16387)
				return PotionTypeAdv.FIRE_RES_EXT_SPL;
			else if(potionType == 16483)
				return PotionTypeAdv.FIRE_RES_EXT_SPL;
			else if(potionType == 16386)
				return PotionTypeAdv.SWIFT_EXT_SPL;
			else if(potionType == 16482)
				return PotionTypeAdv.SWIFT_EXT_SPL;
			else if(potionType == 16394)
				return PotionTypeAdv.SLOW_EXT_SPL;
			else if(potionType == 16426)
				return PotionTypeAdv.SLOW_EXT_SPL;
			else if(potionType == 16421)
				return PotionTypeAdv.REV_HEALTH_SPL;
			else if(potionType == 16428)
				return PotionTypeAdv.REV_HARM_SPL;
			else if(potionType == 16388)
				return PotionTypeAdv.POISON_EXT_SPL;
			else if(potionType == 16484)
				return PotionTypeAdv.POISON_EXT_SPL;
			else if(potionType == 16385)
				return PotionTypeAdv.REGEN_EXT_SPL;
			else if(potionType == 16481)
				return PotionTypeAdv.REGEN_EXT_SPL;
			else if(potionType == 16392)
				return PotionTypeAdv.WEAK_EXT_SPL;
			else if(potionType == 16424)
				return PotionTypeAdv.WEAK_EXT_SPL;
			else if(potionType == 16393)
				return PotionTypeAdv.STRENGTH_EXT_SPL;
			else if(potionType == 16425)
				return PotionTypeAdv.STRENGTH_EXT_SPL;
		}
		else if(material == Material.MAGMA_CREAM){
			if(potionType == 0)
				return PotionTypeAdv.WATER;
			else if(potionType == 16)
				return PotionTypeAdv.FIRE_RES;
		}
		else if(material == Material.BLAZE_POWDER){
			if(potionType == 0)
				return PotionTypeAdv.MUNDANE;
			else if(potionType == 16)
				return PotionTypeAdv.STRENGTH;		
		}
		else if(material == Material.SUGAR){
			if(potionType == 0)
				return PotionTypeAdv.MUNDANE;
			else if(potionType == 16)
				return PotionTypeAdv.SWIFT;
		}
		else if(material == Material.GHAST_TEAR){
			if(potionType == 0)
				return PotionTypeAdv.MUNDANE;
			else if(potionType == 16)
				return PotionTypeAdv.REGEN;
		}
		else if(material == Material.SPECKLED_MELON){
			if(potionType == 0)
				return PotionTypeAdv.MUNDANE;
			else if(potionType == 16)
				return PotionTypeAdv.HEALTH;
		}
		else if(material == Material.SPIDER_EYE){
			if(potionType == 0)
				return PotionTypeAdv.MUNDANE;
			else if(potionType == 16)
				return PotionTypeAdv.POISON;
		}
		else if(material == Material.SULPHUR){
			if(potionType == 8195)
				return PotionTypeAdv.FIRE_RES_SPL;
			else if(potionType == 8227)
				return PotionTypeAdv.REV_FIRE_RES_SPL;
			else if(potionType == 8194)
				return PotionTypeAdv.SWIFT_SPL;
			else if(potionType == 8192)
				return PotionTypeAdv.HEALTH_SPL;
			else if(potionType == 8261)
				return PotionTypeAdv.REV_HEALTH_SPL;
			else if(potionType == 8196)
				return PotionTypeAdv.POISON_SPL;
			else if(potionType == 8193)
				return PotionTypeAdv.REGEN_SPL;
			else if(potionType == 8201)
				return PotionTypeAdv.STRENGTH_SPL;
			else if(potionType == 8259)
				return PotionTypeAdv.FIRE_RES_EXT_SPL;
			else if(potionType == 8202)
				return PotionTypeAdv.SLOW_SPL;
			else if(potionType == 8234)
				return PotionTypeAdv.REV_SLOW_SPL;
			else if(potionType == 8258)
				return PotionTypeAdv.SWIFT_EXT_SPL;
			else if(potionType == 8226)
				return PotionTypeAdv.SWIFT_TWO_SPL;
			else if(potionType == 8229)
				return PotionTypeAdv.HEALTH_TWO_SPL;
			else if(potionType == 8204)
				return PotionTypeAdv.HARM_SPL;
			else if(potionType == 8268)
				return PotionTypeAdv.REV_HARM_SPL;
			else if(potionType == 8260)
				return PotionTypeAdv.POISON_EXT_SPL;
			else if(potionType == 8228)
				return PotionTypeAdv.POISON_TWO_SPL;
			else if(potionType == 8257)
				return PotionTypeAdv.REGEN_EXT_SPL;
			else if(potionType == 8225)
				return PotionTypeAdv.REGEN_TWO_SPL;
			else if(potionType == 8200)
				return PotionTypeAdv.WEAK_SPL;
			else if(potionType == 8232)
				return PotionTypeAdv.REV_WEAK_SPL;
			else if(potionType == 8233)
				return PotionTypeAdv.STRENGTH_TWO_SPL;
			else if(potionType == 8265)
				return PotionTypeAdv.STRENGTH_EXT_SPL;
			else if(potionType == 8266)
				return PotionTypeAdv.SLOW_EXT_SPL;
			else if(potionType == 8236)
				return PotionTypeAdv.HARM_TWO_SPL;
			else if(potionType == 8264)
				return PotionTypeAdv.WEAK_EXT_SPL;
		}
		
		return newPotion;
	}
}
