package com.trsoftware.chunkbusters.managers;

import com.trsoftware.chunkbusters.ChunkBusters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIManager {

    public ChunkBusters plugin;

    public GUIManager(ChunkBusters plugin) {
        this.plugin = plugin;
    }

    public Inventory busterGUI = null;

    public void createInventory() {
        busterGUI = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.title")));

        ItemStack allLevels = new ItemStack(Material.FEATHER);
        ItemMeta allLevelsMeta = allLevels.getItemMeta();
        allLevelsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bBust All Levels"));
        ArrayList<String> allLevelsLore = new ArrayList<>();
        allLevelsLore.add(ChatColor.translateAlternateColorCodes('&',"&7Click here to destroy ALL"));
        allLevelsLore.add(ChatColor.translateAlternateColorCodes('&',"&7y-levels in this chunk."));
        allLevelsMeta.setLore(allLevelsLore);
        allLevelsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        allLevels.setItemMeta(allLevelsMeta);
        busterGUI.setItem(11, allLevels);

        ItemStack belowLevels = new ItemStack(Material.WOODEN_SHOVEL);
        ItemMeta belowLevelsMeta = belowLevels.getItemMeta();
        belowLevelsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bBust Lower Levels"));
        ArrayList<String> belowLevelsLore = new ArrayList<>();
        belowLevelsLore.add(ChatColor.translateAlternateColorCodes('&',"&7Click here to destroy blocks in this chunk, at"));
        belowLevelsLore.add(ChatColor.translateAlternateColorCodes('&',"&7the y-level this buster was placed at and below."));
        belowLevelsMeta.setLore(belowLevelsLore);
        belowLevelsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        belowLevels.setItemMeta(belowLevelsMeta);
        busterGUI.setItem(13, belowLevels);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cCancel"));
        exitMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        exit.setItemMeta(exitMeta);
        busterGUI.setItem(15, exit);

        ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        for(int i = 0; i < 27; i++) {
            if(i == 11 || i == 13 || i == 15) {
                continue;
            }
            busterGUI.setItem(i, filler);
        }
    }

}
