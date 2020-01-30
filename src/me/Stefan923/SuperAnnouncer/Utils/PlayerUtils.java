package me.Stefan923.SuperAnnouncer.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public interface PlayerUtils {

    default List<Player> getPlayersByPermission(String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).collect(Collectors.toList());
    }

}
