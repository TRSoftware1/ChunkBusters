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
            case 1:
                //TODO
                break;

            case 2:
                //TODO
                break;

            case 3:
                if(args[0].equalsIgnoreCase("give")) {
                    if (sender.hasPermission("chunkbusters.give")) {
                        //TODO check last arg is int
                        plugin.bm.giveChunkBuster(sender, args[1], Integer.valueOf(args[2]));
                    } else {
                        plugin.sendMessageToConsole(sender, plugin.pmessages.getString("noPermission"));
                    }
                }
                break;

            default:
                //TODO help
                break;
        }

        return true;
    }
}
