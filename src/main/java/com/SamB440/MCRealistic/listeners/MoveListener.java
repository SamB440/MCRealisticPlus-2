/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SamB440.MCRealistic.Main;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

public class MoveListener implements Listener {
	
	Main plugin;
	ArrayList<World> worlds;
	ArrayList<UUID> burn;
	
	public MoveListener(Main plugin)
	{
		this.plugin = plugin;
		this.worlds = plugin.getWorlds();
		this.burn = plugin.getBurning();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerMove(PlayerMoveEvent pme) {
		Player p = pme.getPlayer();
		if(worlds.contains(p.getWorld())) {
			Random rand = new Random();
			int max = 20;
			int min = 1;
			int randomNum = rand.nextInt((max - min) + 1) + min;
			Location blockunder = p.getLocation();
			Location trail = p.getLocation();
			trail.setY(trail.getY() + 0.05);
			blockunder.setY(blockunder.getY() - 1);
			Block b = blockunder.getBlock();
			Location blockabove = p.getLocation();
			blockabove.setY(blockabove.getY() + 10);
			if(!(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
			{
				ApplicableRegionSet r = WGBukkit.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
				if(p.isOnGround() && getConfig().getBoolean("Server.Player.Trail.Enabled") && r.getRegions().size() == 0) 
				{           			
					p.getWorld().playEffect(trail, Effect.FOOTSTEP, 1);
					if(randomNum == 3 && b.getType().equals(Material.GRASS) && getConfig().getBoolean("Server.Player.Path")) 
					{
						List<String> g = getConfig().getStringList("Server.Player.Trail.Grass_Blocks");
						Material m = Material.valueOf(g.get(new Random().nextInt(g.size())));
						b.setType(m);
					} else if(randomNum == 1 && b.getType().equals(Material.SAND) && getConfig().getBoolean("Server.Player.Path")) {
						List<String> s = getConfig().getStringList("Server.Player.Trail.Sand_Blocks");
						Material m = Material.valueOf(s.get(new Random().nextInt(s.size())));
						b.setType(m);
					}
				}
				
				if ((double)p.getWalkSpeed() > getConfig().getDouble("Players.DefaultWalkSpeed." + p.getUniqueId())) 
				{
					p.setWalkSpeed((float)getConfig().getDouble("Players.DefaultWalkSpeed." + p.getUniqueId()));
				}
				
				if (p.getLocation().getBlock().getType() == Material.TORCH) 
				{
					if (!burn.contains(p.getUniqueId())) {
						burn.add(p.getUniqueId());
					}
				} else if (burn.contains(p.getUniqueId())) {
					p.setFireTicks(0);
					burn.remove(p.getUniqueId());
				}
			}
		}
	}
	
	private FileConfiguration getConfig()
	{
		return plugin.getConfig();
	}
}
