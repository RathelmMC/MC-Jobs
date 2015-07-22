package com.dmgkz.mcjobs.util;

import static com.dmgkz.mcjobs.util.EnchantTypeAdv.values;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public enum EnchantTypeAdv {
    ARROW_DMG_1("arrow_dmg_1", Enchantment.ARROW_DAMAGE, 1),
    ARROW_DMG_2("arrow_dmg_2", Enchantment.ARROW_DAMAGE, 2),
    ARROW_DMG_3("arrow_dmg_3", Enchantment.ARROW_DAMAGE, 3),
    ARROW_DMG_4("arrow_dmg_4", Enchantment.ARROW_DAMAGE, 4),
    ARROW_DMG_5("arrow_dmg_5", Enchantment.ARROW_DAMAGE, 5),
    ARROW_FIRE("arrow_fire", Enchantment.ARROW_FIRE, 1),
    ARROW_INF("arrow_inf", Enchantment.ARROW_INFINITE, 1),
    ARROW_KNOCK_1("arrow_knock_1", Enchantment.ARROW_KNOCKBACK, 1),
    ARROW_KNOCK_2("arrow_knock_2", Enchantment.ARROW_KNOCKBACK, 2),
    SHARP_1("sharp_1", Enchantment.DAMAGE_ALL, 1), 
    SHARP_2("sharp_2", Enchantment.DAMAGE_ALL, 2), 
    SHARP_3("sharp_3", Enchantment.DAMAGE_ALL, 3),
    SHARP_4("sharp_4", Enchantment.DAMAGE_ALL, 4),
    SHARP_5("sharp_5", Enchantment.DAMAGE_ALL, 5),
    BOA_1("boa_1", Enchantment.DAMAGE_ARTHROPODS, 1),
    BOA_2("boa_2", Enchantment.DAMAGE_ARTHROPODS, 2),
    BOA_3("boa_3", Enchantment.DAMAGE_ARTHROPODS, 3),
    BOA_4("boa_4", Enchantment.DAMAGE_ARTHROPODS, 4),
    BOA_5("boa_5", Enchantment.DAMAGE_ARTHROPODS, 5),
    DMG_UNDEAD_1("dmg_undead_1", Enchantment.DAMAGE_UNDEAD, 1),
    DMG_UNDEAD_2("dmg_undead_2", Enchantment.DAMAGE_UNDEAD, 2),
    DMG_UNDEAD_3("dmg_undead_3", Enchantment.DAMAGE_UNDEAD, 3),
    DMG_UNDEAD_4("dmg_undead_4", Enchantment.DAMAGE_UNDEAD, 4),
    DMG_UNDEAD_5("dmg_undead_5", Enchantment.DAMAGE_UNDEAD, 5),
    WATER_WALKER_1("water_walker_1", Enchantment.DEPTH_STRIDER, 1),
    WATER_WALKER_2("water_walker_2", Enchantment.DEPTH_STRIDER, 2),
    WATER_WALKER_3("water_walker_3", Enchantment.DEPTH_STRIDER, 3),
    LURE_1("lure_1", Enchantment.LURE, 1),
    LURE_2("lure_2", Enchantment.LURE, 3),
    LURE_3("lure_3", Enchantment.LURE, 2),
    LUCK_OF_THE_SEA_1("luck_of_the_sea_1", Enchantment.LUCK, 1),
    LUCK_OF_THE_SEA_2("luck_of_the_sea_2", Enchantment.LUCK, 2),
    LUCK_OF_THE_SEA_3("luck_of_the_sea_3", Enchantment.LUCK, 3),
    EFFICIENCY_1("efficiency_1", Enchantment.DIG_SPEED, 1),
    EFFICIENCY_2("efficiency_2", Enchantment.DIG_SPEED, 2),
    EFFICIENCY_3("efficiency_3", Enchantment.DIG_SPEED, 3),
    EFFICIENCY_4("efficiency_4", Enchantment.DIG_SPEED, 4),
    EFFICIENCY_5("efficiency_5", Enchantment.DIG_SPEED, 5),
    UNBREAKING_1("unbreaking_1", Enchantment.DURABILITY, 1),
    UNBREAKING_2("unbreaking_2", Enchantment.DURABILITY, 2),
    UNBREAKING_3("unbreaking_3", Enchantment.DURABILITY, 3),
    UNBREAKING_4("unbreaking_4", Enchantment.DURABILITY, 4),
    UNBREAKING_5("unbreaking_5", Enchantment.DURABILITY, 5),
    FIRE_ASPECT_1("fire_aspect_1", Enchantment.FIRE_ASPECT, 1),
    FIRE_ASPECT_2("fire_aspect_2", Enchantment.FIRE_ASPECT, 2),
    KNOCKBACK_1("knockback_1", Enchantment.KNOCKBACK, 1),
    KNOCKBACK_2("knockback_2", Enchantment.KNOCKBACK, 2),
    FORTUNE_1("fortune_1", Enchantment.LOOT_BONUS_BLOCKS, 1),
    FORTUNE_2("fortune_2", Enchantment.LOOT_BONUS_BLOCKS, 2),
    FORTUNE_3("fortune_3", Enchantment.LOOT_BONUS_BLOCKS, 3),
    LOOTING_1("looting_1", Enchantment.LOOT_BONUS_MOBS, 1),
    LOOTING_2("looting_2", Enchantment.LOOT_BONUS_MOBS, 2), 
    LOOTING_3("looting_3", Enchantment.LOOT_BONUS_MOBS, 3), 
    RESPIRATION_1("respiration_1", Enchantment.OXYGEN, 1), 
    RESPIRATION_2("respiration_2", Enchantment.OXYGEN, 2), 
    RESPIRATION_3("respiration_3", Enchantment.OXYGEN, 3), 
    PROTECTION_1("protection_1", Enchantment.PROTECTION_ENVIRONMENTAL, 1),
    PROTECTION_2("protection_2", Enchantment.PROTECTION_ENVIRONMENTAL, 2),
    PROTECTION_3("protection_3", Enchantment.PROTECTION_ENVIRONMENTAL, 3),
    PROTECTION_4("protection_4", Enchantment.PROTECTION_ENVIRONMENTAL, 4),
    BLAST_PROT_1("blast_prot_1", Enchantment.PROTECTION_EXPLOSIONS, 1), 
    BLAST_PROT_2("blast_prot_2", Enchantment.PROTECTION_EXPLOSIONS, 2), 
    BLAST_PROT_3("blast_prot_3", Enchantment.PROTECTION_EXPLOSIONS, 3), 
    BLAST_PROT_4("blast_prot_4", Enchantment.PROTECTION_EXPLOSIONS, 4), 
    FEATHER_FALL_1("feather_fall_1", Enchantment.PROTECTION_FALL, 1), 
    FEATHER_FALL_2("feather_fall_2", Enchantment.PROTECTION_FALL, 2), 
    FEATHER_FALL_3("feather_fall_3", Enchantment.PROTECTION_FALL, 3), 
    FEATHER_FALL_4("feather_fall_4", Enchantment.PROTECTION_FALL, 4), 
    FIRE_PROT_1("fire_prot_1", Enchantment.PROTECTION_FIRE, 1),
    FIRE_PROT_2("fire_prot_2", Enchantment.PROTECTION_FIRE, 2),
    FIRE_PROT_3("fire_prot_3", Enchantment.PROTECTION_FIRE, 3), 
    FIRE_PROT_4("fire_prot_4", Enchantment.PROTECTION_FIRE, 4), 
    PROJ_PROT_1("proj_prot_1", Enchantment.PROTECTION_PROJECTILE, 1), 
    PROJ_PROT_2("proj_prot_2", Enchantment.PROTECTION_PROJECTILE, 2), 
    PROJ_PROT_3("proj_prot_3", Enchantment.PROTECTION_PROJECTILE, 3), 
    PROJ_PROT_4("proj_prot_4", Enchantment.PROTECTION_PROJECTILE, 4), 
    SILK_TOUCH("silk_touch", Enchantment.SILK_TOUCH, 1),
    AQUA_AFFINITY("aqua_affinity", Enchantment.WATER_WORKER, 1);
    

    private final String _name;
    private final Enchantment _enchant;
    private final Integer _value;
    private final Map<Enchantment, Integer> _enchantMap;
    private static final Map<String, EnchantTypeAdv> _NAME_MAP;
    private static final Map<EnchantTypeAdv, Map<Enchantment, Integer>> _ENCHANT_MAP;
    private static final Map<EnchantTypeAdv, Integer> _LEVEL_MAP;

    static {
        _NAME_MAP = new HashMap();
        _ENCHANT_MAP = new HashMap();
        _LEVEL_MAP = new HashMap();
        for (EnchantTypeAdv type : values()) {
            if(type._name != null)
                _NAME_MAP.put(type._name.toLowerCase(), type);
            if((type._enchant != null) && (type._value != null)) {
                type._enchantMap.put(type._enchant, type._value);
                _ENCHANT_MAP.put(type, type._enchantMap);
            }
            if(type._value != null)
                _LEVEL_MAP.put(type, type._value);
        }
    }
  
    private EnchantTypeAdv(String name, Enchantment enchant, Integer value) {
        _name = name;
        _enchant = enchant;
        _value = value;
        _enchantMap = new HashMap();
    }
  
    public static EnchantTypeAdv getEnchantAdv(String str) {
        EnchantTypeAdv eta = null;
        if(_NAME_MAP.containsKey(str.toLowerCase()))
            eta = _NAME_MAP.get(str.toLowerCase());
        return eta;
    }
  
    public static EnchantTypeAdv getEnchantAdv(Enchantment enchant, Integer value) {
        for(Map.Entry<EnchantTypeAdv, Map<Enchantment, Integer>> e : _ENCHANT_MAP.entrySet()) {
            if(e.getValue().containsKey(enchant) && e.getValue().get(enchant) == value)
                return e.getKey();
        }
        return null;
    }
  
    public static Enchantment getEnchant(String str) {
        Enchantment enchant = null;
        if(_NAME_MAP.containsKey(str.toLowerCase()))
            enchant = (_NAME_MAP.get(str.toLowerCase()))._enchant;
        return enchant;
    }

    public static Enchantment getEnchant(EnchantTypeAdv eta) {
        return eta._enchant;
    }

    public Enchantment getEnchant() {
        return _enchant;
    }
    
    public String getName() {
        return _name;
    }

    public static Integer getLevel(String str) {
        EnchantTypeAdv eta = getEnchantAdv(str);
        if(eta != null)
            return eta._value;
        return null;
    }

    public static Integer getLevel(EnchantTypeAdv eta) {
        if(_LEVEL_MAP.containsKey(eta))
            return _LEVEL_MAP.get(eta);
        return -1;
    }
}
