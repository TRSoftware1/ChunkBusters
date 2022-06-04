package com.trsoftware.chunkbusters.listeners;

import com.massivecraft.factions.*;
import com.massivecraft.factions.perms.Role;
import com.trsoftware.chunkbusters.ChunkBusters;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BusterPlaceListener implements Listener {

    public ChunkBusters plugin;

    public BusterPlaceListener(ChunkBusters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBusterPlace(BlockPlaceEvent e) {

        if(e.getPlayer().getInventory().getItemInOffHand().isSimilar(plugin.bm.chunkBusterItem)) {
            e.setCancelled(true);
            return;
        }

        if(e.getPlayer().getInventory().getItemInMainHand().isSimilar(plugin.bm.chunkBusterItem)) {

            // Check general building rights
            if(e.isCancelled()) {
                plugin.sendMessage(e.getPlayer(), plugin.pmessages.getString("cannotBuildHere"));
                return;
            }

            // Check faction building rights
            if(Bukkit.getPluginManager().isPluginEnabled("Factions")) {
                FLocation fLoc = new FLocation(e.getBlockPlaced().getLocation());
                Faction faction = Board.getInstance().getFactionAt(fLoc);
                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());

                if((faction.isWilderness() && plugin.getConfig().getBoolean("factions.blockWilderness"))
                        || (faction.isWarZone() || faction.isSafeZone())) {
                    plugin.sendMessage(e.getPlayer(), plugin.pmessages.getString("cannotBuildHere"));
                    e.setCancelled(true);
                    return;
                }

                if(fPlayer.getTag().equals(faction.getTag())) {
                    String requiredRole = plugin.getConfig().getString("factions.minimumRoleRequired");
                    if(!fPlayer.getRole().isAtLeast(Role.fromString(requiredRole))) {
                        plugin.sendMessage(e.getPlayer(), plugin.pmessages.getString("factionsNeedHigherRank").replaceAll("%rank%", requiredRole));
                        e.setCancelled(true);
                        return;
                    }
                }

            }


            e.setCancelled(true);
            Location loc = e.getBlockPlaced().getLocation();
            Player p = e.getPlayer();

            if(!p.hasPermission("chunkbusters.use")) {
                plugin.sendMessage(p, plugin.pmessages.getString("noPermission"));
                return;
            }



            plugin.bm.playerLocation.put(p, loc);
            plugin.bm.openGUI(p);
        }
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent e) {
        if(e.getBlock().getType().equals(Material.WATER) || e.getBlock().getType().equals(Material.LAVA)) {
            if(plugin.bm.noWaterChunks.contains(e.getToBlock().getChunk()) || plugin.bm.noWaterChunks.contains(e.getBlock().getChunk())) {
                e.setCancelled(true);
            }
        }
    }

}
