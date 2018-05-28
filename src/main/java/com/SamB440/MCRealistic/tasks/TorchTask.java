package com.SamB440.MCRealistic.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.SamB440.MCRealistic.Main;

public class TorchTask implements Runnable {
	
	Main plugin;
	
	public TorchTask(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if(Bukkit.getServer().getOnlinePlayers().size() > 0)
		{
            for(Player p : Bukkit.getOnlinePlayers()) 
            {
                if (!plugin.getBurning().contains(p.getUniqueId())) continue;
                p.setFireTicks(20);
            }
		}
	}
}
