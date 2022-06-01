package com.trsoftware.chunkbusters.listeners;

import com.trsoftware.chunkbusters.ChunkBusters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    public ChunkBusters plugin;

    public GUIListener(ChunkBusters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGUIInteract(InventoryClickEvent e) {

        if(e.getCurrentItem() == null) {
            return;
        }

        if(!e.getInventory().equals(plugin.gm.busterGUI)) {
            return;
        }

        if(e.getRawSlot() >= 27) {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        int pSlot = p.getInventory().getHeldItemSlot();
        double ecoCost = plugin.getConfig().getDouble("ecoCost");

        //TODO refactor this whole section to be less gross

        if(e.getSlot() == 11) {
            //TODO send message
            if(plugin.isVaultEnabled && ecoCost > 0.00) {
                if (ChunkBusters.econ.getBalance(p) >= ecoCost) {
                    ChunkBusters.econ.withdrawPlayer(p, ecoCost);
                } else {
                    //TODO not enough money message
                    p.closeInventory();
                    return;
                }
            }

            if(plugin.getConfig().getBoolean("blockIfOtherPlayersInChunk")) {
                if(p.getLocation().getWorld().getPlayers().size() > 0) {
                    for (Player others : p.getLocation().getWorld().getPlayers()) {
                        if(!others.getName().equals(p.getName())) {
                            if(others.getLocation().getChunk().equals(plugin.bm.playerLocation.get(p).getChunk())) {
                                plugin.sendMessage(p, plugin.pmessages.getString("playersInChunk"));
                                plugin.bm.playerLocation.remove(p);
                                p.closeInventory();
                                return;
                            }
                        }
                    }
                }
            }

            plugin.bm.useChunkBuster(p, plugin.bm.playerLocation.get(p), true);
            if(p.getInventory().getItemInMainHand().getAmount() > 1) {
                ItemStack temp = p.getInventory().getItemInMainHand();
                temp.setAmount(temp.getAmount() - 1);
                p.getInventory().setItem(pSlot, temp);
            } else {
                p.getInventory().setItem(pSlot, null);
            }
        } else if(e.getSlot() == 13) {
            //TODO send message
            if(plugin.isVaultEnabled && ecoCost > 0.00) {
                if (ChunkBusters.econ.getBalance(p) >= ecoCost) {
                    ChunkBusters.econ.withdrawPlayer(p, ecoCost);
                } else {
                    //TODO not enough money message
                    p.closeInventory();
                    return;
                }
            }

            if(plugin.getConfig().getBoolean("blockIfOtherPlayersInChunk")) {
                if(p.getLocation().getWorld().getPlayers().size() > 0) {
                    for (Player others : p.getLocation().getWorld().getPlayers()) {
                        if(!others.getName().equals(p.getName())) {
                            if(others.getLocation().getChunk().equals(plugin.bm.playerLocation.get(p).getChunk())) {
                                plugin.sendMessage(p, plugin.pmessages.getString("playersInChunk"));
                                plugin.bm.playerLocation.remove(p);
                                p.closeInventory();
                                return;
                            }
                        }
                    }
                }
            }

            plugin.bm.useChunkBuster((Player) e.getWhoClicked(), plugin.bm.playerLocation.get(e.getWhoClicked()), false);
            if(p.getInventory().getItemInMainHand().getAmount() > 1) {
                ItemStack temp = p.getInventory().getItemInMainHand();
                temp.setAmount(temp.getAmount() - 1);
                p.getInventory().setItem(pSlot, temp);
            } else {
                p.getInventory().setItem(pSlot, null);
            }
        } else if(e.getSlot() == 15) {
            //TODO send cancel message
        }

        plugin.bm.playerLocation.remove(p);
        p.closeInventory();
    }

}
