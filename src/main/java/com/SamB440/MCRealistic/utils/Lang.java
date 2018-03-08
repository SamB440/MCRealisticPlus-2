package com.SamB440.MCRealistic.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Clip
 */
public enum Lang {
    /**
     * No permission
     */
    NO_PERMISSION("no_permission", "&cYou do not have the required permission {0}."),
	NOT_TIRED("not_tired", "&aI don't feel tired anymore.."),
	TOO_TIRED("too_tired", "&cI'm too tired to do that."),
	TIRED("tired", "&cI am tired..."),
	VERY_TIRED("very_tired", "&cI am very tired... I should get some sleep."),
	NO_HAND_CHOP("no_hand_chop", "&cYou can't chop down trees with your hands!"),
	NOT_THIRSTY("not_thirsty", "&aI'm not thirsty anymore!"),
	LITTLE_THIRSTY("little_thirsty", "I am a little thirsty..."),
	GETTING_THIRSTY("getting_thirsty", "&cI am getting thirsty..."),
	REALLY_THIRSTY("really_thirsty", "&c&l&nI am really thirsty.. I should drink some water."),
	COSY("cosy", "&2I feel cosy..."),
	COLD("cold", "&c&nI am cold, I should wear some clothes (Armour)."),
	HURT("hurt", "&c&lI am hurt!"),
	HUNGRY("hungry", "&c&lI am hungry! I should really eat something..."),
	SHOULD_SLEEP("should_sleep", "&cI should sleep in that bed..."),
	USED_BANDAGE("used_bandage", "&aYou used a bandage, and your legs healed!"),
	LEGS_HEALED("legs_healed", "&aYour legs healed!"),
	BROKEN_BONES("broken_bones", "&cYou fell from a high place and broke your bones!");

    private String path, def;
    private static FileConfiguration LANG;

    Lang(final String path, final String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(final FileConfiguration config) {
        LANG = config;
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }
 
    public String getConfigValue(final String[] args) {
        String value = ChatColor.translateAlternateColorCodes('&',
                LANG.getString(this.path, this.def));

        if (args == null)
            return value;
        else {
            if (args.length == 0)
                return value;

            for (int i = 0; i < args.length; i++) {
                value = value.replace("{" + i + "}", args[i]);
            }
        }

        return value;
    }
}
 
