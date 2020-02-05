package me.Stefan923.SuperAnnouncer;

import me.Stefan923.SuperAnnouncer.Commands.CommandManager;
import me.Stefan923.SuperAnnouncer.Language.LanguageManager;
import me.Stefan923.SuperAnnouncer.Listeners.PlayerJoinListener;
import me.Stefan923.SuperAnnouncer.Settings.SettingsManager;
import me.Stefan923.SuperAnnouncer.Tasks.AnnouncerTaskDependent;
import me.Stefan923.SuperAnnouncer.Tasks.AnnouncerTaskIndependent;
import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import me.Stefan923.SuperAnnouncer.Utils.Versions.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuperAnnouncer extends JavaPlugin implements MessageUtils, VersionUtils {

    private static SuperAnnouncer instance;

    private SettingsManager settingsManager;
    private HashMap<String, LanguageManager> languageManagers;
    private HashMap<String, BukkitTask> tasks;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;

        settingsManager = SettingsManager.getInstance();
        settingsManager.setup(this);

        languageManagers = new HashMap<>();
        for (String fileName : settingsManager.getConfig().getStringList("Languages.Available Languages")) {
            LanguageManager languageManager = new LanguageManager();
            fileName = fileName.toLowerCase();
            languageManager.setup(this, fileName);
            languageManagers.put(fileName, languageManager);
        }

        sendLogger("&8&l> &7&m------ &8&l( &3&lSuperAnnouncer &b&lby Stefan923 &8&l) &7&m------ &8&l<");
        sendLogger("&b   Plugin has been initialized.");
        sendLogger("&b   Version: &3v" + getDescription().getVersion());
        sendLogger("&b   Enabled listeners: &3" + enableListeners());
        sendLogger("&b   Enabled commands: &3" + enableCommands());
        sendLogger("&8&l> &7&m------ &8&l( &3&lSuperAnnouncer &b&lby Stefan923 &8&l) &7&m------ &8&l<");

        tasks = new HashMap<>();
        if (Bukkit.getPluginManager().getPlugin("SuperCore") != null)
            startAnnouncementsDependent();
        else startAnnouncementsIndependent();

        if (settingsManager.getConfig().getBoolean("Update Checker.Enable.On Plugin Enable"))
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
                checkForUpdate(this, this);
            });
    }

    public static SuperAnnouncer getInstance() {
        return instance;
    }

    private Integer enableListeners() {
        Integer i = 0;
        PluginManager pluginManager = getServer().getPluginManager();
        if (settingsManager.getConfig().getBoolean("Update Checker.Enable.On Join")) {
            pluginManager.registerEvents(new PlayerJoinListener(), this);
        }
        return i;
    }

    private Integer enableCommands() {
        commandManager = new CommandManager(this);
        return commandManager.getCommands().size();
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void reloadSettingManager() {
        settingsManager.reload();
    }

    public LanguageManager getLanguageManager(String language) {
        return languageManagers.get(language);
    }

    public HashMap<String, LanguageManager> getLanguageManagers() {
        return languageManagers;
    }

    public void reloadLanguageManagers() {
        languageManagers.clear();
        for (String fileName : settingsManager.getConfig().getStringList("Languages.Available Languages")) {
            LanguageManager languageManager = new LanguageManager();
            fileName = fileName.toLowerCase();
            languageManager.setup(this, fileName);
            languageManagers.put(fileName, languageManager);
        }
    }

    protected void startAnnouncementsDependent() {
        FileConfiguration settings = settingsManager.getConfig();
        int announceInterval = settings.getInt("Announcer Task.Seconds Between Announcements");
        List<String> disabledWorlds = new ArrayList<>();
        settings.getStringList("Announcer Task.Disabled Worlds").forEach(world -> disabledWorlds.add(world.toLowerCase()));
        for (String language : getLanguageManagers().keySet()) {
            if (!tasks.containsKey(language)) {
                tasks.put(language, getServer().getScheduler().runTaskTimerAsynchronously(this, new AnnouncerTaskDependent(this, language, disabledWorlds), 20L * announceInterval, 20L * announceInterval));
            } else {
                tasks.get(language).cancel();
                tasks.replace(language, getServer().getScheduler().runTaskTimerAsynchronously(this, new AnnouncerTaskDependent(this, language, disabledWorlds), 20L * announceInterval, 20L * announceInterval));
            }
        }
    }

    protected void startAnnouncementsIndependent() {
        FileConfiguration settings = settingsManager.getConfig();
        int announceInterval = settings.getInt("Announcer Task.Seconds Between Announcements");
        List<String> disabledWorlds = new ArrayList<>();
        settings.getStringList("Announcer Task.Disabled Worlds").forEach(world -> disabledWorlds.add(world.toLowerCase()));

        String language = settings.getString("Languages.Default Language");

        if (!tasks.containsKey(language)) {
            tasks.put(language, getServer().getScheduler().runTaskTimerAsynchronously(this, new AnnouncerTaskIndependent(this, language, disabledWorlds), 20L * announceInterval, 20L * announceInterval));
        } else {
            tasks.get(language).cancel();
            tasks.replace(language, getServer().getScheduler().runTaskTimerAsynchronously(this, new AnnouncerTaskIndependent(this, language, disabledWorlds), 20L * announceInterval, 20L * announceInterval));
        }
    }

    protected void stopAnnouncements() {
        for (String language : getLanguageManagers().keySet())
            if (tasks.containsKey(language)) {
                tasks.get(language).cancel();
                tasks.remove(language);
            }
    }

    @Override
    public void onDisable() {
        stopAnnouncements();
    }

}
