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
				p.getWorld().getBlockAt(p.getLocation()).setType(Material.FIRE);
			}
    	}
    }
    private FileConfiguration getConfig()
    {
    	return Main.getInstance().getConfig();
    }
}
