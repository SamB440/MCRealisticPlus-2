/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SamB440.MCRealistic.Main;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/*
 * DONE
 */
public class JoinListener implements Listener {
	
	Logger log = Bukkit.getLogger();
	String c = "[MCRealistic-2] ";
	
	@EventHandler
	public void onJoin(PlayerJoinEvent pje)
	{
		Player p = pje.getPlayer();
		getConfig().set("Players.DefaultWalkSpeed." + p.getUniqueId(), 0.2);
		p.setWalkSpeed((float)getConfig().getDouble("Players.DefaultWalkSpeed." + p.getUniqueId()));
        if(!p.hasPlayedBefore()) 
        {
            getConfig().addDefault("Players.Thirst." + p.getUniqueId(), 0);
            getConfig().addDefault("Players.RealName." + p.getUniqueId(), "null");
            getConfig().addDefault("Players.IsCold." + p.getUniqueId(), false);
            getConfig().addDefault("Players.InTorch." + p.getUniqueId(), false);
            getConfig().addDefault("Players.Fatigue." + p.getUniqueId(), 0);
            getConfig().set("Players.DefaultWalkSpeed." + p.getUniqueId(), 1);
        }
        String[] FirstArray = RespawnListener.getFirstNames().toArray(new String[20]);
        Random random1 = new Random();
        int randomFirstId = random1.nextInt(FirstArray.length);
        String[] LastArray = RespawnListener.getLastNames().toArray(new String[20]);
        Random random2 = new Random();
        int randomLastId = random2.nextInt(LastArray.length);
        Random random3 = new Random();
        int randomAge = random3.nextInt(50);
        getConfig().set("Players.RealName." + p.getUniqueId(), String.valueOf(String.valueOf(FirstArray[randomFirstId])) + " " + LastArray[randomLastId]);
        getConfig().set("Players.RealAge." + p.getUniqueId(), randomAge + 15);
        if(p.isOp())
        {
        	if(!isUpdated())
        	{
        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() 
        		{
        			@Override
        			public void run() {
        				p.sendMessage(" ");
    	    		    TextComponent message = new TextComponent(ChatColor.GOLD + "There is a new version for MCRealistic-2 available! Click this message to download it.");
    	    		    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to visit the download page.").create()));
    	    		    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/mcrealistic-2-mcrealisticplus-recoded.21628/updates"));
    	    		    p.spigot().sendMessage(message);
    	        		p.sendMessage(" ");
    	        		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        		    }
        		}, 100L);
        	}
        }
	}
	private boolean isUpdated()
	{
	    try 
	    {
	        HttpsURLConnection c = (HttpsURLConnection)new URL("https://www.spigotmc.org/api/general.php").openConnection();
	        c.setDoOutput(true);
	        c.setRequestMethod("POST");
	        c.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=21628").getBytes("UTF-8"));
	        String oldVersion = Main.getInstance().getDescription().getVersion();
	        String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine().replaceAll("[a-zA-Z ]", "");
	        if(!newVersion.equals(oldVersion)) 
	        {
	        	return false;
	        }
	    }
	    catch(Exception e) {
	        log.info(c + "Failed to check for updates.");
	    }
		return true;
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
