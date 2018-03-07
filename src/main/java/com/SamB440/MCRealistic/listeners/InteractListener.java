/*MIT License

Copyright (c) 2018 Sam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/
package com.SamB440.MCRealistic.listeners;

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
				} else if(pie.getAction().equals(Action.RIGHT_CLICK_BLOCK) && pie.getClickedBlock() != null && !pie.getClickedBlock().getType().equals(Material.AIR)) {
					if(pie.getClickedBlock().getType().equals(Material.BED) || pie.getClickedBlock().getType().equals(Material.BED_BLOCK))
					{
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
