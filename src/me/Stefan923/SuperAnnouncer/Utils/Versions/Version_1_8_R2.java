package me.Stefan923.SuperAnnouncer.Utils.Versions;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.*;
import net.minecraft.server.v1_8_R2.*;

import java.util.List;

public class Version_1_8_R2 extends Version {

    @Override
    public void send(final Player player, final String json) {
        try {
            final IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', json));
            final PacketPlayOutChat chat = new PacketPlayOutChat(iChatBaseComponent, (byte)0);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);
        } catch (Exception e) {
            System.out.println("[SuperAnnouncer] There was an error sending the following message to: " + (player != null ? player.getName() : "unknown player"));
            System.out.println("[SuperAnnouncer] Message: " + (json != null ? json : "null"));
            System.out.println("[SuperAnnouncer] Reason: " + (e.getMessage() != null ? e.getMessage() : "unknown error"));
        }
    }

    @Override
    public void send(final Player player, final List<String> json) {
        if (json == null || json.isEmpty()) {
            return;
        }
        final String first = json.get(0);
        try {
            final IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', first));
            for (final String line : json) {
                if (line != null && !line.isEmpty()) {
                    if (line.equals(first)) {
                        continue;
                    }
                    iChatBaseComponent.addSibling(IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', line)));
                }
            }
            final PacketPlayOutChat chat = new PacketPlayOutChat(iChatBaseComponent, (byte)0);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);
        } catch (Exception e) {
            System.out.println("[SuperAnnouncer] There was an error sending the following message to: " + (player != null ? player.getName() : "unknown player"));
            System.out.println("[SuperAnnouncer] Message: " + (json.toString() != null ? json.toString() : "null"));
            System.out.println("[SuperAnnouncer] Reason: " + (e.getMessage() != null ? e.getMessage() : "unknown error"));
        }
    }

}
