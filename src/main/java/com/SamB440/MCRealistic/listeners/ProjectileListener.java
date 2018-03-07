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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.SamB440.MCRealistic.Main;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

/*
 * DONE
 */
public class ProjectileListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent phe) 
    {
    	Projectile p = phe.getEntity();
		ApplicableRegionSet r = WGBukkit.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
		if(worlds.contains(p.getWorld()) && r.getRegions().size() == 0) 
		{
			if (getConfig().getBoolean("Server.Player.Allow Enchanted Arrow") && p.getType() == EntityType.ARROW && p.getFireTicks() > 0) 
			{
				if(p.getWorld().getBlockAt(p.getLocation()).getType() == null || p.getWorld().getBlockAt(p.getLocation()).getType().equals(Material.AIR)) p.getWorld().getBlockAt(p.getLocation()).setType(Material.FIRE);
			}
    	}
    }
    private FileConfiguration getConfig()
    {
    	return Main.getInstance().getConfig();
    }
}
