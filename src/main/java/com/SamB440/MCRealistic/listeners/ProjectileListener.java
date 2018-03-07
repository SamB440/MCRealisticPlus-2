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
		System.out.print(r.getRegions().size());
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
