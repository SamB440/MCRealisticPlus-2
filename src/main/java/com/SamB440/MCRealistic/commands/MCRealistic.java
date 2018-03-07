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
import org.bukkit.entity.Player;

import com.SamB440.MCRealistic.Main;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MCRealistic implements CommandExecutor {

	String c = "[MCRealistic-2] ";
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(worlds.contains(p.getWorld()))
			{
				if(cmd.getName().equalsIgnoreCase("mcr") || cmd.getName().equalsIgnoreCase("mcrealistic") || cmd.getName().equalsIgnoreCase("mcrc") || cmd.getName().equalsIgnoreCase("mcreal"))
				{
					if(args.length == 0)
					{
						TextComponent help = new TextComponent(ChatColor.YELLOW + "Showing help for MCRealistic " + ChatColor.WHITE + "1/1");
						help.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.WHITE + "Showing page 1/1 click to go to the next page or use /mcr 2.").create()));
						//help.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mcr 2"));
						p.spigot().sendMessage(help);
						TextComponent c1 = new TextComponent(ChatColor.GREEN + "/MCRealistic");
						c1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.WHITE + "Click to paste command.").create()));
						c1.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/MCRealistic"));
						p.spigot().sendMessage(c1);
						p.sendMessage(ChatColor.WHITE + "   Aliases: /mcr, mcrc, mcreal.");
						p.sendMessage(ChatColor.WHITE + "   Description: Displays help page.");
						TextComponent c2 = new TextComponent(ChatColor.GREEN + "/MCRealistic reload");
						c2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.WHITE + "Click to paste command.").create()));
						c2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/MCRealistic reload"));
						p.spigot().sendMessage(c2);
						p.sendMessage(ChatColor.WHITE + "   Aliases: None.");
						p.sendMessage(ChatColor.WHITE + "   Description: Reload the plugin.");
						p.sendMessage(ChatColor.WHITE + "   Permission(s): OP");
						p.sendMessage(ChatColor.YELLOW + "© 2018 IslandEarth. Made with" + " ❤ " + "by SamB440.");
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("reload"))
						{
							p.sendMessage(ChatColor.GREEN + "Reloading...");
							Main.getInstance().reloadConfig();
							Main.getInstance().saveConfig();
							p.sendMessage(ChatColor.GREEN + "Done!");
						}
					}
				}
			} else p.sendMessage(ChatColor.RED + "MCRealistic is not enabled in this world.");
		} else sender.sendMessage(c + "You must be a player to execute this command.");
		return true;
	}
}
