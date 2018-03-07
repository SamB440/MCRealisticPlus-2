/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SamB440.MCRealistic.Main;

/*
 * DONE
 */
public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent pje)
	{
		getConfig().set("Players.DefaultWalkSpeed." + pje.getPlayer().getUniqueId(), 0.2);
		pje.getPlayer().setWalkSpeed((float)getConfig().getDouble("Players.DefaultWalkSpeed." + pje.getPlayer().getUniqueId()));
		System.out.println(getConfig().getDouble("Players.DefaultWalkSpeed." + pje.getPlayer().getUniqueId()));
        if (!pje.getPlayer().hasPlayedBefore()) 
        {
            getConfig().addDefault("Players.Thirst." + pje.getPlayer().getUniqueId(), 0);
            getConfig().addDefault("Players.RealName." + pje.getPlayer().getUniqueId(), "null");
            getConfig().addDefault("Players.IsCold." + pje.getPlayer().getUniqueId(), false);
            getConfig().addDefault("Players.InTorch." + pje.getPlayer().getUniqueId(), false);
            getConfig().addDefault("Players.Fatigue." + pje.getPlayer().getUniqueId(), 0);
            getConfig().set("Players.DefaultWalkSpeed." + pje.getPlayer().getUniqueId(), 1);
        }
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
