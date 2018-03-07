package com.SamB440.MCRealistic.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TitleManager {
    public static void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks) {
        /*IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msgTitle + "\"}"));
        IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msgSubTitle + "\"}"));
        PacketPlayOutTitle p = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle p2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)p2);
        TitleManager.sendTime(player, ticks);*/
    	if(Bukkit.getVersion().contains("1.12"))
    	{
    		player.sendTitle(msgTitle, msgSubTitle, 20, ticks, 20);
    	}
    }

    public static void sendActionBar(Player player, String message) {
    	if(Bukkit.getVersion().contains("1.12"))
    	{
    		/*IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + message + "\"}"));
    		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc);
    		((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)ppoc);*/
    		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    	}
    }
}
