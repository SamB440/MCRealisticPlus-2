package com.SamB440.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.SamB440.MCRealistic.Main;
import com.SamB440.MCRealistic.utils.TitleManager;

/*
 * COMPLETED
 */
public class InteractListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	
    static ItemStack bandage = new ItemStack(Material.PAPER);
	
	@EventHandler
	public void onInteract(PlayerInteractEvent pie)
	{
		Player p = pie.getPlayer();
		if(worlds.contains(p.getWorld()))
		{
			if ((pie.getAction().equals(Action.RIGHT_CLICK_AIR) || pie.getAction().equals(Action.RIGHT_CLICK_BLOCK))) 
			{
				/*
				 * Check for broken bones
				 */
				if(p.getInventory().getItemInMainHand().equals(bandage) && getConfig().getBoolean("Players.BoneBroke." + p.getUniqueId()))
				{
		            getConfig().set("Players.BoneBroke." + p.getUniqueId(), false);
		            p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Used_Bandage")));
		            p.getInventory().removeItem(new ItemStack(p.getInventory().getItemInMainHand()));
		            p.updateInventory();
				} else if(pie.getClickedBlock().getType().equals(Material.BED) || pie.getClickedBlock().getType().equals(Material.BED_BLOCK)) {
					/*
					 * Check for fatigue
					 */
					if(getConfig().getBoolean("Server.Player.Allow Fatigue") && getConfig().getInt("Players.Fatigue." + p.getUniqueId()) != 0)
					{
			            getConfig().set("Players.Fatigue." + p.getUniqueId(), 0);
			            TitleManager.sendActionBar(p, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Not Tired")));
			            p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Not Tired")));
					}
				}
			}
		}
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
	public static ItemStack getBandage()
	{
		return bandage;
	}
}
