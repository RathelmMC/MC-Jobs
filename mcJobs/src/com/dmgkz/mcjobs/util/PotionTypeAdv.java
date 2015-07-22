package com.dmgkz.mcjobs.util;

import static com.dmgkz.mcjobs.util.PotionTypeAdv.values;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;

public enum PotionTypeAdv {
    WATER("water", 0, 0),
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
    REV_WEAK_SPL("rev_weak_spl", 16424, 4),
    WAT_BRE("wat_bre", 8205, 2),
    WAT_BRE_TWO("wat_bre_two", 8237, 3),
    WAT_BRE_EXT("wat_bre_ext", 8269, 3),
    WAT_BRE_SPL("wat_bre_spl", 16397, 4),
    WAT_BRE_TWO_SPL("wat_bre_two_spl", 16429, 5),
    WAT_BRE_EXT_SPL("wat_bre_ext_spl", 16461, 5),
    LEAPING("leaping", 8203, 2),
    LEAPING_TWO("leaping_two", 8235, 3),
    LEAPING_EXT("leaping_ext", 8267, 3),
    LEAPING_SPL("leaping_spl", 16395, 4),
    LEAPING_TWO_SPL("leaping_two_spl", 16427, 5),
    LEAPING_EXT_SPL("leaping_ext_spl", 16459, 5);
  
    private final Short _typeID;
    private final String _name;
    private final Short _tier;
    private static final Map<String, PotionTypeAdv> _NAME_MAP;
    private static final Map<Short, PotionTypeAdv> _ID_MAP;
    private static final Map<PotionTypeAdv, Short> _TIER;
  
    static {
        _NAME_MAP = new HashMap();
        _ID_MAP = new HashMap();
        _TIER = new HashMap();
        for (PotionTypeAdv type : values()) {
            if(type._name != null)
                _NAME_MAP.put(type._name.toLowerCase(), type);
            if(type._typeID >= 0)
                _ID_MAP.put(type._typeID, type);
            if(type._tier >= 0)
                _TIER.put(type, type._tier);
        }
    }
  
    private PotionTypeAdv(String name, Integer value, Integer tier) {
        _typeID = value.shortValue();
        _name = name;
        _tier = tier.shortValue();
    }
  
    public Short getPotionValue() {
        return _typeID;
    }
    
    public String getName() {
        return _name;
    }
  
    public static PotionTypeAdv getPotion(String str) {
        PotionTypeAdv type = null;
        if(_NAME_MAP.containsKey(str.toLowerCase()))
            type = (PotionTypeAdv)_NAME_MAP.get(str.toLowerCase());
        return type;
    }
  
    public static PotionTypeAdv getPotion(Short value) {
        PotionTypeAdv type = null;
        if(_ID_MAP.containsKey(value))
            type = _ID_MAP.get(value);
        return type;
    }
  
    public Short getTier() {
        return _TIER.get(this);
    }
  
    public static Boolean isHigherTier(PotionTypeAdv potOne, PotionTypeAdv potTwo) {
        if((potOne == null) || (potTwo == null))
            return false;
        return (potOne.getTier() > potTwo.getTier());
    }
  
    public static PotionTypeAdv getMakeResults(Short potionType, Material material) {
        PotionTypeAdv newPotion = null;
        if(material == Material.NETHER_STALK) {
            if(potionType == 0)
                return AWKWARD;
        } else if(material == Material.RAW_FISH) {
            if(potionType == 16)
                return WAT_BRE;
        } else if(material == Material.RABBIT_FOOT) {
            if(potionType == 16)
                return LEAPING;
        } else if(material == Material.GLOWSTONE_DUST) {
            if(potionType == 0)
                return THICK;
            if(potionType == 8259)
                return REV_FIRE_RES;
            if(potionType == 8194)
                return SWIFT_TWO;
            if(potionType == 8197)
                return HEALTH_TWO;
            if(potionType == 8261)
                return HEALTH_TWO;
            if(potionType == 8196)
                return POISON_TWO;
            if(potionType == 8193)
                return REGEN_TWO;
            if(potionType == 8201)
                return STRENGTH_TWO;
            if(potionType == 8266)
                return REV_SLOW;
            if(potionType == 8258)
                return SWIFT_TWO;
            if(potionType == 8203)
                return LEAPING_TWO;
            if(potionType == 8204)
                return HARM_TWO;
            if(potionType == 8268)
                return HARM_TWO;
            if(potionType == 8260)
                return POISON_TWO;
            if(potionType == 8257)
                return REGEN_TWO;
            if(potionType == 8264)
                return REV_WEAK;
            if(potionType == 8265)
                return STRENGTH_TWO;
            if(potionType == 16483)
                return REV_FIRE_RES_SPL;
            if(potionType == 16458)
                return REV_SLOW_SPL;
            if(potionType == 16386)
                return SWIFT_TWO_SPL;
            if(potionType == 16450)
                return SWIFT_TWO_SPL;
            if(potionType == 16389)
                return HEALTH_TWO_SPL;
            if(potionType == 16453)
                return HEALTH_TWO_SPL;
            if(potionType == 16396)
                return HARM_TWO_SPL;
            if(potionType == 16460)
                return HARM_TWO_SPL;
            if(potionType == 16388)
                return POISON_TWO_SPL;
            if(potionType == 16452)
                return POISON_TWO_SPL;
            if(potionType == 16385)
                return REGEN_TWO_SPL;
            if(potionType == 16393)
                return STRENGTH_TWO_SPL;
            if(potionType == 16449)
                return REGEN_TWO_SPL;
            if(potionType == 16456)
                return REV_WEAK_SPL;
            if(potionType == 16457)
                return STRENGTH_TWO_SPL;
        } else if(material == Material.FERMENTED_SPIDER_EYE) {
            if(potionType == 0)
                return WEAK;
            if(potionType == 8192)
                return WEAK;
            if(potionType == 16)
                return WEAK;
            if(potionType == 32)
                return WEAK;
            if(potionType == 64)
                return WEAK_EXT;
            if(potionType == 8195)
                return SLOW;
            if(potionType == 8227)
                return SLOW;
            if(potionType == 16387)
                return SLOW_SPL;
            if(potionType == 16419)
                return SLOW_SPL;
            if(potionType == 8194)
                return SLOW;
            if(potionType == 16386)
                return SLOW_SPL;
            if(potionType == 8197)
                return HARM;
            if(potionType == 8261)
                return HARM;
            if(potionType == 16389)
                return HARM_SPL;
            if(potionType == 16453)
                return HARM_SPL;
            if(potionType == 8196)
                return HARM;
            if(potionType == 16388)
                return HARM_SPL;
            if(potionType == 8193)
                return WEAK;
            if(potionType == 16385)
                return WEAK_SPL;
            if(potionType == 8201)
                return WEAK;
            if(potionType == 16393)
                return WEAK_SPL;
            if(potionType == 8226)
                return SLOW;
            if(potionType == 16482)
                return SLOW_SPL;
            if(potionType == 8259)
                return SLOW_EXT;
            if(potionType == 16483)
                return SLOW_EXT_SPL;
            if(potionType == 8258)
                return SLOW_EXT;
            if(potionType == 16450)
                return SLOW_EXT_SPL;
            if(potionType == 8229)
                return HARM_TWO;
            if(potionType == 16421)
                return HARM_TWO_SPL;
            if(potionType == 8260)
                return HARM;
            if(potionType == 16452)
                return HARM_SPL;
            if(potionType == 8228)
                return HARM_TWO;
            if(potionType == 16484)
                return HARM_TWO_SPL;
            if(potionType == 8257)
                return WEAK_EXT;
            if(potionType == 16449)
                 return WEAK_EXT_SPL;
            if(potionType == 8225)
                return WEAK;
            if(potionType == 16481)
                return WEAK_SPL;
            if(potionType == 8233)
                return WEAK;
            if(potionType == 16425)
                return WEAK_SPL;
            if(potionType == 8265)
                return WEAK_EXT;
            if(potionType == 16457)
                return WEAK_EXT_SPL;
        } else if(material == Material.REDSTONE) {
            if(potionType == 0)
                return MUNDANE_EXT;
            if(potionType == 8195)
                return FIRE_RES_EXT;
            if(potionType == 8227)
                return FIRE_RES_EXT;
            if(potionType == 8194)
                return SWIFT_EXT;
            if(potionType == 8229)
                return REV_HEALTH;
            if(potionType == 8196)
                return POISON_EXT;
            if(potionType == 8193)
                return REGEN_EXT;
            if(potionType == 8201)
                return STRENGTH_EXT;
            if(potionType == 8202)
                return SLOW_EXT;
            if(potionType == 8203)
                return LEAPING_EXT;
            if(potionType == 8205)
                return WAT_BRE_EXT;
            if(potionType == 8234)
                return SLOW_EXT;
            if(potionType == 8226)
                return SWIFT_EXT;
            if(potionType == 8236)
                return REV_HARM;
            if(potionType == 8228)
                return POISON_EXT;
            if(potionType == 8225)
                return REGEN_EXT;
            if(potionType == 8200)
                return WEAK_EXT;
            if(potionType == 8233)
                return STRENGTH_EXT;
            if(potionType == 16387)
                return FIRE_RES_EXT_SPL;
            if(potionType == 16483)
                return FIRE_RES_EXT_SPL;
            if(potionType == 16386)
                return SWIFT_EXT_SPL;
            if(potionType == 16482)
                return SWIFT_EXT_SPL;
            if(potionType == 16394)
                return SLOW_EXT_SPL;
            if(potionType == 16426)
                return SLOW_EXT_SPL;
            if(potionType == 16421)
                return REV_HEALTH_SPL;
            if(potionType == 16428)
                return REV_HARM_SPL;
            if(potionType == 16388)
                return POISON_EXT_SPL;
            if(potionType == 16484)
                return POISON_EXT_SPL;
            if(potionType == 16385)
                return REGEN_EXT_SPL;
            if(potionType == 16481)
                return REGEN_EXT_SPL;
            if(potionType == 16392)
                return WEAK_EXT_SPL;
            if(potionType == 16424)
                return WEAK_EXT_SPL;
            if(potionType == 16393)
                return STRENGTH_EXT_SPL;
            if(potionType == 16425)
                return STRENGTH_EXT_SPL;
        } else if(material == Material.MAGMA_CREAM) {
            if(potionType == 0)
                return WATER;
            if(potionType == 16)
                return FIRE_RES;
        } else if(material == Material.BLAZE_POWDER) {
            if(potionType == 0)
                return MUNDANE;
            if(potionType == 16)
                return STRENGTH;
        } else if(material == Material.SUGAR) {
            if(potionType == 0)
                return MUNDANE;
            if(potionType == 16)
                return SWIFT;
        } else if(material == Material.GHAST_TEAR) {
            if(potionType == 0)
                return MUNDANE;
            if(potionType == 16)
                return REGEN;
        } else if(material == Material.SPECKLED_MELON) {
            if(potionType == 0)
                return MUNDANE;
            if(potionType == 16)
                return HEALTH;
        } else if(material == Material.SPIDER_EYE) {
            if(potionType == 0)
                return MUNDANE;
            if(potionType == 16)
                return POISON;
        } else if(material == Material.SULPHUR) {
            if(potionType == 8195)
                return FIRE_RES_SPL;
            if(potionType == 8227)
                return REV_FIRE_RES_SPL;
            if(potionType == 8194)
                return SWIFT_SPL;
            if(potionType == 8192)
                return HEALTH_SPL;
            if(potionType == 8261)
                return REV_HEALTH_SPL;
            if(potionType == 8196)
                return POISON_SPL;
            if(potionType == 8193)
                return REGEN_SPL;
            if(potionType == 8201)
                return STRENGTH_SPL;
            if(potionType == 8259)
                return FIRE_RES_EXT_SPL;
            if(potionType == 8202)
                return SLOW_SPL;
            if(potionType == 8234)
                return REV_SLOW_SPL;
            if(potionType == 8258)
                return SWIFT_EXT_SPL;
            if(potionType == 8226)
                return SWIFT_TWO_SPL;
            if(potionType == 8229)
                return HEALTH_TWO_SPL;
            if(potionType == 8204)
                return HARM_SPL;
            if(potionType == 8268)
                return REV_HARM_SPL;
            if(potionType == 8260)
                return POISON_EXT_SPL;
            if(potionType == 8228)
                return POISON_TWO_SPL;
            if(potionType == 8257)
                return REGEN_EXT_SPL;
            if(potionType == 8225)
                return REGEN_TWO_SPL;
            if(potionType == 8200)
                return WEAK_SPL;
            if(potionType == 8232)
                return REV_WEAK_SPL;
            if(potionType == 8233)
                return STRENGTH_TWO_SPL;
            if(potionType == 8265)
                return STRENGTH_EXT_SPL;
            if(potionType == 8266)
                return SLOW_EXT_SPL;
            if(potionType == 8236)
                return HARM_TWO_SPL;
            if(potionType == 8267)
                return WEAK_EXT_SPL;
            if(potionType == 8203)
                return LEAPING_SPL;
            if(potionType == 8235)
                return LEAPING_TWO_SPL;
            if(potionType == 8264)
                return LEAPING_EXT_SPL;
            if(potionType == 8205)
                return WAT_BRE_SPL;
            if(potionType == 8269)
                return WAT_BRE_EXT_SPL;
        }
        return newPotion;
    }
}
