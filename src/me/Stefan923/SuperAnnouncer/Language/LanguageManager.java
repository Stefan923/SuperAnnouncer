package me.Stefan923.SuperAnnouncer.Language;

import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LanguageManager implements MessageUtils {

    private static LanguageManager instance = new LanguageManager();
    private FileConfiguration config;
    private File cfile;
    private String languageFile;

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setup(Plugin p, String languageFile) {
        this.languageFile = languageFile;

        cfile = new File(p.getDataFolder(), "languages/" + languageFile);
        if (!cfile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                File dir = new File(p.getDataFolder() + "/languages");

                if (!dir.exists())
                    dir.mkdir();

                cfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(cfile);
        config.options().header("SuperCore by Stefan923.\n");
        config.addDefault("Language Display Name", "English");
        config.addDefault("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.addDefault("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.addDefault("Update Checker.Available", "&8(&3!&8) &fThere is a new version of &bSuperAnnouncer &favailable!\n&8(&3!&8) &fDownload link: &b%link%");
        config.addDefault("Update Checker.Not Available", "&8(&3!&8) &fThere's no update available for &bSuperAnnouncer&f.");
        config.addDefault("Announcements.1", Arrays.asList("&aThis announcement contains &ec&do&cl&bo&5r&6s&a!", "&eIt has multiple lines of text."));
        config.addDefault("Announcements.2", Arrays.asList("&bThis is the second announcement!", "&eOnline players: %server_online% (You need PlaceholderAPI)"));
        config.addDefault("Announcements.3", Arrays.asList("{\"text\": \"&eThis is a JSON message! \",\"extra\":[{\"text\":\"&bClick Here\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"&cThis &da &ahover &bmessage!\"},\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org\"}}]}"));
        config.options().copyDefaults(true);

        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reset() {
        config.set("Language Display Name", "English");
        config.set("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.set("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.set("Update Checker.Available", "&8(&3!&8) &fThere is a new version of &bSuperAnnouncer &favailable!\n&8(&3!&8) &fDownload link: &b%link%");
        config.set("Update Checker.Not Available", "&8(&3!&8) &fThere's no update available for &bSuperAnnouncer&f.");
        config.set("Announcements.1", Arrays.asList("&aThis announcement contains &ec&do&cl&bo&5r&6s&a!", "&eIt has multiple lines of text."));
        config.set("Announcements.2", Arrays.asList("&bThis is the second announcement!", "&eOnline players: %server_online% (You need PlaceholderAPI)"));
        config.set("Announcements.3", Arrays.asList("{\"text\": \"&eThis is a JSON message! \",\"extra\":[{\"text\":\"&bClick Here\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"&cThis &da &ahover &bmessage!\"},\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org\"}}]}"));

        save();
    }

    public void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            sendLogger(ChatColor.RED + "File '" + languageFile + "' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public String getLanguageFileName() {
        return languageFile;
    }

}
