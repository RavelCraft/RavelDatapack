package com.connexal.raveldatapack.api;

import com.connexal.raveldatapack.api.managers.*;
import com.connexal.raveldatapack.api.utils.YmlConfig;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

public class RavelDatapackAPI {
    private static boolean initialized = false;

    private static final Logger logger = Logger.getLogger("Minecraft");
    private static Server server = null;
    private static Plugin plugin = null;
    private static YmlConfig config = null;

    private static RecipeManager recipeManager = null;
    private static BlockManager blockManager = null;
    private static DimensionManager dimensionManager = null;
    private static EnchantmentManager enchantmentManager = null;
    private static ItemManager itemManager = null;
    private static MapManager mapManager = null;

    public static void init(Plugin plugin, YmlConfig config, CustomAdder customAdder) {
        if (initialized) {
            throw new IllegalStateException("RavelDatapackAPI is already initialized");
        }
        initialized = true;

        RavelDatapackAPI.server = plugin.getServer();
        RavelDatapackAPI.plugin = plugin;
        RavelDatapackAPI.config = config;

        recipeManager = new RecipeManager();
        blockManager = new BlockManager();
        dimensionManager = new DimensionManager();
        enchantmentManager = new EnchantmentManager();
        itemManager = new ItemManager();
        mapManager = new MapManager();

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(blockManager, plugin);
        pluginManager.registerEvents(dimensionManager, plugin);
        pluginManager.registerEvents(enchantmentManager, plugin);
        pluginManager.registerEvents(itemManager, plugin);
        pluginManager.registerEvents(mapManager, plugin);

        customAdder.add();
        dimensionManager.createWorlds();

        config.saveConfig();
    }

    public static void delete() {
        if (!initialized) {
            throw new IllegalStateException("RavelDatapackAPI is not initialized");
        }
        initialized = false;

        recipeManager.unregisterAllRecipes();
        recipeManager = null;
        blockManager = null;
        dimensionManager = null;
        enchantmentManager.unregisterEnchantments();
        enchantmentManager = null;
        itemManager = null;
        mapManager = null;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Server getServer() {
        return server;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileConfiguration getConfig() {
        return config.getConfig();
    }

    public static void saveConfig() {
        config.saveConfig();
    }

    public static RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public static BlockManager getBlockManager() {
        return blockManager;
    }

    public static DimensionManager getDimensionManager() {
        return dimensionManager;
    }

    public static EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }

    public static ItemManager getItemManager() {
        return itemManager;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }
}
