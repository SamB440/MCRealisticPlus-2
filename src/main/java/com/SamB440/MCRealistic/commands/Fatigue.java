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

public class Fatigue implements CommandExecutor {
	
	String c = "[MCRealistic-2] ";
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(worlds.contains(p.getWorld()))
			{
				if(!p.hasPermission("mcr.fatigue")) 
				{
					p.sendMessage(ChatColor.RED + "You don't have permission to see your fatigue.");
				} else if(getConfig().getBoolean("Server.Player.Allow /fatigue")) {
					p.sendMessage(ChatColor.GOLD + "==== " + ChatColor.DARK_GREEN + "My fatigue is: " + ChatColor.GREEN + getConfig().getInt(new StringBuilder("Players.Fatigue.").append(p.getUniqueId()).toString()) + "/250" + ChatColor.GOLD + " ====");
					return true;
				}
			} else p.sendMessage(ChatColor.RED + "Fatigue is not enabled in this world.");
		} else sender.sendMessage(c + "You must be a player to execute this command.");
		return true;
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
