package com.SamB440.MCRealistic.placeholders;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.SamB440.MCRealistic.Main;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion {

	Main plugin;
	
	public Placeholders(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public String onPlaceholderRequest(Player player, String arg1) 
	{
		if(player == null) return "";
		
		switch(arg1.toLowerCase())
		{
			case "thirst":
				return String.valueOf(getConfig().getInt("Players.Thirst." + player.getUniqueId()));
			case "fatigue":
				return String.valueOf(getConfig().getInt("Players.Fatigue." + player.getUniqueId()));
		}
		
		return null;
	}
	
	private FileConfiguration getConfig()
	{
		return plugin.getConfig();
	}

	@Override
	public String getAuthor() {
		return "SamB440";
	}

	@Override
	public String getIdentifier() {
		return "mcrealistic";
	}

	@Override
	public String getPlugin() {
		return null;
	}

	@Override
	public String getVersion() {
		return "2.0.4";
	}
}
