package com.trsoftware.chunkbusters.managers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.trsoftware.chunkbusters.ChunkBusters;
import com.trsoftware.chunkbusters.RemovalQueue;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusterManager {

    public ChunkBusters plugin;

    public BusterManager(ChunkBusters plugin) {
        this.plugin = plugin;
    }

    public HashMap<Player, Location> playerLocation = new HashMap<>();
    public ArrayList<String> whiteListedBlocks = new ArrayList<>();
    public ArrayList<Chunk> noWaterChunks = new ArrayList<>();

    public ItemStack chunkBusterItem = null;

    public void setBusterItem() {
        ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("chunkBuster.item.type")));
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("chunkBuster.item.name")));
        ArrayList<String> lore = new ArrayList<>();
        for(String itemLore : plugin.getConfig().getStringList("chunkBuster.item.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', itemLore));
        }
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(itemMeta);

        if(plugin.getConfig().getBoolean("chunkBuster.item.glow")) {
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }

        chunkBusterItem = item;
    }

    public void loadWhiteList() {
        for(String key : plugin.getConfig().getStringList("whiteListedBlocks")) {
            whiteListedBlocks.add(key);
        }
    }

    public void giveChunkBuster(CommandSender sender, String pName, int amount) {
        //TODO check player valid
        ItemStack buster = chunkBusterItem;
        buster.setAmount(amount);
        Bukkit.getPlayer(pName).getInventory().addItem(buster);
        plugin.sendMessageToConsole(sender, plugin.pmessages.getString("gaveChunkBusters")
                .replaceAll("%amount%", String.valueOf(amount))
                .replaceAll("%player%", pName));
        plugin.sendMessage(Bukkit.getPlayer(pName), plugin.pmessages.getString("receivedChunkBusters")
                .replaceAll("%amount%", String.valueOf(amount)));
    }

    public void openGUI(Player p) {
        p.openInventory(plugin.gm.busterGUI);
    }

    public void useChunkBuster(Player p, Location loc, boolean allLevels) {
        RemovalQueue removalQueue = new RemovalQueue(plugin, p);
        Chunk chunk = loc.getChunk();
        noWaterChunks.add(chunk);
        int start = loc.getWorld().getMaxHeight();
        if(!allLevels) {
            start = (int)loc.getY();
        }

        if(plugin.getConfig().getBoolean("extraWorldGuardCheck")) {
            for (int y = start; y > loc.getWorld().getMinHeight(); y--) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        Location temp = chunk.getBlock(x, y, z).getLocation();
                        if(!inRegion(temp)) {
                            removalQueue.getBlocks().add(chunk.getBlock(x, y, z));
                        }
                    }
                }
            }
        } else {
            for (int y = start; y > loc.getWorld().getMinHeight(); y--) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        removalQueue.getBlocks().add(chunk.getBlock(x, y, z));
                    }
                }
            }
        }
        removalQueue.runTaskTimer(plugin, 1L, 1L);
    }

    public boolean inRegion(Location loc) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        List<String> regions = container.get(BukkitAdapter.adapt(loc.getWorld())).getApplicableRegionsIDs(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()));

        if(regions.size() > 0) {
            return true;
        }
        return false;
    }

}
