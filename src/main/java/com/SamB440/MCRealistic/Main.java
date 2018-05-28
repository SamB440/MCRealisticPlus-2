/*******************************************************************************
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 ******************************************************************************/
package com.SamB440.MCRealistic;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SamB440.MCRealistic.API.Message;
import com.SamB440.MCRealistic.API.data.MessageType;
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
import com.SamB440.MCRealistic.placeholders.Placeholders;
import com.SamB440.MCRealistic.tasks.StaminaTask;
import com.SamB440.MCRealistic.tasks.TorchTask;
import com.SamB440.MCRealistic.utils.ConfigWrapper;
import com.SamB440.MCRealistic.utils.Lang;
import com.SamB440.MCRealistic.utils.TitleManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import lombok.Getter;

public class Main extends JavaPlugin {
	
	@Getter private static Main instance;
	
	String c = "[MCRealistic-2] ";
	
	Logger log = Bukkit.getLogger();
	
	@Getter private ArrayList<World> worlds = new ArrayList<World>();
	@Getter ArrayList<UUID> burning = new ArrayList<UUID>();
	@Getter private ArrayList<Player> diseases = new ArrayList<Player>();
	@Getter private ArrayList<Player> colds = new ArrayList<Player>();
	
    private ConfigWrapper messagesFile;
	
	@SuppressWarnings("unused")
	public void onEnable()
	{
		instance = this;
		hookPlugins();
		
		getConfig().options().copyDefaults(true);
		createConfig();
		messagesFile = new ConfigWrapper(this, "/lang", getConfig().getString("Language.Lang") + ".yml");
        messagesFile.createNewFile("Loading lang/" + getConfig().getString("Language.Lang") + ".yml", "MCRealistic-2 Language File");
        loadMessages();
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
		for(String s : getConfig().getStringList("Server.Building.Ignored_Blocks"))
		{
			Material m = Material.valueOf(s);
			ignore.add(m);
		}
		
		BlockListener.addBlocks(ignore);
		
		Metrics metrics = new Metrics(this);
		log.info(c + "Started metrics. Opt-out using global bStats config.");
        for(String s : getConfig().getStringList("Worlds"))
        {
        	World w = Bukkit.getWorld(s);
        	worlds.add(w);
        }
	}
	
	public void onDisable()
	{
		instance = null;
	}
	
	private void loadMessages() 
	{
        Lang.setFile(messagesFile.getConfig());
        for (final Lang value : Lang.values()) 
        {
            messagesFile.getConfig().addDefault(value.getPath(),
                    value.getDefault());
        }
        messagesFile.getConfig().options().copyDefaults(true);
        messagesFile.saveConfig();
	}
	
	private void hookPlugins()
	{
		if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null) 
		{
			log.info(c + "WorldGuard found!");
		} else {
			log.info(c + "WorldGuard not found, disabling!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
		{
			log.info(c + "PlaceholderAPI found!");
			new Placeholders(this).register();
		} else {
			log.info(c + "PlaceholderAPI not found!");
		}
	}
	
	private void registerListeners()
	{
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InteractListener(), this);
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new RespawnListener(), this);
		pm.registerEvents(new ProjectileListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new MoveListener(this), this);
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
			worlds.add("world_dregora");
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
			header += "Messages.Type" + eol;
			header += "  MESSAGE, ACTIONBAR, TITLE" + eol;
			header += eol;
			getConfig().options().header(header);
			getConfig().addDefault("Worlds", worlds);
			getConfig().addDefault("Language.Lang", "en-gb");
			getConfig().addDefault("Server.Weather.WeatherAffectsPlayer", true);
			getConfig().addDefault("Server.Player.Thirst", true);
			getConfig().addDefault("Server.Stamina.Enabled", true);
			getConfig().addDefault("Server.Player.DisplayHungerMessage", true);
			getConfig().addDefault("Server.Player.DisplayCozyMessage", true);
			getConfig().addDefault("Server.Player.DisplayHurtMessage", true);
			getConfig().addDefault("Server.Player.Weight", true);
			getConfig().addDefault("Server.Player.Broken_Bones.Enabled", true);
			getConfig().addDefault("Server.Building.Realistic_Building", true);
			getConfig().addDefault("Server.Building.Ignored_Blocks", Arrays.asList(
					"TORCH", 
					"REDSTONE_TORCH_OFF", 
					"REDSTONE_TORCH_ON", 
					"SIGN", 
					"SIGN_POST", 
					"WALL_SIGN", 
					"VINE",
					"LADDER",
					"WOOD_BUTTON",
					"STONE_BUTTON",
					"FENCE",
					"WOOD_STEP",
					"WOOD_DOUBLE_STEP",
					"DOUBLE_STEP",
					"DOUBLE_STONE_SLAB2",
					"PURPUR_DOUBLE_SLAB",
					"PURPUR_SLAB",
					"STONE_SLAB2",
					"LOG",
					"LOG_2"));
			getConfig().addDefault("Server.Player.Trail.Grass_Blocks", Arrays.asList(
					"DIRT",
					"GRASS_PATH"));
			getConfig().addDefault("Server.Player.Trail.Sand_Blocks", Arrays.asList(
					"SANDSTONE"));
			getConfig().addDefault("Server.Player.Trail.Enabled", true);
			getConfig().addDefault("Server.Player.Path", true);
			getConfig().addDefault("Server.Player.Allow Fatigue", true);
			getConfig().addDefault("Server.Player.Allow Chop Down Trees With Hands", false);
			getConfig().addDefault("Server.Player.Trees have random number of drops", true);
			getConfig().addDefault("Server.Player.Allow /mystats", true);
			getConfig().addDefault("Server.Player.Allow /fatigue", true);
			getConfig().addDefault("Server.Player.Allow /thirst", true);
			getConfig().addDefault("Server.Player.Spawn with items", true);
			getConfig().addDefault("Server.Player.Allow Enchanted Arrow", true);
			getConfig().addDefault("Server.Player.Torch_Burn", true);
			/*getConfig().addDefault("Server.Messages.Not Tired", "&aI don't feel tired anymore..");
			getConfig().addDefault("Server.Messages.Too Tired", "&cI'm too tired to do that");
			getConfig().addDefault("Server.Messages.Tired", "&cI am tired...");
			getConfig().addDefault("Server.Messages.Very Tired", "&cI am very tired... I should get some sleep.");
			getConfig().addDefault("Server.Messages.No Hand Chop", "&cYou can't chop down trees with your hands!");
    		getConfig().addDefault("Server.Messages.Not Thirsty","&aI'm not thirsty anymore!");
    		getConfig().addDefault("Server.Messages.Little Thirsty", "I am a little thirsty...");
    		getConfig().addDefault("Server.Messages.Getting Thirsty", "&cI am getting thirsty...");
    		getConfig().addDefault("Server.Messages.Really Thirsty", "&c&l&nI am really thirsty.. I should drink some water.");
    		getConfig().addDefault("Server.Messages.Cozy", "&2I feel cozy..");
    		getConfig().addDefault("Server.Messages.colds", "&c&nI am colds, I should wear some clothes. (Armour)");
    		getConfig().addDefault("Server.Messages.Hurt", "&c&lI am hurt!");
    		getConfig().addDefault("Server.Messages.Hungry", "&c&lI am hungry! I should really eat something...");
    		getConfig().addDefault("Server.Messages.Should_Sleep", "&cI should sleep in that bed...");
    		getConfig().addDefault("Server.Messages.Used_Bandage", "&aYou used a bandage, your legs healed!");*/
			getConfig().addDefault("Server.Messages.Type", "MESSAGE");
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
		medicinemeta.setLore(Arrays.asList(ChatColor.WHITE + "Drink to help fight your colds/disease!"));
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
	    
	    ItemStack chocolate = getSkull("http://textures.minecraft.net/texture/a829b238f2ca2f39f2ce43e316e88e8ddfed524a5dfffefafbe8161dd9f93e9");
	    ItemMeta cm = chocolate.getItemMeta();
	    cm.setDisplayName("Chocolate");
	    chocolate.setItemMeta(cm);
	    
	    ShapedRecipe chocolateRecipe = new ShapedRecipe(new NamespacedKey(this, getDescription().getName() + "-chocolate"), chocolate);
	    chocolateRecipe.shape(
	    		" S ",
	    		" C ",
	    		" S ");
	    
	    chocolateRecipe.setIngredient('S', Material.SUGAR);
	    chocolateRecipe.setIngredient('C', Material.COCOA);
	    Bukkit.getServer().addRecipe(chocolateRecipe);
	    
	    ItemStack milkchocolate = getSkull("http://textures.minecraft.net/texture/a829b238f2ca2f39f2ce43e316e88e8ddfed524a5dfffefafbe8161dd9f93e9");
	    ItemMeta mcm = milkchocolate.getItemMeta();
	    mcm.setDisplayName("Milk Chocolate");
	    milkchocolate.setItemMeta(mcm);
	    
	    ShapedRecipe milkChocolateRecipe = new ShapedRecipe(new NamespacedKey(this, getDescription().getName() + "-milk_chocolate"), milkchocolate);
	    milkChocolateRecipe.shape(
	    		" S ",
	    		"MCM",
	    		" S ");
	    
	    milkChocolateRecipe.setIngredient('S', Material.SUGAR);
	    milkChocolateRecipe.setIngredient('C', Material.COCOA);
	    milkChocolateRecipe.setIngredient('M', Material.MILK_BUCKET);
	    Bukkit.getServer().addRecipe(milkChocolateRecipe);
	}
	
	private void startTasks()
	{
		if(getConfig().getBoolean("Server.Stamina.Enabled")) Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new StaminaTask(this), 0, 60);
		if(getConfig().getBoolean("Server.Player.Torch_Burn")) Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TorchTask(this), 0, 20);
		
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
    	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.COSY, null);
    	        		m.send();
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
    	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.HURT, null);
    	        		m.send();
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
	    	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.GETTING_THIRSTY, null);
	    	        		m.send();
    						pl.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 10, 10)));
	    				}
	    				if (CurrentThirst >= 200) 
	    				{
	    	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.REALLY_THIRSTY, null);
	    	        		m.send();
    						pl.damage(3.0);
    						pl.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 10, 10)));
    						pl.addPotionEffect((new PotionEffect(PotionEffectType.BLINDNESS, 10, 10)));
    						int CurrentFatigue = getConfig().getInt("Players.Fatigue." + pl.getUniqueId());
    						getConfig().set("Players.Fatigue." + pl.getUniqueId(), (CurrentFatigue += 20));
	    				}
	    				if (CurrentThirst != 0) continue;
	    				getConfig().set("Players.Thirst." + pl.getUniqueId(), (CurrentThirst + 100));
		        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.LITTLE_THIRSTY, null);
		        		m.send();
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
	    				if(p.hasPermission("mcr.getcolds") && diseases.contains(p) && !(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
		    			{
    						TitleManager.sendTitle(p, "", ChatColor.RED + "The disease begins to damage your body...", 200);
    						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    						p.damage(4);
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
    						p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
    						p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
		    			}
    					else if(p.hasPermission("mcr.getcolds") && colds.contains(p) && !(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
    					{
    						colds.remove(p);
    						diseases.add(p);
    						TitleManager.sendTitle(p, "", ChatColor.RED + "Your colds developed into a disease!", 200);
    						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 0));
    						p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
    						TitleManager.sendActionBar(p, ChatColor.GREEN + "" + ChatColor.BOLD + "TIP:" + ChatColor.WHITE + " Use medicine to fight the disease!");
    					}
    					else if(p.hasPermission("mcr.getcolds") && !colds.contains(p) && !(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR))) 
    					{
    						colds.add(p);
    						TitleManager.sendTitle(p, "", ChatColor.RED + "You have caught a colds!", 200);
    						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3000, 0));
    						p.damage(2);
							TitleManager.sendActionBar(p, ChatColor.GREEN + "" + ChatColor.BOLD + "TIP:" + ChatColor.WHITE + " Use medicine to fight the colds!");
    					}
		    		}
	    		}
	   		}, 0, getConfig().getInt("Server.Player.Immune_System.Interval"));
	    }
	    /*
	     * MISC
	     */
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {

            @Override
            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) 
                {
                	if(!(pl.getGameMode().equals(GameMode.CREATIVE) || pl.getGameMode().equals(GameMode.SPECTATOR)))
                	{
	                    float WeightLeggings;
	                    int CurrentFatigue;
	                    float WeightCombined;
	                    float WeightChestPlate;
	                    if (getConfig().getBoolean("Server.Player.Allow Fatigue")) 
	                    {
	                        if (getConfig().getInt("Players.Fatigue." + pl.getUniqueId()) >= 240) 
	                        {
	        	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.VERY_TIRED, null);
	        	        		m.send();
	                            pl.damage(3.0);
	                            pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1));
	                            pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 1));
	                        }
	                        if (getConfig().getInt("Players.Fatigue." + pl.getUniqueId()) >= 150 && getConfig().getInt("Players.Fatigue." + pl.getUniqueId()) <= 200) 
	                        {
	        	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.TIRED, null);
	        	        		m.send();
	                            pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 1));
	                        }
	                    }
	                    if (!getConfig().getBoolean("Server.Weather.WeatherAffectsPlayer")) continue;
	                    
	                    if (pl.getWorld().hasStorm()) 
	                    {
	                        if (pl.getInventory().getBoots() != null && pl.getInventory().getChestplate() != null) 
	                        {
	                            if (!getConfig().getBoolean("Players.BoneBroke." + pl.getUniqueId())) 
	                            {
	                                getConfig().set("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId(), 0.2);
	                                WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + pl.getUniqueId());
	                                WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + pl.getUniqueId());
	                                WeightCombined = WeightLeggings + WeightChestPlate;
	                                pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
	                                getConfig().set("Players.Iscolds." + pl.getUniqueId(), false);
	                                pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
	                            }
	                        } else if (pl.getInventory().getBoots() == null && pl.getInventory().getChestplate() == null && !getConfig().getBoolean("Players.InTorch." + pl.getUniqueId()) && !getConfig().getBoolean("Players.NearFire." + pl.getUniqueId())) {
	        	        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.COLD, null);
	        	        		m.send();
	                            CurrentFatigue = getConfig().getInt("Players.Fatigue." + pl.getUniqueId());
	                            getConfig().set("Players.Fatigue." + pl.getUniqueId(), (CurrentFatigue += 10));
	                            getConfig().set("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId(), 0.123);
	                            float WeightLeggings2 = getConfig().getInt("Players.LeggingsWeight." + pl.getUniqueId());
	                            float WeightChestPlate2 = getConfig().getInt("Players.ChestplateWeight." + pl.getUniqueId());
	                            float WeightCombined2 = WeightLeggings2 + WeightChestPlate2;
	                            pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId()) - (double)(WeightCombined2 * 0.01f)));
	                            getConfig().set("Players.Iscolds." + pl.getUniqueId(), true);
	                            pl.damage(3.0);
	                        }
	                    }
	                    if (!pl.getWorld().hasStorm() && !getConfig().getBoolean("Players.BoneBroke." + pl.getUniqueId())) 
	                    {
	                        getConfig().set("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId(), 0.2);
	                        getConfig().set("Players.Iscolds." + pl.getUniqueId(), false);
	                        WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + pl.getUniqueId());
	                        WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + pl.getUniqueId());
	                        WeightCombined = WeightLeggings + WeightChestPlate;
	                        pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
	                    }
	                    if (!getConfig().getBoolean("Players.Iscolds." + pl.getUniqueId()) && !getConfig().getBoolean("Players.BoneBroke." + pl.getUniqueId())) 
	                    {
	                        getConfig().set("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId(), 0.2);
	                        WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + pl.getUniqueId());
	                        WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + pl.getUniqueId());
	                        WeightCombined = WeightLeggings + WeightChestPlate;
	                        pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
	                    }
	                    if (!getConfig().getBoolean("Players.InTorch." + pl.getUniqueId()) || !pl.getWorld().hasStorm() || getConfig().getBoolean("Players.BoneBroke." + pl.getUniqueId())) continue;
	                    
	                    getConfig().set("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId(), 0.2);
	                    WeightLeggings = getConfig().getInt("Players.LeggingsWeight." + pl.getUniqueId());
	                    WeightChestPlate = getConfig().getInt("Players.ChestplateWeight." + pl.getUniqueId());
	                    WeightCombined = WeightLeggings + WeightChestPlate;
	                    pl.setWalkSpeed((float)(getConfig().getDouble("Players.DefaultWalkSpeed." + pl.getPlayer().getUniqueId()) - (double)(WeightCombined * 0.01f)));
		        		Message m = new Message(pl, MessageType.valueOf(getConfig().getString("Server.Messages.Type")), Lang.COSY, null);
		        		m.send();
	                    CurrentFatigue = getConfig().getInt("Players.Fatigue." + pl.getUniqueId());
	                    getConfig().set("Players.Fatigue." + pl.getUniqueId(), (--CurrentFatigue));
	                }
                }
            }
        }, 0, 400);
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
        
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        
        head.setItemMeta(headMeta);
		return head;
	}
}
