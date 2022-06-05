package com.trsoftware.chunkbusters;

import com.trsoftware.chunkbusters.commands.MainCommand;
import com.trsoftware.chunkbusters.listeners.BusterPlaceListener;
import com.trsoftware.chunkbusters.listeners.GUIListener;
import com.trsoftware.chunkbusters.managers.BusterManager;
import com.trsoftware.chunkbusters.managers.GUIManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ChunkBusters extends JavaPlugin {

    /**
     *     chunkbusters.admin.give - /cb give <player> <amount>
     *     chunkbusters.admin.help /cb help (admin version)
     *     chunkbusters.help /cb help (user version)
     *     chunkbusters.use - Use a ChunkBuster
     */

    public static Economy econ = null;
    public boolean isVaultEnabled = false;

    public File messages = new File("plugins/ChunkBusters/", "messages.yml");
    public FileConfiguration pmessages = YamlConfiguration.loadConfiguration(messages);

    public ChunkBusters plugin;

    public MainCommand mc;

    public BusterManager bm;
    public GUIManager gm;

    public BusterPlaceListener bpl;
    public GUIListener gl;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveDefaultMessages();
        reloadMessages();
        setupEconomy();
        initializeInstances();
        getCommand("chunkbusters").setExecutor(new MainCommand(this));
        bm.setBusterItem();
        bm.loadWhiteList();
        Bukkit.getPluginManager().registerEvents(new BusterPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(this), this);
        gm.createInventory();

        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            isVaultEnabled = true;
        }
        int pluginId = 15386;
        Metrics metrics = new Metrics(this, pluginId);

    }

    @Override
    public void onDisable() {
        deleteInstances();
    }

    private void initializeInstances() {
        plugin = this;
        mc = new MainCommand(this);
        bm = new BusterManager(this);
        gm = new GUIManager(this);
        bpl = new BusterPlaceListener(this);
        gl = new GUIListener(this);
    }

    private void deleteInstances() {
        plugin = null;
        mc = null;
        bm = null;
        gm = null;
        bpl = null;
        gl = null;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

    public void saveDefaultMessages() {
        try {
            saveResource("messages.yml", false);
        } catch (Exception e) {

        }
    }

    public void reloadMessages() {
        pmessages = YamlConfiguration.loadConfiguration(messages);
    }

    public void sendMessage(Player p, String s) {
        if(s.equalsIgnoreCase("")) {
            return;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public void sendMessageToConsole(CommandSender sender, String s) {
        if(s.equalsIgnoreCase("")) {
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public boolean isInteger(String string) {
        if (string == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
