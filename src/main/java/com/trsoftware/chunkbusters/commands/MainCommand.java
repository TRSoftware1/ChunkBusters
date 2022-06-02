package com.trsoftware.chunkbusters.commands;

import com.trsoftware.chunkbusters.ChunkBusters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    public ChunkBusters plugin;

    public MainCommand(ChunkBusters plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        switch (args.length) {
            case 3:
                if(args[0].equalsIgnoreCase("give")) {
                    if (sender.hasPermission("chunkbusters.admin.give")) {
                        if(!plugin.isInteger(args[2])) {
                            plugin.sendMessageToConsole(sender, plugin.pmessages.getString("mustBeNumber"));
                            return true;
                        }
                        plugin.bm.giveChunkBuster(sender, args[1], Integer.valueOf(args[2]));
                    } else {
                        plugin.sendMessageToConsole(sender, plugin.pmessages.getString("noPermission"));
                    }
                } else {
                    sendHelp(sender);
                }
                break;

            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        if(sender.hasPermission("chunkbusters.admin.help")) {
            for(String help : plugin.pmessages.getStringList("adminHelp")) {
                plugin.sendMessageToConsole(sender, help);
            }
        } else if(sender.hasPermission("chunkbusters.help")) {
            for(String help : plugin.pmessages.getStringList("userHelp")) {
                plugin.sendMessageToConsole(sender, help);
            }
        }
    }

}
