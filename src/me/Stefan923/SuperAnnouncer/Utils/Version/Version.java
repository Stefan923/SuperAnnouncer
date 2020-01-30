package me.Stefan923.SuperAnnouncer.Utils.Version;

import org.bukkit.entity.Player;

import java.util.List;

abstract class Version {

    public abstract void send(final Player player, final String jsonText);

    public abstract void send(final Player player, final List<String> jsonText);

}
