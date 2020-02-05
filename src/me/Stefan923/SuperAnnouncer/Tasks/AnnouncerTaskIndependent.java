package me.Stefan923.SuperAnnouncer.Tasks;

import me.Stefan923.SuperAnnouncer.SuperAnnouncer;
import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import me.Stefan923.SuperAnnouncer.Utils.Versions.VersionUtils;
import me.Stefan923.SuperAnnouncer.Utils.Versions.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AnnouncerTaskIndependent implements Runnable, VersionUtils, MessageUtils {
    private Version version;
    private List<List<String>> messages;
    private List<String> disabledWorlds;

    Integer i;

    public AnnouncerTaskIndependent(final SuperAnnouncer instance, final String language, final List<String> disabledWorlds) {
        version = getVersionObject();
        messages = new ArrayList<>();
        FileConfiguration languageConfig = instance.getLanguageManager(language).getConfig();
        for (String key : languageConfig.getConfigurationSection("Announcements").getKeys(false)) {
            messages.add(languageConfig.getStringList("Announcements." + key));
        }
        this.disabledWorlds = disabledWorlds;

        i = 0;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
            if (!disabledWorlds.contains(player.getWorld().getName().toLowerCase())) {
                String message = prepareMessage(player, messages.get(i));
                if (!message.replace(" ", "").contains("{\"text\":\""))
                    player.sendMessage(message);
                else version.send(player, message);
            }
        if (++i >= messages.size())
            i = 0;
    }
}
