package com.trsoftware.chunkbusters;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;

public class RemovalQueue extends BukkitRunnable {

    private ChunkBusters plugin;
    private LinkedList<Block> blocks = new LinkedList<>();
    private Player p;

    public RemovalQueue(ChunkBusters plugin, Player p) {
        this.plugin = plugin;
        this.p = p;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        Chunk chunk = null;
        for (int count = 0; count < 600; count++) {
            if (!blocks.isEmpty()) {
                Block b = blocks.getFirst();
                chunk = b.getChunk();
                if (!b.getType().equals(Material.AIR) && (!plugin.bm.whiteListedBlocks.contains(b.getType().name()))) {
                    b.setType(Material.AIR);
                } else {
                    count--;
                }
                blocks.removeFirst();
            } else {
                break;
            }
        }
        if (blocks.isEmpty()) {
            plugin.bm.noWaterChunks.remove(chunk);
            cancel();
        }
    }

    public LinkedList<Block> getBlocks() { return this.blocks; }
}
