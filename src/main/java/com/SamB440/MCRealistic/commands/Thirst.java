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

public class Thirst implements CommandExecutor {
	
	String c = "[MCRealistic-2] ";
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(worlds.contains(p.getWorld()))
			{
				if(!p.hasPermission("mcr.thirst")) 
				{
					p.sendMessage(ChatColor.RED + "You don't have permission to see your thirst.");
				} else if(getConfig().getBoolean("Server.Player.Allow /thirst")) {
					p.sendMessage(ChatColor.GOLD + "==== " + ChatColor.DARK_GREEN + "My thirst is: " + ChatColor.GREEN + getConfig().getInt(new StringBuilder("Players.Thirst.").append(p.getUniqueId()).toString()) + "/200" + ChatColor.GOLD + " ====");
					return true;
				}
			} else p.sendMessage(ChatColor.RED + "Thirst is not enabled in this world.");
		} else sender.sendMessage(c + "You must be a player to execute this command.");
		return true;
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
