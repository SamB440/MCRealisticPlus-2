/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
						TextComponent c3 = new TextComponent(ChatColor.GREEN + "/MCRealistic <item> <amount>");
						c3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.WHITE + "Click to paste command.").create()));
						c3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/MCRealistic item 1"));
						p.spigot().sendMessage(c3);
						p.sendMessage(ChatColor.WHITE + "   Aliases: None.");
						p.sendMessage(ChatColor.WHITE + "   Description: Get an item with an amount.");
						p.sendMessage(ChatColor.WHITE + "   Permission(s): mcr.give.item");
						p.sendMessage(ChatColor.YELLOW + "© 2018 IslandEarth. Made with" + " ❤ " + "by SamB440.");
					} else if(args.length == 1) {
						if(args[0].equalsIgnoreCase("reload"))
						{
							p.sendMessage(ChatColor.GREEN + "Reloading...");
							Main.getInstance().reloadConfig();
							Main.getInstance().saveConfig();
							for(Player pl : Bukkit.getOnlinePlayers())
							{
								Main.getInstance().getConfig().set("Players.DefaultWalkSpeed." + pl.getUniqueId(), 0.2);
								pl.setWalkSpeed((float)Main.getInstance().getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getUniqueId()));
							}
							p.sendMessage(ChatColor.GREEN + "Done!");
						}
					} else if(args.length == 2) {
						if(p.hasPermission("mcr.item.give"))
						{
							if(args[0].equalsIgnoreCase("Bandage")) 
							{
								ItemStack Bandage = new ItemStack(Material.PAPER, Integer.parseInt(args[1]));
								ItemMeta BandageItemMeta = Bandage.getItemMeta();
								BandageItemMeta.setDisplayName(ChatColor.DARK_AQUA + "Bandage");
								Bandage.setItemMeta(BandageItemMeta);
								p.getInventory().addItem(Bandage);
							} else if(args[0].equalsIgnoreCase("ChocolateMilk")) {
								ItemStack chocolatemilk = new ItemStack(Material.MILK_BUCKET, Integer.parseInt(args[1]));
								ItemMeta chocolatemilkmeta = chocolatemilk.getItemMeta();
								chocolatemilkmeta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Chocolate Milk");
								chocolatemilkmeta.setLore(Arrays.asList(ChatColor.WHITE + "Drink to gain Speed II."));
								chocolatemilk.setItemMeta(chocolatemilkmeta);
								p.getInventory().addItem(chocolatemilk);
							} else if(args[0].equalsIgnoreCase("Medicine")) {
								ItemStack medicine = new ItemStack(Material.POTION, Integer.parseInt(args[1]));
								ItemMeta medicinemeta = medicine.getItemMeta();
								medicinemeta.setDisplayName(ChatColor.GREEN + "Medicine");
								medicinemeta.setLore(Arrays.asList(ChatColor.WHITE + "Drink to help fight your cold/disease!"));
								medicine.setItemMeta(medicinemeta);
								p.getInventory().addItem(medicine);
							}
						} else p.sendMessage(ChatColor.RED + "You do not have permission to get MCR items.");
					}
				}
			} else p.sendMessage(ChatColor.RED + "MCRealistic is not enabled in this world.");
		} else sender.sendMessage(c + "You must be a player to execute this command.");
		return true;
	}
}
