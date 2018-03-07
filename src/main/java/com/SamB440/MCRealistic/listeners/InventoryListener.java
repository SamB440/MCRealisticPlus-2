/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.SamB440.MCRealistic.Main;

public class InventoryListener implements Listener {
	
	ArrayList<World> worlds = Main.getInstance().getWorlds();
	
	private static int[] $SWITCH_TABLE$org$bukkit$Material;
	
    @EventHandler
    public void OnInvClicked(InventoryCloseEvent ice) {
        if (ice.getPlayer() instanceof Player) 
        {
        	if(getConfig().getBoolean("Server.Player.Weight"))
        	{
	            Player pl = (Player)ice.getPlayer();
	            List<String> worlds = new ArrayList<>();
	            worlds = getConfig().getStringList("Worlds");
	    		if(worlds.contains(pl.getWorld().getName())) 
	    		{
	    			if(!(pl.getGameMode().equals(GameMode.CREATIVE) || pl.getGameMode().equals(GameMode.SPECTATOR))) 
	    			{
	    				pl.setWalkSpeed(0.2f);
	    				if (pl.getInventory().getChestplate() != null) 
	    				{
	    					switch ($SWITCH_TABLE$org$bukkit$Material()[pl.getInventory().getChestplate().getType().ordinal()]) 
	    					{
		    					case 250: {
		    						getConfig().set("Players.ChestplateWeight." + pl.getUniqueId(), 5);
		    						break;
		    					}
		    					case 246: {
		    						getConfig().set("Players.ChestplateWeight." + pl.getUniqueId(), 5);
		    						break;
		    					}
		    					case 258: {
		    						getConfig().set("Players.ChestplateWeight." + pl.getUniqueId(), 6);
		    						break;
		    					}
		    					case 254: {
		    						getConfig().set("Players.ChestplateWeight." + pl.getUniqueId(), 8);
		    						break;
		    					}
		    					default: {
		    						getConfig().set("Players.ChestplateWeight." + pl.getUniqueId(), 0);
		    						break;
		    					}
	    					}
	    				} else getConfig().set("Players.ChestplateWeight." + pl.getUniqueId(), 0);
	    				if (pl.getInventory().getLeggings() != null) 
	    				{
							switch ($SWITCH_TABLE$org$bukkit$Material()[pl.getInventory().getLeggings().getType().ordinal()]) 
							{
								case 251: 
								{
									getConfig().set("Players.LeggingsWeight" + pl.getUniqueId(), 5);
									break;
								}
								case 247: 
								{
									getConfig().set("Players.LeggingsWeight" + pl.getUniqueId(), 5);
									break;
								}
								case 259: 
								{
									getConfig().set("Players.LeggingsWeight" + pl.getUniqueId(), 6);
									break;
								}
								case 255: 
								{
									getConfig().set("Players.LeggingsWeight" + pl.getUniqueId(), 8);
									break;
								}
								default: 
								{
									getConfig().set("Players.LeggingsWeight." + pl.getUniqueId(), 0);
									break;
								}
							}
						} else getConfig().set("Players.LeggingsWeight." + pl.getUniqueId(), 0);
	    				float WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + pl.getUniqueId());
	    				float WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + pl.getUniqueId());
	    				float WeightCombined = WeightLeggings + WeightChestPlate;
	    				pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + ice.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
	    			}	
	    		}	
        	}
        }
    }
    private FileConfiguration getConfig()
    {
    	return Main.getInstance().getConfig();
    }
    static int[] $SWITCH_TABLE$org$bukkit$Material() {
        int[] arrn2 = $SWITCH_TABLE$org$bukkit$Material;
        if (arrn2 != null) {
            return arrn2;
        }
        int[] arrn = new int[Material.values().length];
        try {
            arrn[Material.ACACIA_DOOR.ordinal()] = 197;
        }
        catch (NoSuchFieldError var2_2) {

        }
        try {
            arrn[Material.ACACIA_DOOR_ITEM.ordinal()] = 372;
        }
        catch (NoSuchFieldError var2_3) {
             
        }
        try {
            arrn[Material.ACACIA_FENCE.ordinal()] = 193;
        }
        catch (NoSuchFieldError var2_4) {
             
        }
        try {
            arrn[Material.ACACIA_FENCE_GATE.ordinal()] = 188;
        }
        catch (NoSuchFieldError var2_5) {
             
        }
        try {
            arrn[Material.ACACIA_STAIRS.ordinal()] = 164;
        }
        catch (NoSuchFieldError var2_6) {
             
        }
        try {
            arrn[Material.ACTIVATOR_RAIL.ordinal()] = 158;
        }
        catch (NoSuchFieldError var2_7) {
             
        }
        try {
            arrn[Material.AIR.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2_8) {
             
        }
        try {
            arrn[Material.ANVIL.ordinal()] = 146;
        }
        catch (NoSuchFieldError var2_9) {
             
        }
        try {
            arrn[Material.APPLE.ordinal()] = 203;
        }
        catch (NoSuchFieldError var2_10) {
             
        }
        try {
            arrn[Material.ARMOR_STAND.ordinal()] = 359;
        }
        catch (NoSuchFieldError var2_11) {
             
        }
        try {
            arrn[Material.ARROW.ordinal()] = 205;
        }
        catch (NoSuchFieldError var2_12) {
             
        }
        try {
            arrn[Material.BAKED_POTATO.ordinal()] = 336;
        }
        catch (NoSuchFieldError var2_13) {
             
        }
        try {
            arrn[Material.BANNER.ordinal()] = 368;
        }
        catch (NoSuchFieldError var2_14) {
             
        }
        try {
            arrn[Material.BARRIER.ordinal()] = 167;
        }
        catch (NoSuchFieldError var2_15) {
             
        }
        try {
            arrn[Material.BEACON.ordinal()] = 139;
        }
        catch (NoSuchFieldError var2_16) {
             
        }
        try {
            arrn[Material.BED.ordinal()] = 298;
        }
        catch (NoSuchFieldError var2_17) {
             
        }
        try {
            arrn[Material.BEDROCK.ordinal()] = 8;
        }
        catch (NoSuchFieldError var2_18) {
             
        }
        try {
            arrn[Material.BED_BLOCK.ordinal()] = 27;
        }
        catch (NoSuchFieldError var2_19) {
             
        }
        try {
            arrn[Material.BIRCH_DOOR.ordinal()] = 195;
        }
        catch (NoSuchFieldError var2_20) {
             
        }
        try {
            arrn[Material.BIRCH_DOOR_ITEM.ordinal()] = 370;
        }
        catch (NoSuchFieldError var2_21) {
             
        }
        try {
            arrn[Material.BIRCH_FENCE.ordinal()] = 190;
        }
        catch (NoSuchFieldError var2_22) {
             
        }
        try {
            arrn[Material.BIRCH_FENCE_GATE.ordinal()] = 185;
        }
        catch (NoSuchFieldError var2_23) {
             
        }
        try {
            arrn[Material.BIRCH_WOOD_STAIRS.ordinal()] = 136;
        }
        catch (NoSuchFieldError var2_24) {
             
        }
        try {
            arrn[Material.BLAZE_POWDER.ordinal()] = 320;
        }
        catch (NoSuchFieldError var2_25) {
             
        }
        try {
            arrn[Material.BLAZE_ROD.ordinal()] = 312;
        }
        catch (NoSuchFieldError var2_26) {
             
        }
        try {
            arrn[Material.BOAT.ordinal()] = 276;
        }
        catch (NoSuchFieldError var2_27) {
             
        }
        try {
            arrn[Material.BONE.ordinal()] = 295;
        }
        catch (NoSuchFieldError var2_28) {
             
        }
        try {
            arrn[Material.BOOK.ordinal()] = 283;
        }
        catch (NoSuchFieldError var2_29) {
             
        }
        try {
            arrn[Material.BOOKSHELF.ordinal()] = 48;
        }
        catch (NoSuchFieldError var2_30) {
             
        }
        try {
            arrn[Material.BOOK_AND_QUILL.ordinal()] = 329;
        }
        catch (NoSuchFieldError var2_31) {
             
        }
        try {
            arrn[Material.BOW.ordinal()] = 204;
        }
        catch (NoSuchFieldError var2_32) {
             
        }
        try {
            arrn[Material.BOWL.ordinal()] = 224;
        }
        catch (NoSuchFieldError var2_33) {
             
        }
        try {
            arrn[Material.BREAD.ordinal()] = 240;
        }
        catch (NoSuchFieldError var2_34) {
             
        }
        try {
            arrn[Material.BREWING_STAND.ordinal()] = 118;
        }
        catch (NoSuchFieldError var2_35) {
             
        }
        try {
            arrn[Material.BREWING_STAND_ITEM.ordinal()] = 322;
        }
        catch (NoSuchFieldError var2_36) {
             
        }
        try {
            arrn[Material.BRICK.ordinal()] = 46;
        }
        catch (NoSuchFieldError var2_37) {
             
        }
        try {
            arrn[Material.BRICK_STAIRS.ordinal()] = 109;
        }
        catch (NoSuchFieldError var2_38) {
             
        }
        try {
            arrn[Material.BROWN_MUSHROOM.ordinal()] = 40;
        }
        catch (NoSuchFieldError var2_39) {
             
        }
        try {
            arrn[Material.BUCKET.ordinal()] = 268;
        }
        catch (NoSuchFieldError var2_40) {
             
        }
        try {
            arrn[Material.BURNING_FURNACE.ordinal()] = 63;
        }
        catch (NoSuchFieldError var2_41) {
             
        }
        try {
            arrn[Material.CACTUS.ordinal()] = 82;
        }
        catch (NoSuchFieldError var2_42) {
             
        }
        try {
            arrn[Material.CAKE.ordinal()] = 297;
        }
        catch (NoSuchFieldError var2_43) {
             
        }
        try {
            arrn[Material.CAKE_BLOCK.ordinal()] = 93;
        }
        catch (NoSuchFieldError var2_44) {
             
        }
        try {
            arrn[Material.CARPET.ordinal()] = 172;
        }
        catch (NoSuchFieldError var2_45) {
             
        }
        try {
            arrn[Material.CARROT.ordinal()] = 142;
        }
        catch (NoSuchFieldError var2_46) {
             
        }
        try {
            arrn[Material.CARROT_ITEM.ordinal()] = 334;
        }
        catch (NoSuchFieldError var2_47) {
             
        }
        try {
            arrn[Material.CARROT_STICK.ordinal()] = 341;
        }
        catch (NoSuchFieldError var2_48) {
             
        }
        try {
            arrn[Material.CAULDRON.ordinal()] = 119;
        }
        catch (NoSuchFieldError var2_49) {
             
        }
        try {
            arrn[Material.CAULDRON_ITEM.ordinal()] = 323;
        }
        catch (NoSuchFieldError var2_50) {
             
        }
        try {
            arrn[Material.CHAINMAIL_BOOTS.ordinal()] = 248;
        }
        catch (NoSuchFieldError var2_51) {
             
        }
        try {
            arrn[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 246;
        }
        catch (NoSuchFieldError var2_52) {
             
        }
        try {
            arrn[Material.CHAINMAIL_HELMET.ordinal()] = 245;
        }
        catch (NoSuchFieldError var2_53) {
             
        }
        try {
            arrn[Material.CHAINMAIL_LEGGINGS.ordinal()] = 247;
        }
        catch (NoSuchFieldError var2_54) {
             
        }
        try {
            arrn[Material.CHEST.ordinal()] = 55;
        }
        catch (NoSuchFieldError var2_55) {
             
        }
        try {
            arrn[Material.CLAY.ordinal()] = 83;
        }
        catch (NoSuchFieldError var2_56) {
             
        }
        try {
            arrn[Material.CLAY_BALL.ordinal()] = 280;
        }
        catch (NoSuchFieldError var2_57) {
             
        }
        try {
            arrn[Material.CLAY_BRICK.ordinal()] = 279;
        }
        catch (NoSuchFieldError var2_58) {
             
        }
        try {
            arrn[Material.COAL.ordinal()] = 206;
        }
        catch (NoSuchFieldError var2_59) {
             
        }
        try {
            arrn[Material.COAL_BLOCK.ordinal()] = 174;
        }
        catch (NoSuchFieldError var2_60) {
             
        }
        try {
            arrn[Material.COAL_ORE.ordinal()] = 17;
        }
        catch (NoSuchFieldError var2_61) {
             
        }
        try {
            arrn[Material.COBBLESTONE.ordinal()] = 5;
        }
        catch (NoSuchFieldError var2_62) {
             
        }
        try {
            arrn[Material.COBBLESTONE_STAIRS.ordinal()] = 68;
        }
        catch (NoSuchFieldError var2_63) {
             
        }
        try {
            arrn[Material.COBBLE_WALL.ordinal()] = 140;
        }
        catch (NoSuchFieldError var2_64) {
             
        }
        try {
            arrn[Material.COCOA.ordinal()] = 128;
        }
        catch (NoSuchFieldError var2_65) {
             
        }
        try {
            arrn[Material.COMMAND.ordinal()] = 138;
        }
        catch (NoSuchFieldError var2_66) {
             
        }
        try {
            arrn[Material.COMMAND_MINECART.ordinal()] = 365;
        }
        catch (NoSuchFieldError var2_67) {
             
        }
        try {
            arrn[Material.COMPASS.ordinal()] = 288;
        }
        catch (NoSuchFieldError var2_68) {
             
        }
        try {
            arrn[Material.COOKED_BEEF.ordinal()] = 307;
        }
        catch (NoSuchFieldError var2_69) {
             
        }
        try {
            arrn[Material.COOKED_CHICKEN.ordinal()] = 309;
        }
        catch (NoSuchFieldError var2_70) {
             
        }
        try {
            arrn[Material.COOKED_FISH.ordinal()] = 293;
        }
        catch (NoSuchFieldError var2_71) {
             
        }
        try {
            arrn[Material.COOKED_MUTTON.ordinal()] = 367;
        }
        catch (NoSuchFieldError var2_72) {
             
        }
        try {
            arrn[Material.COOKED_RABBIT.ordinal()] = 355;
        }
        catch (NoSuchFieldError var2_73) {
             
        }
        try {
            arrn[Material.COOKIE.ordinal()] = 300;
        }
        catch (NoSuchFieldError var2_74) {
             
        }
        try {
            arrn[Material.CROPS.ordinal()] = 60;
        }
        catch (NoSuchFieldError var2_75) {
             
        }
        try {
            arrn[Material.DARK_OAK_DOOR.ordinal()] = 198;
        }
        catch (NoSuchFieldError var2_76) {
             
        }
        try {
            arrn[Material.DARK_OAK_DOOR_ITEM.ordinal()] = 373;
        }
        catch (NoSuchFieldError var2_77) {
             
        }
        try {
            arrn[Material.DARK_OAK_FENCE.ordinal()] = 192;
        }
        catch (NoSuchFieldError var2_78) {
             
        }
        try {
            arrn[Material.DARK_OAK_FENCE_GATE.ordinal()] = 187;
        }
        catch (NoSuchFieldError var2_79) {
             
        }
        try {
            arrn[Material.DARK_OAK_STAIRS.ordinal()] = 165;
        }
        catch (NoSuchFieldError var2_80) {
             
        }
        try {
            arrn[Material.DAYLIGHT_DETECTOR.ordinal()] = 152;
        }
        catch (NoSuchFieldError var2_81) {
             
        }
        try {
            arrn[Material.DAYLIGHT_DETECTOR_INVERTED.ordinal()] = 179;
        }
        catch (NoSuchFieldError var2_82) {
             
        }
        try {
            arrn[Material.DEAD_BUSH.ordinal()] = 33;
        }
        catch (NoSuchFieldError var2_83) {
             
        }
        try {
            arrn[Material.DETECTOR_RAIL.ordinal()] = 29;
        }
        catch (NoSuchFieldError var2_84) {
             
        }
        try {
            arrn[Material.DIAMOND.ordinal()] = 207;
        }
        catch (NoSuchFieldError var2_85) {
             
        }
        try {
            arrn[Material.DIAMOND_AXE.ordinal()] = 222;
        }
        catch (NoSuchFieldError var2_86) {
             
        }
        try {
            arrn[Material.DIAMOND_BARDING.ordinal()] = 362;
        }
        catch (NoSuchFieldError var2_87) {
             
        }
        try {
            arrn[Material.DIAMOND_BLOCK.ordinal()] = 58;
        }
        catch (NoSuchFieldError var2_88) {
             
        }
        try {
            arrn[Material.DIAMOND_BOOTS.ordinal()] = 256;
        }
        catch (NoSuchFieldError var2_89) {
             
        }
        try {
            arrn[Material.DIAMOND_CHESTPLATE.ordinal()] = 254;
        }
        catch (NoSuchFieldError var2_90) {
             
        }
        try {
            arrn[Material.DIAMOND_HELMET.ordinal()] = 253;
        }
        catch (NoSuchFieldError var2_91) {
             
        }
        try {
            arrn[Material.DIAMOND_HOE.ordinal()] = 236;
        }
        catch (NoSuchFieldError var2_92) {
             
        }
        try {
            arrn[Material.DIAMOND_LEGGINGS.ordinal()] = 255;
        }
        catch (NoSuchFieldError var2_93) {
             
        }
        try {
            arrn[Material.DIAMOND_ORE.ordinal()] = 57;
        }
        catch (NoSuchFieldError var2_94) {
             
        }
        try {
            arrn[Material.DIAMOND_PICKAXE.ordinal()] = 221;
        }
        catch (NoSuchFieldError var2_95) {
             
        }
        try {
            arrn[Material.DIAMOND_SPADE.ordinal()] = 220;
        }
        catch (NoSuchFieldError var2_96) {
             
        }
        try {
            arrn[Material.DIAMOND_SWORD.ordinal()] = 219;
        }
        catch (NoSuchFieldError var2_97) {
             
        }
        try {
            arrn[Material.DIODE.ordinal()] = 299;
        }
        catch (NoSuchFieldError var2_98) {
             
        }
        try {
            arrn[Material.DIODE_BLOCK_OFF.ordinal()] = 94;
        }
        catch (NoSuchFieldError var2_99) {
             
        }
        try {
            arrn[Material.DIODE_BLOCK_ON.ordinal()] = 95;
        }
        catch (NoSuchFieldError var2_100) {
             
        }
        try {
            arrn[Material.DIRT.ordinal()] = 4;
        }
        catch (NoSuchFieldError var2_101) {
             
        }
        try {
            arrn[Material.DISPENSER.ordinal()] = 24;
        }
        catch (NoSuchFieldError var2_102) {
             
        }
        try {
            arrn[Material.DOUBLE_PLANT.ordinal()] = 176;
        }
        catch (NoSuchFieldError var2_103) {
             
        }
        try {
            arrn[Material.DOUBLE_STEP.ordinal()] = 44;
        }
        catch (NoSuchFieldError var2_104) {
             
        }
        try {
            arrn[Material.DOUBLE_STONE_SLAB2.ordinal()] = 182;
        }
        catch (NoSuchFieldError var2_105) {
             
        }
        try {
            arrn[Material.DRAGON_EGG.ordinal()] = 123;
        }
        catch (NoSuchFieldError var2_106) {
             
        }
        try {
            arrn[Material.DROPPER.ordinal()] = 159;
        }
        catch (NoSuchFieldError var2_107) {
             
        }
        try {
            arrn[Material.EGG.ordinal()] = 287;
        }
        catch (NoSuchFieldError var2_108) {
             
        }
        try {
            arrn[Material.EMERALD.ordinal()] = 331;
        }
        catch (NoSuchFieldError var2_109) {
             
        }
        try {
            arrn[Material.EMERALD_BLOCK.ordinal()] = 134;
        }
        catch (NoSuchFieldError var2_110) {
             
        }
        try {
            arrn[Material.EMERALD_ORE.ordinal()] = 130;
        }
        catch (NoSuchFieldError var2_111) {
             
        }
        try {
            arrn[Material.EMPTY_MAP.ordinal()] = 338;
        }
        catch (NoSuchFieldError var2_112) {
             
        }
        try {
            arrn[Material.ENCHANTED_BOOK.ordinal()] = 346;
        }
        catch (NoSuchFieldError var2_113) {
             
        }
        try {
            arrn[Material.ENCHANTMENT_TABLE.ordinal()] = 117;
        }
        catch (NoSuchFieldError var2_114) {
             
        }
        try {
            arrn[Material.ENDER_CHEST.ordinal()] = 131;
        }
        catch (NoSuchFieldError var2_115) {
             
        }
        try {
            arrn[Material.ENDER_PEARL.ordinal()] = 311;
        }
        catch (NoSuchFieldError var2_116) {
             
        }
        try {
            arrn[Material.ENDER_PORTAL.ordinal()] = 120;
        }
        catch (NoSuchFieldError var2_117) {
             
        }
        try {
            arrn[Material.ENDER_PORTAL_FRAME.ordinal()] = 121;
        }
        catch (NoSuchFieldError var2_118) {
             
        }
        try {
            arrn[Material.ENDER_STONE.ordinal()] = 122;
        }
        catch (NoSuchFieldError var2_119) {
             
        }
        try {
            arrn[Material.EXPLOSIVE_MINECART.ordinal()] = 350;
        }
        catch (NoSuchFieldError var2_120) {
             
        }
        try {
            arrn[Material.EXP_BOTTLE.ordinal()] = 327;
        }
        catch (NoSuchFieldError var2_121) {
             
        }
        try {
            arrn[Material.EYE_OF_ENDER.ordinal()] = 324;
        }
        catch (NoSuchFieldError var2_122) {
             
        }
        try {
            arrn[Material.FEATHER.ordinal()] = 231;
        }
        catch (NoSuchFieldError var2_123) {
             
        }
        try {
            arrn[Material.FENCE.ordinal()] = 86;
        }
        catch (NoSuchFieldError var2_124) {
             
        }
        try {
            arrn[Material.FENCE_GATE.ordinal()] = 108;
        }
        catch (NoSuchFieldError var2_125) {
             
        }
        try {
            arrn[Material.FERMENTED_SPIDER_EYE.ordinal()] = 319;
        }
        catch (NoSuchFieldError var2_126) {
             
        }
        try {
            arrn[Material.FIRE.ordinal()] = 52;
        }
        catch (NoSuchFieldError var2_127) {
             
        }
        try {
            arrn[Material.FIREBALL.ordinal()] = 328;
        }
        catch (NoSuchFieldError var2_128) {
             
        }
        try {
            arrn[Material.FIREWORK.ordinal()] = 344;
        }
        catch (NoSuchFieldError var2_129) {
             
        }
        try {
            arrn[Material.FIREWORK_CHARGE.ordinal()] = 345;
        }
        catch (NoSuchFieldError var2_130) {
             
        }
        try {
            arrn[Material.FISHING_ROD.ordinal()] = 289;
        }
        catch (NoSuchFieldError var2_131) {
             
        }
        try {
            arrn[Material.FLINT.ordinal()] = 261;
        }
        catch (NoSuchFieldError var2_132) {
             
        }
        try {
            arrn[Material.FLINT_AND_STEEL.ordinal()] = 202;
        }
        catch (NoSuchFieldError var2_133) {
             
        }
        try {
            arrn[Material.FLOWER_POT.ordinal()] = 141;
        }
        catch (NoSuchFieldError var2_134) {
             
        }
        try {
            arrn[Material.FLOWER_POT_ITEM.ordinal()] = 333;
        }
        catch (NoSuchFieldError var2_135) {
             
        }
        try {
            arrn[Material.FURNACE.ordinal()] = 62;
        }
        catch (NoSuchFieldError var2_136) {
             
        }
        try {
            arrn[Material.GHAST_TEAR.ordinal()] = 313;
        }
        catch (NoSuchFieldError var2_137) {
             
        }
        try {
            arrn[Material.GLASS.ordinal()] = 21;
        }
        catch (NoSuchFieldError var2_138) {
             
        }
        try {
            arrn[Material.GLASS_BOTTLE.ordinal()] = 317;
        }
        catch (NoSuchFieldError var2_139) {
             
        }
        try {
            arrn[Material.GLOWING_REDSTONE_ORE.ordinal()] = 75;
        }
        catch (NoSuchFieldError var2_140) {
             
        }
        try {
            arrn[Material.GLOWSTONE.ordinal()] = 90;
        }
        catch (NoSuchFieldError var2_141) {
             
        }
        try {
            arrn[Material.GLOWSTONE_DUST.ordinal()] = 291;
        }
        catch (NoSuchFieldError var2_142) {
             
        }
        try {
            arrn[Material.GOLDEN_APPLE.ordinal()] = 265;
        }
        catch (NoSuchFieldError var2_143) {
             
        }
        try {
            arrn[Material.GOLDEN_CARROT.ordinal()] = 339;
        }
        catch (NoSuchFieldError var2_144) {
             
        }
        try {
            arrn[Material.GOLD_AXE.ordinal()] = 229;
        }
        catch (NoSuchFieldError var2_145) {
             
        }
        try {
            arrn[Material.GOLD_BARDING.ordinal()] = 361;
        }
        catch (NoSuchFieldError var2_146) {
             
        }
        try {
            arrn[Material.GOLD_BLOCK.ordinal()] = 42;
        }
        catch (NoSuchFieldError var2_147) {
             
        }
        try {
            arrn[Material.GOLD_BOOTS.ordinal()] = 260;
        }
        catch (NoSuchFieldError var2_148) {
             
        }
        try {
            arrn[Material.GOLD_CHESTPLATE.ordinal()] = 258;
        }
        catch (NoSuchFieldError var2_149) {
             
        }
        try {
            arrn[Material.GOLD_HELMET.ordinal()] = 257;
        }
        catch (NoSuchFieldError var2_150) {
             
        }
        try {
            arrn[Material.GOLD_HOE.ordinal()] = 237;
        }
        catch (NoSuchFieldError var2_151) {
             
        }
        try {
            arrn[Material.GOLD_INGOT.ordinal()] = 209;
        }
        catch (NoSuchFieldError var2_152) {
             
        }
        try {
            arrn[Material.GOLD_LEGGINGS.ordinal()] = 259;
        }
        catch (NoSuchFieldError var2_153) {
             
        }
        try {
            arrn[Material.GOLD_NUGGET.ordinal()] = 314;
        }
        catch (NoSuchFieldError var2_154) {
             
        }
        try {
            arrn[Material.GOLD_ORE.ordinal()] = 15;
        }
        catch (NoSuchFieldError var2_155) {
             
        }
        try {
            arrn[Material.GOLD_PICKAXE.ordinal()] = 228;
        }
        catch (NoSuchFieldError var2_156) {
             
        }
        try {
            arrn[Material.GOLD_PLATE.ordinal()] = 148;
        }
        catch (NoSuchFieldError var2_157) {
             
        }
        try {
            arrn[Material.GOLD_RECORD.ordinal()] = 374;
        }
        catch (NoSuchFieldError var2_158) {
             
        }
        try {
            arrn[Material.GOLD_SPADE.ordinal()] = 227;
        }
        catch (NoSuchFieldError var2_159) {
             
        }
        try {
            arrn[Material.GOLD_SWORD.ordinal()] = 226;
        }
        catch (NoSuchFieldError var2_160) {
             
        }
        try {
            arrn[Material.GRASS.ordinal()] = 3;
        }
        catch (NoSuchFieldError var2_161) {
             
        }
        try {
            arrn[Material.GRAVEL.ordinal()] = 14;
        }
        catch (NoSuchFieldError var2_162) {
             
        }
        try {
            arrn[Material.GREEN_RECORD.ordinal()] = 375;
        }
        catch (NoSuchFieldError var2_163) {
             
        }
        try {
            arrn[Material.GRILLED_PORK.ordinal()] = 263;
        }
        catch (NoSuchFieldError var2_164) {
             
        }
        try {
            arrn[Material.HARD_CLAY.ordinal()] = 173;
        }
        catch (NoSuchFieldError var2_165) {
             
        }
        try {
            arrn[Material.HAY_BLOCK.ordinal()] = 171;
        }
        catch (NoSuchFieldError var2_166) {
             
        }
        try {
            arrn[Material.HOPPER.ordinal()] = 155;
        }
        catch (NoSuchFieldError var2_167) {
             
        }
        try {
            arrn[Material.HOPPER_MINECART.ordinal()] = 351;
        }
        catch (NoSuchFieldError var2_168) {
             
        }
        try {
            arrn[Material.HUGE_MUSHROOM_1.ordinal()] = 100;
        }
        catch (NoSuchFieldError var2_169) {
             
        }
        try {
            arrn[Material.HUGE_MUSHROOM_2.ordinal()] = 101;
        }
        catch (NoSuchFieldError var2_170) {
             
        }
        try {
            arrn[Material.ICE.ordinal()] = 80;
        }
        catch (NoSuchFieldError var2_171) {
             
        }
        try {
            arrn[Material.INK_SACK.ordinal()] = 294;
        }
        catch (NoSuchFieldError var2_172) {
             
        }
        try {
            arrn[Material.IRON_AXE.ordinal()] = 201;
        }
        catch (NoSuchFieldError var2_173) {
             
        }
        try {
            arrn[Material.IRON_BARDING.ordinal()] = 360;
        }
        catch (NoSuchFieldError var2_174) {
             
        }
        try {
            arrn[Material.IRON_BLOCK.ordinal()] = 43;
        }
        catch (NoSuchFieldError var2_175) {
             
        }
        try {
            arrn[Material.IRON_BOOTS.ordinal()] = 252;
        }
        catch (NoSuchFieldError var2_176) {
             
        }
        try {
            arrn[Material.IRON_CHESTPLATE.ordinal()] = 250;
        }
        catch (NoSuchFieldError var2_177) {
             
        }
        try {
            arrn[Material.IRON_DOOR.ordinal()] = 273;
        }
        catch (NoSuchFieldError var2_178) {
             
        }
        try {
            arrn[Material.IRON_DOOR_BLOCK.ordinal()] = 72;
        }
        catch (NoSuchFieldError var2_179) {
             
        }
        try {
            arrn[Material.IRON_FENCE.ordinal()] = 102;
        }
        catch (NoSuchFieldError var2_180) {
             
        }
        try {
            arrn[Material.IRON_HELMET.ordinal()] = 249;
        }
        catch (NoSuchFieldError var2_181) {
             
        }
        try {
            arrn[Material.IRON_HOE.ordinal()] = 235;
        }
        catch (NoSuchFieldError var2_182) {
             
        }
        try {
            arrn[Material.IRON_INGOT.ordinal()] = 208;
        }
        catch (NoSuchFieldError var2_183) {
             
        }
        try {
            arrn[Material.IRON_LEGGINGS.ordinal()] = 251;
        }
        catch (NoSuchFieldError var2_184) {
             
        }
        try {
            arrn[Material.IRON_ORE.ordinal()] = 16;
        }
        catch (NoSuchFieldError var2_185) {
             
        }
        try {
            arrn[Material.IRON_PICKAXE.ordinal()] = 200;
        }
        catch (NoSuchFieldError var2_186) {
             
        }
        try {
            arrn[Material.IRON_PLATE.ordinal()] = 149;
        }
        catch (NoSuchFieldError var2_187) {
             
        }
        try {
            arrn[Material.IRON_SPADE.ordinal()] = 199;
        }
        catch (NoSuchFieldError var2_188) {
             
        }
        try {
            arrn[Material.IRON_SWORD.ordinal()] = 210;
        }
        catch (NoSuchFieldError var2_189) {
             
        }
        try {
            arrn[Material.IRON_TRAPDOOR.ordinal()] = 168;
        }
        catch (NoSuchFieldError var2_190) {
             
        }
        try {
            arrn[Material.ITEM_FRAME.ordinal()] = 332;
        }
        catch (NoSuchFieldError var2_191) {
             
        }
        try {
            arrn[Material.JACK_O_LANTERN.ordinal()] = 92;
        }
        catch (NoSuchFieldError var2_192) {
             
        }
        try {
            arrn[Material.JUKEBOX.ordinal()] = 85;
        }
        catch (NoSuchFieldError var2_193) {
             
        }
        try {
            arrn[Material.JUNGLE_DOOR.ordinal()] = 196;
        }
        catch (NoSuchFieldError var2_194) {
             
        }
        try {
            arrn[Material.JUNGLE_DOOR_ITEM.ordinal()] = 371;
        }
        catch (NoSuchFieldError var2_195) {
             
        }
        try {
            arrn[Material.JUNGLE_FENCE.ordinal()] = 191;
        }
        catch (NoSuchFieldError var2_196) {
             
        }
        try {
            arrn[Material.JUNGLE_FENCE_GATE.ordinal()] = 186;
        }
        catch (NoSuchFieldError var2_197) {
             
        }
        try {
            arrn[Material.JUNGLE_WOOD_STAIRS.ordinal()] = 137;
        }
        catch (NoSuchFieldError var2_198) {
             
        }
        try {
            arrn[Material.LADDER.ordinal()] = 66;
        }
        catch (NoSuchFieldError var2_199) {
             
        }
        try {
            arrn[Material.LAPIS_BLOCK.ordinal()] = 23;
        }
        catch (NoSuchFieldError var2_200) {
             
        }
        try {
            arrn[Material.LAPIS_ORE.ordinal()] = 22;
        }
        catch (NoSuchFieldError var2_201) {
             
        }
        try {
            arrn[Material.LAVA.ordinal()] = 11;
        }
        catch (NoSuchFieldError var2_202) {
             
        }
        try {
            arrn[Material.LAVA_BUCKET.ordinal()] = 270;
        }
        catch (NoSuchFieldError var2_203) {
             
        }
        try {
            arrn[Material.LEASH.ordinal()] = 363;
        }
        catch (NoSuchFieldError var2_204) {
             
        }
        try {
            arrn[Material.LEATHER.ordinal()] = 277;
        }
        catch (NoSuchFieldError var2_205) {
             
        }
        try {
            arrn[Material.LEATHER_BOOTS.ordinal()] = 244;
        }
        catch (NoSuchFieldError var2_206) {
             
        }
        try {
            arrn[Material.LEATHER_CHESTPLATE.ordinal()] = 242;
        }
        catch (NoSuchFieldError var2_207) {
             
        }
        try {
            arrn[Material.LEATHER_HELMET.ordinal()] = 241;
        }
        catch (NoSuchFieldError var2_208) {
             
        }
        try {
            arrn[Material.LEATHER_LEGGINGS.ordinal()] = 243;
        }
        catch (NoSuchFieldError var2_209) {
             
        }
        try {
            arrn[Material.LEAVES.ordinal()] = 19;
        }
        catch (NoSuchFieldError var2_210) {
             
        }
        try {
            arrn[Material.LEAVES_2.ordinal()] = 162;
        }
        catch (NoSuchFieldError var2_211) {
             
        }
        try {
            arrn[Material.LEVER.ordinal()] = 70;
        }
        catch (NoSuchFieldError var2_212) {
             
        }
        try {
            arrn[Material.LOG.ordinal()] = 18;
        }
        catch (NoSuchFieldError var2_213) {
             
        }
        try {
            arrn[Material.LOG_2.ordinal()] = 163;
        }
        catch (NoSuchFieldError var2_214) {
             
        }
        try {
            arrn[Material.LONG_GRASS.ordinal()] = 32;
        }
        catch (NoSuchFieldError var2_215) {
             
        }
        try {
            arrn[Material.MAGMA_CREAM.ordinal()] = 321;
        }
        catch (NoSuchFieldError var2_216) {
             
        }
        try {
            arrn[Material.MAP.ordinal()] = 301;
        }
        catch (NoSuchFieldError var2_217) {
             
        }
        try {
            arrn[Material.MELON.ordinal()] = 303;
        }
        catch (NoSuchFieldError var2_218) {
             
        }
        try {
            arrn[Material.MELON_BLOCK.ordinal()] = 104;
        }
        catch (NoSuchFieldError var2_219) {
             
        }
        try {
            arrn[Material.MELON_SEEDS.ordinal()] = 305;
        }
        catch (NoSuchFieldError var2_220) {
             
        }
        try {
            arrn[Material.MELON_STEM.ordinal()] = 106;
        }
        catch (NoSuchFieldError var2_221) {
             
        }
        try {
            arrn[Material.MILK_BUCKET.ordinal()] = 278;
        }
        catch (NoSuchFieldError var2_222) {
             
        }
        try {
            arrn[Material.MINECART.ordinal()] = 271;
        }
        catch (NoSuchFieldError var2_223) {
             
        }
        try {
            arrn[Material.MOB_SPAWNER.ordinal()] = 53;
        }
        catch (NoSuchFieldError var2_224) {
             
        }
        try {
            arrn[Material.MONSTER_EGG.ordinal()] = 326;
        }
        catch (NoSuchFieldError var2_225) {
             
        }
        try {
            arrn[Material.MONSTER_EGGS.ordinal()] = 98;
        }
        catch (NoSuchFieldError var2_226) {
             
        }
        try {
            arrn[Material.MOSSY_COBBLESTONE.ordinal()] = 49;
        }
        catch (NoSuchFieldError var2_227) {
             
        }
        try {
            arrn[Material.MUSHROOM_SOUP.ordinal()] = 225;
        }
        catch (NoSuchFieldError var2_228) {
             
        }
        try {
            arrn[Material.MUTTON.ordinal()] = 366;
        }
        catch (NoSuchFieldError var2_229) {
             
        }
        try {
            arrn[Material.MYCEL.ordinal()] = 111;
        }
        catch (NoSuchFieldError var2_230) {
             
        }
        try {
            arrn[Material.NAME_TAG.ordinal()] = 364;
        }
        catch (NoSuchFieldError var2_231) {
             
        }
        try {
            arrn[Material.NETHERRACK.ordinal()] = 88;
        }
        catch (NoSuchFieldError var2_232) {
             
        }
        try {
            arrn[Material.NETHER_BRICK.ordinal()] = 113;
        }
        catch (NoSuchFieldError var2_233) {
             
        }
        try {
            arrn[Material.NETHER_BRICK_ITEM.ordinal()] = 348;
        }
        catch (NoSuchFieldError var2_234) {
             
        }
        try {
            arrn[Material.NETHER_BRICK_STAIRS.ordinal()] = 115;
        }
        catch (NoSuchFieldError var2_235) {
             
        }
        try {
            arrn[Material.NETHER_FENCE.ordinal()] = 114;
        }
        catch (NoSuchFieldError var2_236) {
             
        }
        try {
            arrn[Material.NETHER_STALK.ordinal()] = 315;
        }
        catch (NoSuchFieldError var2_237) {
             
        }
        try {
            arrn[Material.NETHER_STAR.ordinal()] = 342;
        }
        catch (NoSuchFieldError var2_238) {
             
        }
        try {
            arrn[Material.NETHER_WARTS.ordinal()] = 116;
        }
        catch (NoSuchFieldError var2_239) {
             
        }
        try {
            arrn[Material.NOTE_BLOCK.ordinal()] = 26;
        }
        catch (NoSuchFieldError var2_240) {
             
        }
        try {
            arrn[Material.OBSIDIAN.ordinal()] = 50;
        }
        catch (NoSuchFieldError var2_241) {
             
        }
        try {
            arrn[Material.PACKED_ICE.ordinal()] = 175;
        }
        catch (NoSuchFieldError var2_242) {
             
        }
        try {
            arrn[Material.PAINTING.ordinal()] = 264;
        }
        catch (NoSuchFieldError var2_243) {
             
        }
        try {
            arrn[Material.PAPER.ordinal()] = 282;
        }
        catch (NoSuchFieldError var2_244) {
             
        }
        try {
            arrn[Material.PISTON_BASE.ordinal()] = 34;
        }
        catch (NoSuchFieldError var2_245) {
             
        }
        try {
            arrn[Material.PISTON_EXTENSION.ordinal()] = 35;
        }
        catch (NoSuchFieldError var2_246) {
             
        }
        try {
            arrn[Material.PISTON_MOVING_PIECE.ordinal()] = 37;
        }
        catch (NoSuchFieldError var2_247) {
             
        }
        try {
            arrn[Material.PISTON_STICKY_BASE.ordinal()] = 30;
        }
        catch (NoSuchFieldError var2_248) {
             
        }
        try {
            arrn[Material.POISONOUS_POTATO.ordinal()] = 337;
        }
        catch (NoSuchFieldError var2_249) {
             
        }
        try {
            arrn[Material.PORK.ordinal()] = 262;
        }
        catch (NoSuchFieldError var2_250) {
             
        }
        try {
            arrn[Material.PORTAL.ordinal()] = 91;
        }
        catch (NoSuchFieldError var2_251) {
             
        }
        try {
            arrn[Material.POTATO.ordinal()] = 143;
        }
        catch (NoSuchFieldError var2_252) {
             
        }
        try {
            arrn[Material.POTATO_ITEM.ordinal()] = 335;
        }
        catch (NoSuchFieldError var2_253) {
             
        }
        try {
            arrn[Material.POTION.ordinal()] = 316;
        }
        catch (NoSuchFieldError var2_254) {
             
        }
        try {
            arrn[Material.POWERED_MINECART.ordinal()] = 286;
        }
        catch (NoSuchFieldError var2_255) {
             
        }
        try {
            arrn[Material.POWERED_RAIL.ordinal()] = 28;
        }
        catch (NoSuchFieldError var2_256) {
             
        }
        try {
            arrn[Material.PRISMARINE.ordinal()] = 169;
        }
        catch (NoSuchFieldError var2_257) {
             
        }
        try {
            arrn[Material.PRISMARINE_CRYSTALS.ordinal()] = 353;
        }
        catch (NoSuchFieldError var2_258) {
             
        }
        try {
            arrn[Material.PRISMARINE_SHARD.ordinal()] = 352;
        }
        catch (NoSuchFieldError var2_259) {
             
        }
        try {
            arrn[Material.PUMPKIN.ordinal()] = 87;
        }
        catch (NoSuchFieldError var2_260) {
             
        }
        try {
            arrn[Material.PUMPKIN_PIE.ordinal()] = 343;
        }
        catch (NoSuchFieldError var2_261) {
             
        }
        try {
            arrn[Material.PUMPKIN_SEEDS.ordinal()] = 304;
        }
        catch (NoSuchFieldError var2_262) {
             
        }
        try {
            arrn[Material.PUMPKIN_STEM.ordinal()] = 105;
        }
        catch (NoSuchFieldError var2_263) {
             
        }
        try {
            arrn[Material.QUARTZ.ordinal()] = 349;
        }
        catch (NoSuchFieldError var2_264) {
             
        }
        try {
            arrn[Material.QUARTZ_BLOCK.ordinal()] = 156;
        }
        catch (NoSuchFieldError var2_265) {
             
        }
        try {
            arrn[Material.QUARTZ_ORE.ordinal()] = 154;
        }
        catch (NoSuchFieldError var2_266) {
             
        }
        try {
            arrn[Material.QUARTZ_STAIRS.ordinal()] = 157;
        }
        catch (NoSuchFieldError var2_267) {
             
        }
        try {
            arrn[Material.RABBIT.ordinal()] = 354;
        }
        catch (NoSuchFieldError var2_268) {
             
        }
        try {
            arrn[Material.RABBIT_FOOT.ordinal()] = 357;
        }
        catch (NoSuchFieldError var2_269) {
             
        }
        try {
            arrn[Material.RABBIT_HIDE.ordinal()] = 358;
        }
        catch (NoSuchFieldError var2_270) {
             
        }
        try {
            arrn[Material.RABBIT_STEW.ordinal()] = 356;
        }
        catch (NoSuchFieldError var2_271) {
             
        }
        try {
            arrn[Material.RAILS.ordinal()] = 67;
        }
        catch (NoSuchFieldError var2_272) {
             
        }
        try {
            arrn[Material.RAW_BEEF.ordinal()] = 306;
        }
        catch (NoSuchFieldError var2_273) {
             
        }
        try {
            arrn[Material.RAW_CHICKEN.ordinal()] = 308;
        }
        catch (NoSuchFieldError var2_274) {
             
        }
        try {
            arrn[Material.RAW_FISH.ordinal()] = 292;
        }
        catch (NoSuchFieldError var2_275) {
             
        }
        try {
            arrn[Material.RECORD_10.ordinal()] = 383;
        }
        catch (NoSuchFieldError var2_276) {
             
        }
        try {
            arrn[Material.RECORD_11.ordinal()] = 384;
        }
        catch (NoSuchFieldError var2_277) {
             
        }
        try {
            arrn[Material.RECORD_12.ordinal()] = 385;
        }
        catch (NoSuchFieldError var2_278) {
             
        }
        try {
            arrn[Material.RECORD_3.ordinal()] = 376;
        }
        catch (NoSuchFieldError var2_279) {
             
        }
        try {
            arrn[Material.RECORD_4.ordinal()] = 377;
        }
        catch (NoSuchFieldError var2_280) {
             
        }
        try {
            arrn[Material.RECORD_5.ordinal()] = 378;
        }
        catch (NoSuchFieldError var2_281) {
             
        }
        try {
            arrn[Material.RECORD_6.ordinal()] = 379;
        }
        catch (NoSuchFieldError var2_282) {
             
        }
        try {
            arrn[Material.RECORD_7.ordinal()] = 380;
        }
        catch (NoSuchFieldError var2_283) {
             
        }
        try {
            arrn[Material.RECORD_8.ordinal()] = 381;
        }
        catch (NoSuchFieldError var2_284) {
             
        }
        try {
            arrn[Material.RECORD_9.ordinal()] = 382;
        }
        catch (NoSuchFieldError var2_285) {
             
        }
        try {
            arrn[Material.REDSTONE.ordinal()] = 274;
        }
        catch (NoSuchFieldError var2_286) {
             
        }
        try {
            arrn[Material.REDSTONE_BLOCK.ordinal()] = 153;
        }
        catch (NoSuchFieldError var2_287) {
             
        }
        try {
            arrn[Material.REDSTONE_COMPARATOR.ordinal()] = 347;
        }
        catch (NoSuchFieldError var2_288) {
             
        }
        try {
            arrn[Material.REDSTONE_COMPARATOR_OFF.ordinal()] = 150;
        }
        catch (NoSuchFieldError var2_289) {
             
        }
        try {
            arrn[Material.REDSTONE_COMPARATOR_ON.ordinal()] = 151;
        }
        catch (NoSuchFieldError var2_290) {
             
        }
        try {
            arrn[Material.REDSTONE_LAMP_OFF.ordinal()] = 124;
        }
        catch (NoSuchFieldError var2_291) {
             
        }
        try {
            arrn[Material.REDSTONE_LAMP_ON.ordinal()] = 125;
        }
        catch (NoSuchFieldError var2_292) {
             
        }
        try {
            arrn[Material.REDSTONE_ORE.ordinal()] = 74;
        }
        catch (NoSuchFieldError var2_293) {
             
        }
        try {
            arrn[Material.REDSTONE_TORCH_OFF.ordinal()] = 76;
        }
        catch (NoSuchFieldError var2_294) {
             
        }
        try {
            arrn[Material.REDSTONE_TORCH_ON.ordinal()] = 77;
        }
        catch (NoSuchFieldError var2_295) {
             
        }
        try {
            arrn[Material.REDSTONE_WIRE.ordinal()] = 56;
        }
        catch (NoSuchFieldError var2_296) {
             
        }
        try {
            arrn[Material.RED_MUSHROOM.ordinal()] = 41;
        }
        catch (NoSuchFieldError var2_297) {
             
        }
        try {
            arrn[Material.RED_ROSE.ordinal()] = 39;
        }
        catch (NoSuchFieldError var2_298) {
             
        }
        try {
            arrn[Material.RED_SANDSTONE.ordinal()] = 180;
        }
        catch (NoSuchFieldError var2_299) {
             
        }
        try {
            arrn[Material.RED_SANDSTONE_STAIRS.ordinal()] = 181;
        }
        catch (NoSuchFieldError var2_300) {
             
        }
        try {
            arrn[Material.ROTTEN_FLESH.ordinal()] = 310;
        }
        catch (NoSuchFieldError var2_301) {
             
        }
        try {
            arrn[Material.SADDLE.ordinal()] = 272;
        }
        catch (NoSuchFieldError var2_302) {
             
        }
        try {
            arrn[Material.SAND.ordinal()] = 13;
        }
        catch (NoSuchFieldError var2_303) {
             
        }
        try {
            arrn[Material.SANDSTONE.ordinal()] = 25;
        }
        catch (NoSuchFieldError var2_304) {
             
        }
        try {
            arrn[Material.SANDSTONE_STAIRS.ordinal()] = 129;
        }
        catch (NoSuchFieldError var2_305) {
             
        }
        try {
            arrn[Material.SAPLING.ordinal()] = 7;
        }
        catch (NoSuchFieldError var2_306) {
             
        }
        try {
            arrn[Material.SEA_LANTERN.ordinal()] = 170;
        }
        catch (NoSuchFieldError var2_307) {
             
        }
        try {
            arrn[Material.SEEDS.ordinal()] = 238;
        }
        catch (NoSuchFieldError var2_308) {
             
        }
        try {
            arrn[Material.SHEARS.ordinal()] = 302;
        }
        catch (NoSuchFieldError var2_309) {
             
        }
        try {
            arrn[Material.SIGN.ordinal()] = 266;
        }
        catch (NoSuchFieldError var2_310) {
             
        }
        try {
            arrn[Material.SIGN_POST.ordinal()] = 64;
        }
        catch (NoSuchFieldError var2_311) {
             
        }
        try {
            arrn[Material.SKULL.ordinal()] = 145;
        }
        catch (NoSuchFieldError var2_312) {
             
        }
        try {
            arrn[Material.SKULL_ITEM.ordinal()] = 340;
        }
        catch (NoSuchFieldError var2_313) {
             
        }
        try {
            arrn[Material.SLIME_BALL.ordinal()] = 284;
        }
        catch (NoSuchFieldError var2_314) {
             
        }
        try {
            arrn[Material.SLIME_BLOCK.ordinal()] = 166;
        }
        catch (NoSuchFieldError var2_315) {
             
        }
        try {
            arrn[Material.SMOOTH_BRICK.ordinal()] = 99;
        }
        catch (NoSuchFieldError var2_316) {
             
        }
        try {
            arrn[Material.SMOOTH_STAIRS.ordinal()] = 110;
        }
        catch (NoSuchFieldError var2_317) {
             
        }
        try {
            arrn[Material.SNOW.ordinal()] = 79;
        }
        catch (NoSuchFieldError var2_318) {
             
        }
        try {
            arrn[Material.SNOW_BALL.ordinal()] = 275;
        }
        catch (NoSuchFieldError var2_319) {
             
        }
        try {
            arrn[Material.SNOW_BLOCK.ordinal()] = 81;
        }
        catch (NoSuchFieldError var2_320) {
             
        }
        try {
            arrn[Material.SOIL.ordinal()] = 61;
        }
        catch (NoSuchFieldError var2_321) {
             
        }
        try {
            arrn[Material.SOUL_SAND.ordinal()] = 89;
        }
        catch (NoSuchFieldError var2_322) {
             
        }
        try {
            arrn[Material.SPECKLED_MELON.ordinal()] = 325;
        }
        catch (NoSuchFieldError var2_323) {
             
        }
        try {
            arrn[Material.SPIDER_EYE.ordinal()] = 318;
        }
        catch (NoSuchFieldError var2_324) {
             
        }
        try {
            arrn[Material.SPONGE.ordinal()] = 20;
        }
        catch (NoSuchFieldError var2_325) {
             
        }
        try {
            arrn[Material.SPRUCE_DOOR.ordinal()] = 194;
        }
        catch (NoSuchFieldError var2_326) {
             
        }
        try {
            arrn[Material.SPRUCE_DOOR_ITEM.ordinal()] = 369;
        }
        catch (NoSuchFieldError var2_327) {
             
        }
        try {
            arrn[Material.SPRUCE_FENCE.ordinal()] = 189;
        }
        catch (NoSuchFieldError var2_328) {
             
        }
        try {
            arrn[Material.SPRUCE_FENCE_GATE.ordinal()] = 184;
        }
        catch (NoSuchFieldError var2_329) {
             
        }
        try {
            arrn[Material.SPRUCE_WOOD_STAIRS.ordinal()] = 135;
        }
        catch (NoSuchFieldError var2_330) {
             
        }
        try {
            arrn[Material.STAINED_CLAY.ordinal()] = 160;
        }
        catch (NoSuchFieldError var2_331) {
             
        }
        try {
            arrn[Material.STAINED_GLASS.ordinal()] = 96;
        }
        catch (NoSuchFieldError var2_332) {
             
        }
        try {
            arrn[Material.STAINED_GLASS_PANE.ordinal()] = 161;
        }
        catch (NoSuchFieldError var2_333) {
             
        }
        try {
            arrn[Material.STANDING_BANNER.ordinal()] = 177;
        }
        catch (NoSuchFieldError var2_334) {
             
        }
        try {
            arrn[Material.STATIONARY_LAVA.ordinal()] = 12;
        }
        catch (NoSuchFieldError var2_335) {
             
        }
        try {
            arrn[Material.STATIONARY_WATER.ordinal()] = 10;
        }
        catch (NoSuchFieldError var2_336) {
             
        }
        try {
            arrn[Material.STEP.ordinal()] = 45;
        }
        catch (NoSuchFieldError var2_337) {
             
        }
        try {
            arrn[Material.STICK.ordinal()] = 223;
        }
        catch (NoSuchFieldError var2_338) {
             
        }
        try {
            arrn[Material.STONE.ordinal()] = 2;
        }
        catch (NoSuchFieldError var2_339) {
             
        }
        try {
            arrn[Material.STONE_AXE.ordinal()] = 218;
        }
        catch (NoSuchFieldError var2_340) {
             
        }
        try {
            arrn[Material.STONE_BUTTON.ordinal()] = 78;
        }
        catch (NoSuchFieldError var2_341) {
             
        }
        try {
            arrn[Material.STONE_HOE.ordinal()] = 234;
        }
        catch (NoSuchFieldError var2_342) {
             
        }
        try {
            arrn[Material.STONE_PICKAXE.ordinal()] = 217;
        }
        catch (NoSuchFieldError var2_343) {
             
        }
        try {
            arrn[Material.STONE_PLATE.ordinal()] = 71;
        }
        catch (NoSuchFieldError var2_344) {
             
        }
        try {
            arrn[Material.STONE_SLAB2.ordinal()] = 183;
        }
        catch (NoSuchFieldError var2_345) {
             
        }
        try {
            arrn[Material.STONE_SPADE.ordinal()] = 216;
        }
        catch (NoSuchFieldError var2_346) {
             
        }
        try {
            arrn[Material.STONE_SWORD.ordinal()] = 215;
        }
        catch (NoSuchFieldError var2_347) {
             
        }
        try {
            arrn[Material.STORAGE_MINECART.ordinal()] = 285;
        }
        catch (NoSuchFieldError var2_348) {
             
        }
        try {
            arrn[Material.STRING.ordinal()] = 230;
        }
        catch (NoSuchFieldError var2_349) {
             
        }
        try {
            arrn[Material.SUGAR.ordinal()] = 296;
        }
        catch (NoSuchFieldError var2_350) {
             
        }
        try {
            arrn[Material.SUGAR_CANE.ordinal()] = 281;
        }
        catch (NoSuchFieldError var2_351) {
             
        }
        try {
            arrn[Material.SUGAR_CANE_BLOCK.ordinal()] = 84;
        }
        catch (NoSuchFieldError var2_352) {
             
        }
        try {
            arrn[Material.SULPHUR.ordinal()] = 232;
        }
        catch (NoSuchFieldError var2_353) {
             
        }
        try {
            arrn[Material.THIN_GLASS.ordinal()] = 103;
        }
        catch (NoSuchFieldError var2_354) {
             
        }
        try {
            arrn[Material.TNT.ordinal()] = 47;
        }
        catch (NoSuchFieldError var2_355) {
             
        }
        try {
            arrn[Material.TORCH.ordinal()] = 51;
        }
        catch (NoSuchFieldError var2_356) {
             
        }
        try {
            arrn[Material.TRAPPED_CHEST.ordinal()] = 147;
        }
        catch (NoSuchFieldError var2_357) {
             
        }
        try {
            arrn[Material.TRAP_DOOR.ordinal()] = 97;
        }
        catch (NoSuchFieldError var2_358) {
             
        }
        try {
            arrn[Material.TRIPWIRE.ordinal()] = 133;
        }
        catch (NoSuchFieldError var2_359) {
             
        }
        try {
            arrn[Material.TRIPWIRE_HOOK.ordinal()] = 132;
        }
        catch (NoSuchFieldError var2_360) {
             
        }
        try {
            arrn[Material.VINE.ordinal()] = 107;
        }
        catch (NoSuchFieldError var2_361) {
             
        }
        try {
            arrn[Material.WALL_BANNER.ordinal()] = 178;
        }
        catch (NoSuchFieldError var2_362) {
             
        }
        try {
            arrn[Material.WALL_SIGN.ordinal()] = 69;
        }
        catch (NoSuchFieldError var2_363) {
             
        }
        try {
            arrn[Material.WATCH.ordinal()] = 290;
        }
        catch (NoSuchFieldError var2_364) {
             
        }
        try {
            arrn[Material.WATER.ordinal()] = 9;
        }
        catch (NoSuchFieldError var2_365) {
             
        }
        try {
            arrn[Material.WATER_BUCKET.ordinal()] = 269;
        }
        catch (NoSuchFieldError var2_366) {
             
        }
        try {
            arrn[Material.WATER_LILY.ordinal()] = 112;
        }
        catch (NoSuchFieldError var2_367) {
             
        }
        try {
            arrn[Material.WEB.ordinal()] = 31;
        }
        catch (NoSuchFieldError var2_368) {
             
        }
        try {
            arrn[Material.WHEAT.ordinal()] = 239;
        }
        catch (NoSuchFieldError var2_369) {
             
        }
        try {
            arrn[Material.WOOD.ordinal()] = 6;
        }
        catch (NoSuchFieldError var2_370) {
             
        }
        try {
            arrn[Material.WOODEN_DOOR.ordinal()] = 65;
        }
        catch (NoSuchFieldError var2_371) {
             
        }
        try {
            arrn[Material.WOOD_AXE.ordinal()] = 214;
        }
        catch (NoSuchFieldError var2_372) {
             
        }
        try {
            arrn[Material.WOOD_BUTTON.ordinal()] = 144;
        }
        catch (NoSuchFieldError var2_373) {
             
        }
        try {
            arrn[Material.WOOD_DOOR.ordinal()] = 267;
        }
        catch (NoSuchFieldError var2_374) {
             
        }
        try {
            arrn[Material.WOOD_DOUBLE_STEP.ordinal()] = 126;
        }
        catch (NoSuchFieldError var2_375) {
             
        }
        try {
            arrn[Material.WOOD_HOE.ordinal()] = 233;
        }
        catch (NoSuchFieldError var2_376) {
             
        }
        try {
            arrn[Material.WOOD_PICKAXE.ordinal()] = 213;
        }
        catch (NoSuchFieldError var2_377) {
             
        }
        try {
            arrn[Material.WOOD_PLATE.ordinal()] = 73;
        }
        catch (NoSuchFieldError var2_378) {
             
        }
        try {
            arrn[Material.WOOD_SPADE.ordinal()] = 212;
        }
        catch (NoSuchFieldError var2_379) {
             
        }
        try {
            arrn[Material.WOOD_STAIRS.ordinal()] = 54;
        }
        catch (NoSuchFieldError var2_380) {
             
        }
        try {
            arrn[Material.WOOD_STEP.ordinal()] = 127;
        }
        catch (NoSuchFieldError var2_381) {
             
        }
        try {
            arrn[Material.WOOD_SWORD.ordinal()] = 211;
        }
        catch (NoSuchFieldError var2_382) {
             
        }
        try {
            arrn[Material.WOOL.ordinal()] = 36;
        }
        catch (NoSuchFieldError var2_383) {
             
        }
        try {
            arrn[Material.WORKBENCH.ordinal()] = 59;
        }
        catch (NoSuchFieldError var2_384) {
             
        }
        try {
            arrn[Material.WRITTEN_BOOK.ordinal()] = 330;
        }
        catch (NoSuchFieldError var2_385) {
             
        }
        try {
            arrn[Material.YELLOW_FLOWER.ordinal()] = 38;
        }
        catch (NoSuchFieldError var2_386) {
             
        }
        $SWITCH_TABLE$org$bukkit$Material = arrn;
        return $SWITCH_TABLE$org$bukkit$Material;
    }
}
