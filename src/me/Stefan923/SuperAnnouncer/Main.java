package me.Stefan923.SuperAnnouncer;

import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements MessageUtils {

    public Main instance;

    @Override
    public void onEnable() {
        instance = this;

        sendLogger("&8&l> &7&m------ &8&l( &3&lSuperAnnouncer &b&lby Stefan923 &8&l) &7&m------ &8&l<");
        sendLogger("&b   Plugin has been initialized.");
        sendLogger("&b   Version: &3v" + getDescription().getVersion());
        sendLogger("&b   Enabled listeners: &3" + enableListeners());
        sendLogger("&b   Enabled commands: &3" + enableCommands());
        sendLogger("&8&l> &7&m------ &8&l( &3&lSuperAnnouncer &b&lby Stefan923 &8&l) &7&m------ &8&l<");
    }

    private Integer enableListeners() {
        Integer i = 2;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        return i;
    }

    private Integer enableCommands() {
        commandManager = new CommandManager(this);
        return commandManager.getCommands().size();
    }

    @Override
    public void onDisable() {

    }

}
