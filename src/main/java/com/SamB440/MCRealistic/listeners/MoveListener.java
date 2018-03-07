package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SamB440.MCRealistic.Main;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

public class MoveListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	ArrayList<UUID> burn = Main.getInstance().getBurning();
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
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
				if(p.isOnGround() && getConfig().getBoolean("Server.Player.Trail") && r.getRegions().size() == 0) 
				{           			
					p.getWorld().playEffect(trail, Effect.FOOTSTEP, 1);
					if(randomNum == 3 && b.getType().equals(Material.GRASS) && getConfig().getBoolean("Server.Player.Path")) 
					{
						b.setType(Material.DIRT);
					} else if(randomNum == 5 && b.getType().equals(Material.GRASS) && getConfig().getBoolean("Server.Player.Path")) {
						b.setType(Material.GRASS_PATH);
					} else if(randomNum == 1 && b.getType().equals(Material.SAND) && getConfig().getBoolean("Server.Player.Path")) {
						b.setType(Material.SANDSTONE);
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
				} 
				else if (burn.contains(p.getUniqueId())) 
				{
					p.setFireTicks(0);
					burn.remove(p.getUniqueId());
				}
			}
		}
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
