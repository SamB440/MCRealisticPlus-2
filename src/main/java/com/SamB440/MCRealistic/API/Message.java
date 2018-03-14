package com.SamB440.MCRealistic.API;

import org.bukkit.entity.Player;

import com.SamB440.MCRealistic.API.data.MessageType;
import com.SamB440.MCRealistic.utils.Lang;
import com.SamB440.MCRealistic.utils.TitleManager;

public class Message {
	
	Player p;
	MessageType m;
	Lang l;
	String[] var;
	
	public Message(Player p, MessageType m, Lang l, String[] var)
	{
		this.m = m;
		this.l = l;
		this.p = p;
		this.var = var;
	}
	
	public void send()
	{
		if(m.equals(MessageType.ACTIONBAR))
		{
			TitleManager.sendActionBar(p, l.getConfigValue(var));
		} else if(m.equals(MessageType.MESSAGE)) {
			p.sendMessage(l.getConfigValue(var));
		} else if(m.equals(MessageType.TITLE)) {
			TitleManager.sendTitle(p, "", l.getConfigValue(var), 120);
		}
	}
}
