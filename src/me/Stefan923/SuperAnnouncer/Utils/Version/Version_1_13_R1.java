package me.Stefan923.SuperAnnouncer.Utils.Version;

import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.*;
import net.minecraft.server.v1_13_R1.*;

import java.util.List;

public class Version_1_13_R1 extends Version {

    @Override
    public void send(final Player player, final String json) {
        try {
            final IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', json));
            final PacketPlayOutChat chat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.CHAT);
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
                    if (iChatBaseComponent != null) {
                        iChatBaseComponent.addSibling(IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', line)));
                    }
                }
            }
            final PacketPlayOutChat chat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.CHAT);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);
        } catch (Exception e) {
            System.out.println("[SuperAnnouncer] There was an error sending the following message to: " + (player != null ? player.getName() : "unknown player"));
            System.out.println("[SuperAnnouncer] Message: " + (json.toString() != null ? json.toString() : "null"));
            System.out.println("[SuperAnnouncer] Reason: " + (e.getMessage() != null ? e.getMessage() : "unknown error"));
        }
    }

}
