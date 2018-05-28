package com.SamB440.MCRealistic.tasks;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SamB440.MCRealistic.Main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class StaminaTask implements Runnable {
	
	Main plugin;
	HashMap<Player, Integer> cancel = new HashMap<Player, Integer>();
	HashMap<Horse, Location> move = new HashMap<Horse, Location>();
	
	public StaminaTask(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		if(Bukkit.getOnlinePlayers().size() > 0)
    	{
    		for(Player pl : Bukkit.getOnlinePlayers())
    		{
    			if(!pl.isInsideVehicle())
    			{
    				if(cancel.containsKey(pl))
    				{
    					Bukkit.getScheduler().cancelTask(cancel.get(pl));
    					cancel.remove(pl);
    				}
    				
	    			if(pl.isSprinting() && pl.getFoodLevel() > 0)
	    			{
	    				pl.setFoodLevel(pl.getFoodLevel() - 1);
	    			} else if(!pl.isSprinting() && pl.getFoodLevel() < 20) {
	    				pl.setFoodLevel(pl.getFoodLevel() + 1);
	    			}
    			} else if(pl.getVehicle() instanceof Horse) {
    				Horse horse = (Horse) pl.getVehicle();
    				if(move.containsKey(horse))
    				{
    					if(move.get(horse).distance(horse.getLocation()) >= 1)
    					{
	    					move.remove(horse);
	    					move.put(horse, horse.getLocation());
		    				if(pl.getFoodLevel() > 6)
		    				{
			    				if(pl.getFoodLevel() > 10) pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&7[&aHorse is galloping&7]")));
			    				else pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&7[&6Horse is trotting&7]")));
			    				
			    				pl.setFoodLevel(pl.getFoodLevel() - 1);
			    				
			    				for(PotionEffect p : horse.getActivePotionEffects())
			    				{
			    					if(p.getType() == PotionEffectType.SLOW) horse.removePotionEffect(PotionEffectType.SLOW);
			    				}
			    				
			    				if(pl.getFoodLevel() > 10) horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 2, false, false));
			    				
			    				if(cancel.containsKey(pl)) {
			    					Bukkit.getScheduler().cancelTask(cancel.get(pl));
			    					cancel.remove(pl);
			    				}
		    				} else {
		    					pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&7[&cYour horse is tired&7]")));
		    					
		    					if(!horse.hasPotionEffect(PotionEffectType.SLOW)) 
		    					{
		    						horse.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3, false, false));
		    						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> pl.setFoodLevel(pl.getFoodLevel() + 1), 100);
		    					}
		    					
		    					if(!cancel.containsKey(pl)) {
		    						int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
		    							pl.getWorld().playSound(pl.getLocation(), Sound.ENTITY_HORSE_BREATHE, 1, 1);
		    							pl.getWorld().spawnParticle(Particle.CLOUD, horse.getEyeLocation(), 7, 1, 1, 1, 0);
		    						}, 0, 40);
		    						cancel.put(pl, i);
		    					}
    						}
    					}
    				} else move.put(horse, horse.getLocation());
    			}
    		}
    	}
	}
}
