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
        busterGUI = Bukkit.createInventory(null, plugin.getConfig().getInt("gui.size"), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.title")));

        ItemStack allLevels = new ItemStack(Material.getMaterial(plugin.getConfig().getString("gui.inventory.allLevelsButton.item")));
        ItemMeta allLevelsMeta = allLevels.getItemMeta();
        allLevelsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.inventory.allLevelsButton.name")));
        ArrayList<String> allLevelsLore = new ArrayList<>();
        for(String lore : plugin.getConfig().getStringList("gui.inventory.allLevelsButton.lore")) {
            allLevelsLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        allLevelsMeta.setLore(allLevelsLore);
        allLevelsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        allLevels.setItemMeta(allLevelsMeta);
        busterGUI.setItem(plugin.getConfig().getInt("gui.inventory.allLevelsButton.slot"), allLevels);

        ItemStack belowLevels = new ItemStack(Material.getMaterial(plugin.getConfig().getString("gui.inventory.belowLevelsButton.item")));
        ItemMeta belowLevelsMeta = belowLevels.getItemMeta();
        belowLevelsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.inventory.belowLevelsButton.name")));
        ArrayList<String> belowLevelsLore = new ArrayList<>();
        for(String lore : plugin.getConfig().getStringList("gui.inventory.belowLevelsButton.lore")) {
            belowLevelsLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        belowLevelsMeta.setLore(belowLevelsLore);
        belowLevelsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        belowLevels.setItemMeta(belowLevelsMeta);
        busterGUI.setItem(plugin.getConfig().getInt("gui.inventory.belowLevelsButton.slot"), belowLevels);

        ItemStack exit = new ItemStack(Material.getMaterial(plugin.getConfig().getString("gui.inventory.exitButton.item")));
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.inventory.exitButton.name")));
        ArrayList<String> exitLore = new ArrayList<>();
        for(String lore : plugin.getConfig().getStringList("gui.inventory.exitButton.lore")) {
            exitLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        exitMeta.setLore(exitLore);
        exitMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        exit.setItemMeta(exitMeta);
        busterGUI.setItem(plugin.getConfig().getInt("gui.inventory.exitButton.slot"), exit);

        ItemStack filler = new ItemStack(Material.getMaterial(plugin.getConfig().getString("gui.inventory.filler.item")));
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui.inventory.filler.name")));
        ArrayList<String> fillerLore = new ArrayList<>();
        for(String lore : plugin.getConfig().getStringList("gui.inventory.filler.lore")) {
            fillerLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        fillerMeta.setLore(fillerLore);
        filler.setItemMeta(fillerMeta);

        for(int i = 0; i < 27; i++) {
            if(i == 11 || i == 13 || i == 15) {
                continue;
            }
            busterGUI.setItem(i, filler);
        }
    }

}
