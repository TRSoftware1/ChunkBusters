package com.trsoftware.chunkbusters.listeners;

import com.trsoftware.chunkbusters.ChunkBusters;
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

        if(e.getRawSlot() >= plugin.getConfig().getInt("gui.size")) {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            return;
        }

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        int pSlot = p.getInventory().getHeldItemSlot();
        double ecoCost = plugin.getConfig().getDouble("ecoCost");

        if(e.getSlot() == plugin.getConfig().getInt("gui.inventory.allLevelsButton.slot") || e.getSlot() == plugin.getConfig().getInt("gui.inventory.belowLevelsButton.slot")) {
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

            if(p.getInventory().getItemInMainHand().getAmount() > 1) {
                ItemStack temp = p.getInventory().getItemInMainHand();
                temp.setAmount(temp.getAmount() - 1);
                p.getInventory().setItem(pSlot, temp);
            } else {
                p.getInventory().setItem(pSlot, null);
            }

            if(e.getSlot() == plugin.getConfig().getInt("gui.inventory.allLevelsButton.slot")) {
                plugin.bm.useChunkBuster(p, plugin.bm.playerLocation.get(p), true);
            } else {
                plugin.bm.useChunkBuster(p, plugin.bm.playerLocation.get(p), false);
            }

        } else if(e.getSlot() == plugin.getConfig().getInt("gui.inventory.exitButton.slot")) {
            //TODO send cancel message
        }

        plugin.bm.playerLocation.remove(p);
        p.closeInventory();
    }

}
