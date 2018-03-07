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
package com.SamB440.MCRealistic.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.SamB440.MCRealistic.Main;

/*
 * DONE
 */
public class JoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent pje)
	{
        if (!pje.getPlayer().hasPlayedBefore()) 
        {
            getConfig().addDefault("Players.Thirst." + pje.getPlayer().getUniqueId(), 0);
            getConfig().addDefault("Players.RealName." + pje.getPlayer().getUniqueId(), "null");
            getConfig().addDefault("Players.IsCold." + pje.getPlayer().getUniqueId(), false);
            getConfig().addDefault("Players.InTorch." + pje.getPlayer().getUniqueId(), false);
            getConfig().addDefault("Players.Fatigue." + pje.getPlayer().getUniqueId(), 0);
            getConfig().set("Players.DefaultWalkSpeed." + pje.getPlayer().getUniqueId(), 1);
        }
	}
	private FileConfiguration getConfig()
	{
		return Main.getInstance().getConfig();
	}
}
