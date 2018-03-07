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
