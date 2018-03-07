/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.SamB440.MCRealistic.Main;

public class MyStats implements CommandExecutor {
	
	String c = "[MCRealistic-2] ";
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(worlds.contains(p.getWorld()))
			{
				if(!p.hasPermission("mcr.mystats")) 
				{
					p.sendMessage(ChatColor.RED + "You don't have permission to see your fatigue.");
				} else if(getConfig().getBoolean("Server.Player.Allow /fatigue")) {
					p.sendMessage(ChatColor.GOLD + "==== " + ChatColor.DARK_GREEN + "My stats are: " + ChatColor.GOLD + " ====");
					p.sendMessage(ChatColor.AQUA + "My name is: " + getConfig().getString(new StringBuilder("Players.RealName.").append(p.getUniqueId()).toString()));
					p.sendMessage(ChatColor.AQUA + "My nickname is: " + p.getDisplayName());
					p.sendMessage(ChatColor.AQUA + "My age is: " + getConfig().getInt(new StringBuilder("Players.RealAge.").append(p.getUniqueId()).toString()));
					p.sendMessage(ChatColor.GOLD + "====================");
					return true;
				}
			} else p.sendMessage(ChatColor.RED + "MyStats is not enabled in this world.");
		} else sender.sendMessage(c + "You must be a player to execute this command.");
		return true;
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
