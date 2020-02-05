package me.Stefan923.SuperAnnouncer.Tasks;

import me.Stefan923.SuperAnnouncer.SuperAnnouncer;
import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import me.Stefan923.SuperAnnouncer.Utils.Versions.VersionUtils;
import me.Stefan923.SuperAnnouncer.Utils.Versions.*;
import me.Stefan923.SuperCore.API.SuperCoreAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AnnouncerTaskDependent implements Runnable, VersionUtils, MessageUtils {
    SuperCoreAPI superCoreAPI;

    private String language;
    private Version version;
    private List<List<String>> messages;
    private List<String> disabledWorlds;

    Integer i;

    public AnnouncerTaskDependent(final SuperAnnouncer instance, final String language, final List<String> disabledWorlds) {
        superCoreAPI = SuperCoreAPI.getInstance();

        this.language = language;
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
        List<Player> players = superCoreAPI.getPlayersByLang(language);
        if (players != null)
            for (Player player : players)
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
