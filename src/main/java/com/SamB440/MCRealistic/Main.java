/*MIT License

Copyright (c) 2018 Sam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/
package com.SamB440.MCRealistic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import com.SamB440.MCRealistic.listeners.BlockListener;
import com.SamB440.MCRealistic.listeners.InteractListener;
import com.SamB440.MCRealistic.listeners.JoinListener;
import com.SamB440.MCRealistic.listeners.ProjectileListener;
import com.SamB440.MCRealistic.listeners.RespawnListener;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	String c = "[MCRealistic-2] ";
	
	Logger log = Bukkit.getLogger();
	
	private ArrayList<World> worlds = new ArrayList<World>();
	private ArrayList<UUID> burn = new ArrayList<UUID>();
	
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
	@SuppressWarnings("deprecation")
	private void registerRecipes()
	{
		ItemStack medicine = new ItemStack(Material.POTION, 2);
		ItemMeta medicinemeta = medicine.getItemMeta();
		medicinemeta.setDisplayName(ChatColor.GREEN + "Medicine");
		medicinemeta.setLore(Arrays.asList(ChatColor.WHITE + "Drink to help fight your cold/disease!"));
		medicine.setItemMeta(medicinemeta);
		
		ShapedRecipe medicinecraft = new ShapedRecipe(medicine);
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
		
		ShapedRecipe chocolatemilkcraft = new ShapedRecipe(chocolatemilk);
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
	    
	    ShapedRecipe BandageRecipe = new ShapedRecipe(bandage);
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
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

            @SuppressWarnings("unlikely-arg-type")
			@Override
            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) 
                {
                	List<Block> b2 = new ArrayList<Block>();
                	b2 = getNearbyBlocks(pl.getLocation(), 10);
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
        }, 0, 600);
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
