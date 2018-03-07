/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SamB440.MCRealistic.utils.TitleManager;
import com.SamB440.MCRealistic.commands.Fatigue;
import com.SamB440.MCRealistic.commands.MCRealistic;
import com.SamB440.MCRealistic.commands.MyStats;
import com.SamB440.MCRealistic.commands.Thirst;
import com.SamB440.MCRealistic.listeners.BlockListener;
import com.SamB440.MCRealistic.listeners.ConsumeListener;
import com.SamB440.MCRealistic.listeners.EntityListener;
import com.SamB440.MCRealistic.listeners.FoodChangeListener;
import com.SamB440.MCRealistic.listeners.InteractListener;
import com.SamB440.MCRealistic.listeners.InventoryListener;
import com.SamB440.MCRealistic.listeners.JoinListener;
import com.SamB440.MCRealistic.listeners.MoveListener;
import com.SamB440.MCRealistic.listeners.ProjectileListener;
import com.SamB440.MCRealistic.listeners.RespawnListener;
import com.SamB440.MCRealistic.metrics.Metrics;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	String c = "[MCRealistic-2] ";
	
	Logger log = Bukkit.getLogger();
	
	private ArrayList<World> worlds = new ArrayList<World>();
	private ArrayList<UUID> burn = new ArrayList<UUID>();
	private ArrayList<Player> disease = new ArrayList<Player>();
	private ArrayList<Player> cold = new ArrayList<Player>();
	
	@SuppressWarnings("unused")
	public void onEnable()
	{
		instance = this;
		if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null) 
		{
			log.info(c + "WorldGuard found!");
		} else if(Bukkit.getPluginManager().getPlugin("WorldGuard") == null) {
			log.info(c + "WorldGuard not found, disabling!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		getConfig().options().copyDefaults(true);
		createConfig();
		registerListeners();
		registerRecipes();
		registerCommands();
		startTasks();
		ArrayList<String> firstnames = new ArrayList<String>();
        firstnames.add("Mark");
        firstnames.add("Daniel");
        firstnames.add("Ilan");
        firstnames.add("Alex");
        firstnames.add("James");
        firstnames.add("John");
        firstnames.add("Robert");
        firstnames.add("Michael");
        firstnames.add("William");
        firstnames.add("Joseph");
        firstnames.add("Donald");
        firstnames.add("Steven");
        firstnames.add("Kevin");
        firstnames.add("Joe");
        firstnames.add("Carl");
        firstnames.add("Patrick");
        firstnames.add("Peter");
        firstnames.add("Justin");
        firstnames.add("Harry");
        firstnames.add("Howard");
		ArrayList<String> lastnames = new ArrayList<String>();
        lastnames.add("Hawking");
        lastnames.add("Potter");
        lastnames.add("Biber");
        lastnames.add("Duck");
        lastnames.add("Smith");
        lastnames.add("Brown");
        lastnames.add("White");
        lastnames.add("Lopez");
        lastnames.add("Lee");
        lastnames.add("Thompson");
        lastnames.add("Phillips");
        lastnames.add("Evans");
        lastnames.add("Young");
        lastnames.add("Hall");
        lastnames.add("Cooper");
        lastnames.add("Bell");
        lastnames.add("Morris");
        lastnames.add("Edwards");
        lastnames.add("Baker");
        lastnames.add("Kelly");
		RespawnListener.addNames(firstnames, lastnames);
		ArrayList<Material> ignore = new ArrayList<Material>();
		ignore.add(Material.TORCH);
		ignore.add(Material.REDSTONE_TORCH_OFF);
		ignore.add(Material.REDSTONE_TORCH_ON);
		ignore.add(Material.SIGN);
		ignore.add(Material.SIGN_POST);
		ignore.add(Material.WALL_SIGN);
		ignore.add(Material.VINE);
		ignore.add(Material.LADDER);
		ignore.add(Material.WOOD_BUTTON);
		ignore.add(Material.STONE_BUTTON);
		ignore.add(Material.FENCE);
		ignore.add(Material.WOOD_STEP);
		ignore.add(Material.WOOD_DOUBLE_STEP);
		ignore.add(Material.DOUBLE_STEP);
		ignore.add(Material.DOUBLE_STONE_SLAB2);
		ignore.add(Material.PURPUR_DOUBLE_SLAB);
		ignore.add(Material.PURPUR_SLAB);
		ignore.add(Material.STONE_SLAB2);
		ignore.add(Material.LOG);
		ignore.add(Material.LOG_2);
		BlockListener.addBlocks(ignore);
		Metrics metrics = new Metrics(this);
        for(String s : getConfig().getStringList("Worlds"))
        {
        	World w = Bukkit.getWorld(s);
        	worlds.add(w);
        }
        System.out.println(worlds);
	}
	public void onDisable()
	{
		instance = null;
	}
	public static Main getInstance()
	{
		return instance;
	}
	public ArrayList<World> getWorlds()
	{
        return worlds;
	}
	private void registerListeners()
	{
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InteractListener(), this);
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new RespawnListener(), this);
		pm.registerEvents(new ProjectileListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new ConsumeListener(), this);
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new FoodChangeListener(), this);
		pm.registerEvents(new EntityListener(), this);
	}
	private void createConfig()
	{
    	File file = new File("plugins/MCRealistic-2/config.yml");
    	if(!file.exists())
    	{
			List<String> worlds = new ArrayList<>();
			worlds.add("world");
			List<String> diseases = new ArrayList<>();
			diseases.add("a disease");
			String header;
			String eol = System.getProperty("line.separator");
			header = "MCRealistic-2 Properties:" + eol;
			header += eol;
			header += "Worlds" + eol;
			header += "  Here you define which worlds you enable." + eol;
			header += eol;
			header += "WeatherAffectsPlayer" + eol;
			header += "  This option defines whether the player should be affected by the weather. Default true." + eol;
			header += eol;
			header += "Thirst" + eol;
			header += "  This option defines whether thirst is enabled. Default true." + eol;
			header += eol;
			header += "DisplayHungerMessage" + eol;
			header += "  Whether the hunger message should be shown. Default true." + eol;
			header += eol;
			header += "DisplayCozyMessage" + eol;
			header += "  Whether the cozy message should be shown. Default true." + eol;
			header += eol;
			header += "DisplayHurtMessage" + eol;
			header += "  Whether the hurt message would be shown. Default true." + eol;
			header += eol;
			header += "Weight" + eol;
			header += "  This option defines whether the player should be affected by weight." + eol;
			header += eol;
			header += "Realistic_Building" + eol;
			header += "  This option defines whether blocks will fall." + eol;
			header += eol;
			getConfig().options().header(header);
			getConfig().addDefault("Worlds", worlds);
			getConfig().addDefault("Server.Weather.WeatherAffectsPlayer", true);
			getConfig().addDefault("Server.Player.Thirst", true);
			getConfig().addDefault("Server.Player.DisplayHungerMessage", true);
			getConfig().addDefault("Server.Player.DisplayCozyMessage", true);
			getConfig().addDefault("Server.Player.DisplayHurtMessage", true);
			getConfig().addDefault("Server.Player.Weight", true);
			getConfig().addDefault("Server.Building.Realistic_Building", true);
			getConfig().addDefault("Server.Player.Trail", true);
			getConfig().addDefault("Server.Player.Path", true);
			getConfig().addDefault("Server.Player.Allow Fatigue", true);
			getConfig().addDefault("Server.Player.Allow Chop Down Trees With Hands", false);
			getConfig().addDefault("Server.Player.Trees have random number of drops", true);
			getConfig().addDefault("Server.Player.Allow /mystats", true);
			getConfig().addDefault("Server.Player.Allow /fatigue", true);
			getConfig().addDefault("Server.Player.Allow /thirst", true);
			getConfig().addDefault("Server.Player.Spawn with items", true);
			getConfig().addDefault("Server.Player.Allow Enchanted Arrow", true);
			getConfig().addDefault("Server.Messages.Not Tired", "&aI don't feel tired anymore..");
			getConfig().addDefault("Server.Messages.Too Tired", "&cI'm too tired to do that");
			getConfig().addDefault("Server.Messages.Tired", "&cI am tired...");
			getConfig().addDefault("Server.Messages.Very Tired", "&cI am very tired... I should get some sleep.");
			getConfig().addDefault("Server.Messages.No Hand Chop", "&cYou can't chop down trees with your hands!");
    		getConfig().addDefault("Server.Messages.Not Thirsty","&aI'm not thirsty anymore!");
    		getConfig().addDefault("Server.Messages.Little Thirsty", "I am a little thirsty...");
    		getConfig().addDefault("Server.Messages.Getting Thirsty", "&cI am getting thirsty...");
    		getConfig().addDefault("Server.Messages.Really Thirsty", "&c&l&nI am really thirsty.. I should drink some water.");
    		getConfig().addDefault("Server.Messages.Cozy", "&2I feel cozy..");
    		getConfig().addDefault("Server.Messages.Cold", "&c&nI am cold, I should wear some clothes. (Armour)");
    		getConfig().addDefault("Server.Messages.Hurt", "&c&lI am hurt!");
    		getConfig().addDefault("Server.Messages.Hungry", "&c&lI am hungry! I should really eat something...");
    		getConfig().addDefault("Server.Messages.Should_Sleep", "&cI should sleep in that bed...");
    		getConfig().addDefault("Server.Messages.Used_Bandage", "&aYou used a bandage, your legs healed!");
    		getConfig().addDefault("Server.Messages.Respawn", true);
    		getConfig().addDefault("Server.Player.Thirst.Interval", 6000);
    		getConfig().addDefault("Server.Player.Thirst.Enabled", true);
    		getConfig().addDefault("Server.Player.Immune_System.Interval", 6000);
    		getConfig().addDefault("Server.Player.Immune_System.Enabled", true);
    		getConfig().addDefault("Server.Player.Immune_System.Req_Players", 2);
    		saveConfig();
    		System.out.println(c + "Created & saved config!");
    	}
	}
	private void registerCommands()
	{
		getCommand("thirst").setExecutor(new Thirst());
		getCommand("fatigue").setExecutor(new Fatigue());
		getCommand("mcrealistic").setExecutor(new MCRealistic());
		getCommand("mystats").setExecutor(new MyStats());
	}
	@SuppressWarnings("deprecation")
	private void registerRecipes()
	{
		ItemStack medicine = new ItemStack(Material.POTION, 2);
		ItemMeta medicinemeta = medicine.getItemMeta();
		medicinemeta.setDisplayName(ChatColor.GREEN + "Medicine");
		medicinemeta.setLore(Arrays.asList(ChatColor.WHITE + "Drink to help fight your cold/disease!"));
		medicine.setItemMeta(medicinemeta);
		
		ShapedRecipe medicinecraft = new ShapedRecipe(new NamespacedKey(this, getDescription().getName() + "-medicine"), medicine);
			medicinecraft.shape(
				"   ",
				" B ",
				"ASA");
			
		medicinecraft.setIngredient('B', Material.GLASS_BOTTLE);
		medicinecraft.setIngredient('A', Material.APPLE);
		medicinecraft.setIngredient('S', Material.SPIDER_EYE);
		Bukkit.getServer().addRecipe(medicinecraft);
		
		ItemStack chocolatemilk = new ItemStack(Material.MILK_BUCKET);
		ItemMeta chocolatemilkmeta = chocolatemilk.getItemMeta();
		chocolatemilkmeta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Chocolate Milk");
		chocolatemilkmeta.setLore(Arrays.asList(ChatColor.WHITE + "Drink to gain Speed II."));
		chocolatemilk.setItemMeta(chocolatemilkmeta);
		
		ShapedRecipe chocolatemilkcraft = new ShapedRecipe(new NamespacedKey(this, getDescription().getName() + "-chocolate_milk"), chocolatemilk);
			chocolatemilkcraft.shape(
			    "   ",
			    "CBC",
			    "   ");
			
		chocolatemilkcraft.setIngredient('C', Material.INK_SACK, (short) 3);
		chocolatemilkcraft.setIngredient('B', Material.BUCKET);
		Bukkit.getServer().addRecipe(chocolatemilkcraft);
		
		ItemStack bandage = InteractListener.getBandage();
		ItemMeta bm = bandage.getItemMeta();
		bm.setDisplayName(ChatColor.DARK_AQUA + "Bandage");
	    bandage.setItemMeta(bm);
	    
	    ShapedRecipe BandageRecipe = new ShapedRecipe(new NamespacedKey(this, getDescription().getName() + "-bandage"), bandage);
	    BandageRecipe.shape(
	    		"   ", 
	    		" B ", 
	    		" % ");
	    
	    BandageRecipe.setIngredient('%', Material.INK_SACK, 15);
	    BandageRecipe.setIngredient('B', Material.PAPER);
	    Bukkit.getServer().addRecipe(BandageRecipe);
	}
	private void startTasks()
	{
		/*
		 * TORCH FIRE
		 */
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            @Override
            public void run() 
            {
                for (Player p : Bukkit.getOnlinePlayers()) 
                {
                    if (!burn.contains(p.getUniqueId())) continue;
                    p.setFireTicks(20);
                }
            }
        }, 0L, 20L);
        /*
         * FATIGUE
         */
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

			@Override
            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) 
                {
                	List<Material> b2 = new ArrayList<Material>();
                	for(Block b : getNearbyBlocks(pl.getLocation(), 10))
                	{
                		b2.add(b.getType());
                	}
    				if(b2.contains(Material.FIRE) || b2.contains(Material.FURNACE) && getConfig().getBoolean("Server.Player.DisplayCozyMessage")) 
    				{
    					pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 0));
    					TitleManager.sendActionBar(pl, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Cozy")));
    					getConfig().set("Players.NearFire." + pl.getUniqueId(), true);
    				} else if(!b2.contains(Material.FIRE) || !b2.contains(Material.FURNACE) && getConfig().getBoolean("Server.Player.DisplayCozyMessage")) {
    					getConfig().set("Players.NearFire." + pl.getUniqueId(), false);
    				}
    				if (!getConfig().getBoolean("Server.Player.Allow Fatigue")) 
                    {
                        getConfig().set("Players.Fatigue." + pl.getUniqueId(), 0);
                    }
                    if (pl.getHealth() < 6.0 && getConfig().getBoolean("Server.Player.DisplayHurtMessage")) 
                    {
                        pl.setSprinting(false);
                        pl.setSneaking(true);
                        TitleManager.sendActionBar(pl, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Hurt")));
                    }
                    int radius = 4;
                    Location loc = pl.getLocation();
                    World world = loc.getWorld();
                    int x = - radius;
                    while (x < radius) 
                    {
                        int y = - radius;
                        while (y < radius) 
                        {
                            int z = - radius;
                            while (z < radius) 
                            {
                                Block block = world.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                                if (block.getType() == Material.TORCH) 
                                {
                                    getConfig().set("Players.InTorch." + pl.getUniqueId(), true);
                                    return;
                                }
                                getConfig().set("Players.InTorch." + pl.getUniqueId(), false);
                                ++z;
                            }
                            ++y;
                        }
                        ++x;
                    }
                }
            }
        }, 0L, 600L);
        /*
         * THIRST
         */
	    Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() 
	    {
	    	@Override
	        public void run() {
	    		for (Player pl : Bukkit.getOnlinePlayers()) 
	    		{
	    			if (getConfig().getBoolean("Server.Player.Thirst.Enabled") && !(pl.getGameMode().equals(GameMode.CREATIVE) || pl.getGameMode().equals(GameMode.SPECTATOR)))
	    			{
	    				int CurrentThirst = getConfig().getInt("Players.Thirst." + pl.getUniqueId());
	    				if (CurrentThirst <= 100 && CurrentThirst > 0) 
	    				{
	    					getConfig().set("Players.Thirst." + pl.getUniqueId(), (CurrentThirst + 100));
	    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Getting Thirsty")));
	    					TitleManager.sendActionBar(pl, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Getting Thirsty")));
    						pl.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 10, 10)));
	    				}
	    				if (CurrentThirst >= 200) 
	    				{
    						pl.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Really Thirsty")));
    						TitleManager.sendActionBar(pl, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Really Thirsty")));
    						pl.damage(3.0);
    						pl.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 10, 10)));
    						pl.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 10, 10)));
    						int CurrentFatigue = getConfig().getInt("Players.Fatigue." + pl.getUniqueId());
    						getConfig().set("Players.Fatigue." + pl.getUniqueId(), (CurrentFatigue += 20));
	    				}
	    				if (CurrentThirst != 0) continue;
	    				getConfig().set("Players.Thirst." + pl.getUniqueId(), (CurrentThirst + 100));
	    				pl.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Little Thirsty")));
	    				TitleManager.sendActionBar(pl, ChatColor.translateAlternateColorCodes('&', getConfig().getString("Server.Messages.Little Thirsty")));
	    			}
	    		}
	    	}
	    }, 0L, getConfig().getInt("Server.Player.Thirst.Interval"));
	    /*
	     * IMMUNE SYSTEM
	     */
	    if(getConfig().getBoolean("Server.Player.Immune_System.Enabled")) 
	    {
	    	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() 
	    	{
	    		@Override
	    		public void run() {
	    			
	    			if(Bukkit.getOnlinePlayers().size() >= getConfig().getInt("Server.Player.Immune_System.Req_Players")) 
	    			{
	    				int random = new Random().nextInt(getServer().getOnlinePlayers().size());
	    				Player p = (Player) getServer().getOnlinePlayers().toArray()[random];
	    				if(p.hasPermission("mcr.getcold") && disease.contains(p) && !(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
		    			{
    						TitleManager.sendTitle(p, "", ChatColor.RED + "The disease begins to damage your body...", 200);
    						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    						p.damage(4);
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
    						p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
    						p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
		    			}
    					else if(p.hasPermission("mcr.getcold") && cold.contains(p) && !(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
    					{
    						cold.remove(p);
    						disease.add(p);
    						TitleManager.sendTitle(p, "", ChatColor.RED + "Your cold developed into a disease!", 200);
    						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
    						p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
    						TitleManager.sendActionBar(p, ChatColor.GREEN + "" + ChatColor.BOLD + "TIP:" + ChatColor.WHITE + " Use medicine to fight the disease!");
    					}
    					else if(p.hasPermission("mcr.getcold") && !cold.contains(p) && !(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
    					{
    						cold.add(p);
    						TitleManager.sendTitle(p, "", ChatColor.RED + "You have caught a cold!", 200);
    						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3000, 0));
    						p.damage(2);
							TitleManager.sendActionBar(p, ChatColor.GREEN + "" + ChatColor.BOLD + "TIP:" + ChatColor.WHITE + " Use medicine to fight the cold!");
    					}
		    		}
	    		}
	   		}, 0, getConfig().getInt("Server.Player.Immune_System.Interval"));
	    }
	}
	public ArrayList<UUID> getBurning()
	{
		return burn;
	}
	public ArrayList<Player> getDiseases()
	{
		return disease;
	}
	public ArrayList<Player> getColds()
	{
		return cold;
	}
	private List<Block> getNearbyBlocks(Location location, int radius) 
	{
		List<Block> blocks = new ArrayList<Block>();
		for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) 
		{
			for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) 
			{
				for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) 
				{
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}
}
