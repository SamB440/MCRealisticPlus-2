package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.SamB440.MCRealistic.Main;
import com.SamB440.MCRealistic.utils.TitleManager;

public class BlockListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent bpe) 
	{
		Player p = bpe.getPlayer();
		Block block = bpe.getBlock();
		if(worlds.contains(p.getWorld())) 
		{
			if (getConfig().getBoolean("Server.Building.Realistic_Building") && p.getGameMode().equals(GameMode.SURVIVAL)) 
			{
	        	if (p.getInventory().getItemInMainHand().hasItemMeta()) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.TORCH)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.REDSTONE_TORCH_OFF)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.REDSTONE_TORCH_ON)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.SIGN)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.SIGN_POST)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.VINE)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.WOOD_BUTTON)) {
	        		return;
	        	} else if (bpe.getBlock().getType().equals(Material.STONE_BUTTON)) {
	        		return;
	        	} else if(bpe.getBlock().getType().equals(Material.FENCE)) {
	        		return;
	        	} else if(bpe.getBlock().getType().equals(Material.LOG) || bpe.getBlock().getType().equals(Material.LOG_2)) {
	        		return;
	        	} else if (bpe.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) {
	        		return;
	        	}
	        	if(!block.getType().equals(Material.SIGN)) 
	        	{
	    			Location loc = block.getLocation();
	    			loc.setX(loc.getX() + 0.5);
	    			loc.setZ(loc.getZ() + 0.5);
	    			loc.getWorld().spawnFallingBlock(loc, block.getType(), block.getData());
	    			block.setType(Material.AIR);
	        	}
			}
        	if (getConfig().getInt("Players.Fatigue." + bpe.getPlayer().getUniqueId()) >= 250 && block.getType() != Material.BED && block.getType() != Material.BED_BLOCK) 
        	{
        		TitleManager.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Too Tired")));
        		bpe.setCancelled(true);
        	}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent bbe) 
	{
		Player p = bbe.getPlayer();
        Block block = bbe.getBlock();
		if(worlds.contains(p.getWorld())) 
		{
	        int CurrentFatigue = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
	        getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue));
	        if(!(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
	        {        	
		        if (getConfig().getInt("Players.Fatigue." + p.getUniqueId()) >= 250) 
		        {
		            TitleManager.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Too Tired")));
		            bbe.setCancelled(true);
		        }
		        if (block.getType().equals(Material.LOG) || block.getType().equals(Material.LOG_2)) 
		        {
		            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) && !getConfig().getBoolean("Server.Player.Allow Chop Down Trees With Hands")) 
		            {
		                p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.No Hand Chop")));
		                bbe.setCancelled(true);
		            }
		            if ((p.getInventory().getItemInMainHand().getType().equals(Material.WOOD_AXE) || p.getInventory().getItemInMainHand().getType().equals(Material.IRON_AXE) || p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE) || p.getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE)) && getConfig().getBoolean("Server.Player.Trees have random number of drops")) 
		            {
		                int CurrentFatigue2;
		                Random random5 = new Random();
		                int randomTreeChop = random5.nextInt(2);
		                if (randomTreeChop == 0) 
		                {
		                    p.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
		                    CurrentFatigue2 = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
		                    getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue2));
		                }
		                if (randomTreeChop == 1) 
		                {
		                    bbe.setCancelled(true);
		                    block.setType(Material.AIR);
		                    CurrentFatigue2 = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
		                    getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue2));
		                }
		                if (randomTreeChop == 2) 
		                {
		                    CurrentFatigue2 = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
		                    getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue2));
		                    p.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
		                    p.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
		                }
		            }
		        }
		        if (getConfig().getInt("Players.Fatigue." + p.getUniqueId()) >= 250) 
		        {
		        	TitleManager.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Too Tired")));
		            bbe.setCancelled(true);
		        }
	        }
		}
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
