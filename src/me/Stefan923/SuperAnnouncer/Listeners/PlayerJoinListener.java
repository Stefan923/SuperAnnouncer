package me.Stefan923.SuperAnnouncer.Listeners;

import me.Stefan923.SuperAnnouncer.SuperAnnouncer;
import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import me.Stefan923.SuperAnnouncer.Utils.Versions.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener, MessageUtils, VersionUtils {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        SuperAnnouncer instance = SuperAnnouncer.getInstance();
        if (player.hasPermission("superannouncer.updatechecker"))
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                checkForUpdate(instance, player);
            });
    }

}
