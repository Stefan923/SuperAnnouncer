package me.Stefan923.SuperAnnouncer.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SettingsManager {

    private static SettingsManager instance = new SettingsManager();
    private FileConfiguration config;
    private File cfile;

    public static SettingsManager getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        cfile = new File(p.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("SuperCore by Stefan923\n");
        config.addDefault("Languages.Default Language", "lang_en.yml");
        config.addDefault("Languages.Available Languages", Arrays.asList("lang_en.yml"));
        config.addDefault("Announcer Task.Seconds Between Announcements", 90);
        config.addDefault("Announcer Task.Disabled Worlds", Arrays.asList("example_world", "example_world2"));
        config.addDefault("Update Checker.Enable.On Plugin Enable", true);
        config.addDefault("Update Checker.Enable.On Join", true);
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void resetConfig() {
        config.set("Languages.Default Language", "lang_en.yml");
        config.set("Languages.Available Languages", Arrays.asList("lang_en.yml"));
        config.set("Announcer Task.Seconds Between Announcements", 90);
        config.set("Announcer Task.Disabled Worlds", Arrays.asList("example_world", "example_world2"));
        config.set("Update Checker.Enable.On Plugin Enable", true);
        config.set("Update Checker.Enable.On Join", true);
        save();
    }

    private void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'settings.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

}
