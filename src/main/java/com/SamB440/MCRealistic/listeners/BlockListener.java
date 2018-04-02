/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Leaves;

import com.SamB440.MCRealistic.Main;
import com.SamB440.MCRealistic.API.Message;
import com.SamB440.MCRealistic.API.data.MessageType;
import com.SamB440.MCRealistic.utils.Lang;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class BlockListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	static ArrayList<Material> ignore = new ArrayList<Material>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent bpe) 
	{
		Player p = bpe.getPlayer();
		Block block = bpe.getBlock();
		if(worlds.contains(p.getWorld()) && p.getGameMode().equals(GameMode.SURVIVAL)) 
		{
        	if (getConfig().getInt("Players.Fatigue." + bpe.getPlayer().getUniqueId()) >= 250 && block.getType() != Material.BED && block.getType() != Material.BED_BLOCK) 
        	{
        		Message m = new Message(p, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.TOO_TIRED, null);
        		m.send();
        		bpe.setCancelled(true);
        		return;
        	}
	        int CurrentFatigue = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
	        getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue));
			if (getConfig().getBoolean("Server.Building.Realistic_Building")) 
			{
	        	if (p.getInventory().getItemInMainHand().hasItemMeta()) {
	        		return;
	        	} else if (ignore.contains(bpe.getBlock().getType())) {
	        		return;
	        	} else if (bpe.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) {
	        		return;
	        	} else if(bpe.getBlock().getLocation().subtract(1.0, 0.0, 0.0).getBlock().getType().equals(Material.LOG) || bpe.getBlock().getLocation().subtract(0.0, 0.0, 1.0).getBlock().getType().equals(Material.LOG) || bpe.getBlock().getLocation().add(1.0, 0.0, 0.0).getBlock().getType().equals(Material.LOG) || bpe.getBlock().getLocation().add(0.0, 0.0, 1.0).getBlock().getType().equals(Material.LOG) || bpe.getBlock().getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals(Material.LOG)) {
	        		return;
	        	}
	    		Location loc = block.getLocation();
	    		loc.setX(loc.getX() + 0.5);
	    		loc.setZ(loc.getZ() + 0.5);
	    		loc.getWorld().spawnFallingBlock(loc, block.getType(), block.getData());
	    		block.setType(Material.AIR);
			}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent bbe) 
	{
		Player p = bbe.getPlayer();
        Block block = bbe.getBlock();
		if(worlds.contains(p.getWorld()) && p.getGameMode().equals(GameMode.SURVIVAL))
		{
	        if (getConfig().getInt("Players.Fatigue." + p.getUniqueId()) >= 250) 
	        {
        		Message m = new Message(p, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.TOO_TIRED, null);
        		m.send();
	            bbe.setCancelled(true);
	            return;
	        }
	        int CurrentFatigue = getConfig().getInt("Players.Fatigue." + p.getUniqueId());
	        getConfig().set("Players.Fatigue." + p.getUniqueId(), (++CurrentFatigue));     	
	        if(block.getType().equals(Material.LOG) || block.getType().equals(Material.LOG_2)) 
	        {
	            if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) && !getConfig().getBoolean("Server.Player.Allow Chop Down Trees With Hands")) 
	            {
	        		Message m = new Message(p, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.NO_HAND_CHOP, null);
	        		m.send();
	                bbe.setCancelled(true);
	            }
	            if ((p.getInventory().getItemInMainHand().getType().equals(Material.WOOD_AXE) || p.getInventory().getItemInMainHand().getType().equals(Material.IRON_AXE) || p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE) || p.getInventory().getItemInMainHand().getType().equals(Material.STONE_AXE)) && getConfig().getBoolean("Server.Player.Trees have random number of drops")) 
	            {
	                Random random5 = new Random();
	                int randomTreeChop = random5.nextInt(2);
	                if (randomTreeChop == 0) 
	                {
	                    p.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
	                }
	                if (randomTreeChop == 1) 
	                {
	                	bbe.setDropItems(false);
	                }
	                if (randomTreeChop == 2) 
	                {
	                    p.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
	                    p.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
	                }
	            }
	        } /*else if(!p.getInventory().getItemInMainHand().getType().equals(Material.SHEARS) && block.getType().equals(Material.LEAVES) || block.getType().equals(Material.LEAVES_2)) {
	        	Random r = new Random();
	        	int randomFruit = r.nextInt(3);
	        	ItemStack banana = getSkull("http://textures.minecraft.net/texture/ce8a3b3d9f89df1f3b996cce89c0f722b3dfa34c2ec2a5b9038010e0dd1ae");
	        	BlockState s = block.getState();
	        	s.update();
	        	TreeSpecies species = ((Leaves)s.getData()).getSpecies();
	        	if(randomFruit == 0 || randomFruit == 2)
	        	{
	        		if(species.equals(TreeSpecies.JUNGLE)) p.getWorld().dropItem(block.getLocation(), banana);
	        	}
	        }*/
        }
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
	public static void addBlocks(ArrayList<Material> b)
	{
		ignore = b;
	}
	private ItemStack getSkull(String url) 
	{
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if(url.isEmpty())
        {
        	return head;
        }
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try 
        {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) 
        {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
		return head;
	}
}
