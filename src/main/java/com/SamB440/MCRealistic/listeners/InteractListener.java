/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;

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
import com.SamB440.MCRealistic.API.Message;
import com.SamB440.MCRealistic.API.data.MessageType;
import com.SamB440.MCRealistic.utils.Lang;

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
	        		Message m = new Message(p, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.USED_BANDAGE, null);
	        		m.send();
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
			        		Message m = new Message(p, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.NOT_TIRED, null);
			        		m.send();
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
