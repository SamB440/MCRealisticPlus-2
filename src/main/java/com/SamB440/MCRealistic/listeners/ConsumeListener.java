package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SamB440.MCRealistic.Main;
import com.SamB440.MCRealistic.utils.TitleManager;

public class ConsumeListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	ArrayList<Player> hasdisease = Main.getInstance().getDiseases();
	ArrayList<Player> hascold = Main.getInstance().getColds();
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent pice) {
		Player p = pice.getPlayer();
		if(worlds.contains(p.getWorld())) 
		{
	        if(!(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
	        {
	        	if (pice.getItem() != null && pice.getItem().getType() != null && p != null && pice.getItem().getType().equals(Material.POTION)) 
	        	{
	        		getConfig().set("Players.Thirst." + p.getUniqueId(), 0);
	        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Not Thirsty")));
	        		TitleManager.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Not Thirsty")));
	        		int CurrentFatigue = this.getConfig().getInt("Players.Fatigue." + p.getUniqueId());
	        		getConfig().set("Players.Fatigue." + p.getUniqueId(), (CurrentFatigue -= 2));
	        		if(pice.getItem().hasItemMeta()) 
	        		{
	        			if(pice.getItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Medicine") && pice.getItem().getItemMeta().getLore().equals(Arrays.asList(ChatColor.WHITE + "Drink to help fight your cold/disease!"))) 
	        			{
	        				if(hascold.contains(p)) 
	        				{
	        					TitleManager.sendTitle(p, "", ChatColor.GREEN + "The cold begins to subside...", 200);
	        					hascold.remove(p);
	        				}
	        				else if(hasdisease.contains(p)) 
	        				{
	        					hasdisease.remove(p);
	        					TitleManager.sendTitle(p, "", ChatColor.GREEN + "The disease begins to subside...", 200);
	        				}
	        			}
	        		}
	            	if(pice.getItem().getType().equals(Material.MILK_BUCKET)) 
	            	{
	            		if(pice.getItem().hasItemMeta() && pice.getItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Chocolate Milk") && pice.getItem().getItemMeta().getLore().equals(Arrays.asList(ChatColor.WHITE + "Drink to gain Speed II."))) 
	            		{
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
	            		}
	            	}
	        	}
	        }
		}
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
