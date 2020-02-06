package me.Stefan923.SuperAnnouncer.Utils.Versions;

import me.Stefan923.SuperAnnouncer.Language.LanguageManager;
import me.Stefan923.SuperAnnouncer.SuperAnnouncer;
import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public interface VersionUtils extends MessageUtils {

    default Version getVersionObject() {
        String version = getServerVersion();

        if (version.equals("v1_8_R1")) {
            return new Version_1_8_R1();
        } else if (version.equals("v1_8_R2")) {
            return new Version_1_8_R2();
        } else if (version.equals("v1_8_R3")) {
            return new Version_1_8_R3();
        } else if (version.equals("v1_9_R1")) {
            return new Version_1_9_R1();
        } else if (version.equals("v1_9_R2")) {
            return new Version_1_9_R2();
        } else if (version.equals("v1_10_R1")) {
            return new Version_1_10_R1();
        } else if (version.equals("v1_11_R1")) {
            return new Version_1_11_R1();
        } else if (version.equals("v1_12_R1")) {
            return new Version_1_12_R1();
        } else if (version.equals("v1_13_R1")) {
            return new Version_1_13_R1();
        } else if (version.equals("v1_13_R2")) {
            return new Version_1_13_R2();
        } else if (version.equals("v1_14_R1")) {
            return new Version_1_14_R1();
        } else if (version.equals("v1_15_R1")) {
            return new Version_1_15_R1();
        }
        return null;
    }

    default String getServerVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    default void checkForUpdate(Plugin plugin, SuperAnnouncer instance) {
        String version = getLatestPluginVersion(plugin);

        LanguageManager languageManager = instance.getLanguageManager(instance.getSettingsManager().getConfig().getString("Languages.Default Language"));
        if (!version.equalsIgnoreCase(getCurrentPluginVersion()))
            sendLogger(formatAll(languageManager.getConfig().getString("Update Checker.Available").replace("%link%", "https://www.spigotmc.org/resources/72224")));
        else
            sendLogger(formatAll(languageManager.getConfig().getString("Update Checker.Not Available")));
    }

    default void checkForUpdate(Plugin plugin, Player player) {
        String version = getLatestPluginVersion(plugin);

        if (!version.equalsIgnoreCase(getCurrentPluginVersion()))
            player.sendMessage(formatAll(prepareMessageByLang(player, "Update Checker.Available").replace("%link%", "https://www.spigotmc.org/resources/72224")));
        else
            player.sendMessage(formatAll(prepareMessageByLang(player, "Update Checker.Not Available")));
    }

    default String getLatestPluginVersion(Plugin plugin) {
        String version = "";
        try {
            InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=72224").openStream();
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext())
                version = scanner.next();
        } catch (IOException exception) {
            plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
        }
        return version;
    }

    default String getCurrentPluginVersion() {
        return Bukkit.getPluginManager().getPlugin("SuperAnnouncer").getDescription().getVersion();
    }

}
