package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.SamB440.MCRealistic.Main;

/*
 * DONE
 */
public class RespawnListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	
	static ArrayList<String> lastnames = new ArrayList<String>();
	static ArrayList<String> firstnames = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent pre) 
	{
        Player p = pre.getPlayer();
		if(worlds.contains(p.getWorld())) 
		{
	        getConfig().set("Players.Thirst." + p.getUniqueId(), 0);
	        if (getConfig().getBoolean("Server.Player.Spawn with items")) 
	        {
	            p.getInventory().addItem(new ItemStack(new ItemStack(Material.getMaterial((int)374))));
	            p.getInventory().addItem(new ItemStack(new ItemStack(Material.WOOD_AXE)));
	        }
	        
	        /*
	         * Get names
	         */
	        String[] FirstArray = firstnames.toArray(new String[20]);
	        Random random1 = new Random();
	        int randomFirstId = random1.nextInt(FirstArray.length);
	        String[] LastArray = lastnames.toArray(new String[20]);
	        Random random2 = new Random();
	        int randomLastId = random2.nextInt(LastArray.length);
	        Random random3 = new Random();
	        int randomAge = random3.nextInt(50);
	        
	        if (getConfig().getBoolean("Server.Messages.Respawn")) 
	        {
	        	p.sendMessage(ChatColor.GOLD + "Type /mystats to see your stats!");
	        	p.sendMessage(ChatColor.GOLD + "Type /fatigue to see your fatigue!");
	        }
	        getConfig().set("Players.RealName." + p.getUniqueId(), String.valueOf(String.valueOf(FirstArray[randomFirstId])) + " " + LastArray[randomLastId]);
	        getConfig().set("Players.RealAge." + p.getUniqueId(), randomAge + 15);
		}
    }
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
	public static void addNames(ArrayList<String> fn, ArrayList<String> ln)
	{
		firstnames = fn;
		lastnames = ln;
	}
}
