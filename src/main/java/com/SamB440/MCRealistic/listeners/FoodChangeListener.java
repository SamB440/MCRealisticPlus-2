/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.SamB440.MCRealistic.Main;
import com.SamB440.MCRealistic.API.Message;
import com.SamB440.MCRealistic.API.data.MessageType;
import com.SamB440.MCRealistic.utils.Lang;

public class FoodChangeListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent flce) {
        Player p = (Player) flce.getEntity();
		if(worlds.contains(p.getWorld())) 
		{
			if(!(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
			{
				if (getConfig().getBoolean("Server.Player.DisplayHungerMessage") && p.getFoodLevel() < 6) 
				{
	        		Message m = new Message(p, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.HUNGRY, null);
	        		m.send();
		    		int CurrentFatigue = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
		    		getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue));
        		}
        	}
        }
    }
    private FileConfiguration getConfig()
    {
    	return Main.getInstance().getConfig();
    }
}
