package me.Stefan923.SuperAnnouncer.Commands.Type;

import me.Stefan923.SuperAnnouncer.Commands.AbstractCommand;
import me.Stefan923.SuperAnnouncer.SuperAnnouncer;
import me.Stefan923.SuperAnnouncer.Utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandAnnouncer extends AbstractCommand implements MessageUtils {

    public CommandAnnouncer() {
        super(null, false, "superannouncer");
    }

    @Override
    protected AbstractCommand.ReturnType runCommand(SuperAnnouncer instance, CommandSender sender, String... args) {
        sender.sendMessage(formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sendCenteredMessage(sender, formatAll("&3&lSuperAnnouncer &f&lv" + instance.getDescription().getVersion()));
        sendCenteredMessage(sender, formatAll("&8&l» &fPlugin author: &bStefan923"));
        sendCenteredMessage(sender, formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&l» &fAnnounces news and tips."));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sender.sendMessage(formatAll(" "));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(SuperAnnouncer instance, CommandSender sender, String... args) {
        if (sender.hasPermission("superannouncer.admin"))
            return Arrays.asList("reload");
        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/announcer";
    }

    @Override
    public String getDescription() {
        return "Displays plugin info";
    }

}
