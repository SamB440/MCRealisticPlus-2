/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.SamB440.MCRealistic.Main;
import com.SamB440.MCRealistic.utils.Lang;

public class EntityListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	
    @EventHandler
    public void onPlayerFall(EntityDamageEvent ede) {
        if (ede.getEntity() instanceof Player) 
        {
            Player player = (Player)ede.getEntity();
    		if(worlds.contains(player.getWorld()) && Main.getInstance().getConfig().getBoolean("Server.Player.Broken_Bones.Enabled")) 
    		{
    			if(!(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))) 
    			{
    				if(ede.getCause() == EntityDamageEvent.DamageCause.FALL && player.getFallDistance() >= 7.0f) 
    				{
    					getConfig().set("Players.BoneBroke." + player.getUniqueId(), true);
    					player.sendMessage(Lang.BROKEN_BONES.getConfigValue(null));
    					getConfig().set("Players.DefaultWalkSpeed." + player.getPlayer().getUniqueId(), 0.13);
    					float WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + player.getUniqueId());
    					float WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + player.getUniqueId());
    					float WeightCombined = WeightLeggings + WeightChestPlate;
    					player.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + player.getUniqueId()) - (double)(WeightCombined * 0.01f)));
    					Float floatObj = Float.valueOf(player.getFallDistance() * 80.0f);
    					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable()
    					{
    						@Override
    						public void run() 
    						{
    							if (getConfig().getBoolean("Players.BoneBroke." + player.getUniqueId())) 
    							{
    								player.sendMessage(Lang.LEGS_HEALED.getConfigValue(null));
    								getConfig().set("Players.DefaultWalkSpeed." + player.getPlayer().getUniqueId(), 0.2);
    								float WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + player.getUniqueId());
    								float WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + player.getUniqueId());
    								float WeightCombined = WeightLeggings + WeightChestPlate;
    								player.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + player.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
    								getConfig().set("Players.BoneBroke." + player.getUniqueId(), false);
    							}
    						}
    					}, floatObj.longValue());
    				}
    			}
    		}
        }
    }
    private FileConfiguration getConfig()
    {
    	return Main.getInstance().getConfig();
    }
}
