package me.Stefan923.SuperAnnouncer.Utils.Versions;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class Version {

    public abstract void send(final Player player, final String jsonText);

    public abstract void send(final Player player, final List<String> jsonText);

}
