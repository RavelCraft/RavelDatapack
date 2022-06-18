package com.connexal.raveldatapack;

import com.connexal.raveldatapack.commands.RavelDatapackCommand;
import com.connexal.raveldatapack.items.CustomItem;
import com.connexal.raveldatapack.listeners.DimensionListener;
import com.connexal.raveldatapack.listeners.EnchantmentListener;
import com.connexal.raveldatapack.managers.*;
import com.connexal.raveldatapack.pack.TexturePack;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Logger;

public final class RavelDatapack extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static RavelDatapack instance = null;

    private static ConfigManager configManager = null;
    private static PluginMessageManager pluginMessageManager = null;
    private static ItemManager itemManager = null;
    private static HatManager hatManager = null;
    private static EnchantmentManager enchantmentManager = null;
    private static MapManager mapManager = null;
    private static DimensionManager dimensionManager = null;

    private static boolean useResourcePack = true;
    private static boolean floodgateAPI = false;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager();

        pluginMessageManager = new PluginMessageManager();
        itemManager = new ItemManager();
        hatManager = new HatManager();
        enchantmentManager = new EnchantmentManager();
        mapManager = new MapManager();
        dimensionManager = new DimensionManager();

        if (this.getServer().getPluginManager().getPlugin("floodgate") != null) {
            if (FloodgateApi.getInstance() != null) {
                floodgateAPI = true;
            }
        }

        this.saveDefaultConfig();

        if (this.getConfig().contains("useResourcePack")) {
            useResourcePack = this.getConfig().getBoolean("useResourcePack");
        } else {
            this.getConfig().set("useResourcePack", true);
            this.saveConfig();
        }

        log.info(String.format("[%s] Registered %d custom enchantments", getDescription().getName(), enchantmentManager.init()));
        log.info(String.format("[%s] Registered %d custom items", getDescription().getName(), itemManager.init()));
        log.info(String.format("[%s] Registered %d custom hats", getDescription().getName(), hatManager.init()));
        log.info(String.format("[%s] Registered %d custom dimenstions", getDescription().getName(), dimensionManager.init()));
        mapManager.init();

        log.info(String.format("[%s] Adding commands", getDescription().getName()));
        this.getCommand("raveldatapack").setExecutor(new RavelDatapackCommand(this));

        log.info(String.format("[%s] Initialising texture pack", getDescription().getName()));
        TexturePack.init();

        log.info(String.format("[%s] Registering events", getDescription().getName()));
        instance.getServer().getPluginManager().registerEvents(new EnchantmentListener(), this);
        instance.getServer().getPluginManager().registerEvents(new DimensionListener(), this);

        log.info(String.format("[%s] Creating custom dimension worlds", getDescription().getName()));
        dimensionManager.createWorlds();

        log.info(String.format("[%s] All done! Enabled Version %s.", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        this.getServer().getScheduler().cancelTasks(this);

        pluginMessageManager.unregister();

        int num = 0;
        for (CustomItem item : itemManager.getItems().values()) {
            this.getServer().removeRecipe(NamespacedKey.minecraft(item.getNamespaceKey()));
            num++;
        }
        log.info(String.format("[%s] Unregistered %d custom item recipes", getDescription().getName(), num));

        num = 0;
        for (CustomItem item : hatManager.getItems().values()) {
            this.getServer().removeRecipe(NamespacedKey.minecraft(item.getNamespaceKey()));
            num++;
        }
        log.info(String.format("[%s] Unregistered %d custom hat recipes", getDescription().getName(), num));

        num = 0;
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : enchantmentManager.getEnchantments()) {
                byKey.remove(enchantment.getKey());
                num++;
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : enchantmentManager.getEnchantments()) {
                byName.remove(enchantment.getName());
                num++;
            }
        } catch (Exception ignored) {
        }
        log.info(String.format("[%s] Unregistered %d custom enchantments", getDescription().getName(), num));
    }

    public static Logger getLog() {
        return log;
    }

    public static RavelDatapack getInstance() {
        return instance;
    }

    public static PluginMessageManager getPluginMessageManager() {
        return pluginMessageManager;
    }

    public static ItemManager getItemManager() {
        return itemManager;
    }

    public static HatManager getHatManager() {
        return hatManager;
    }

    public static EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }

    public static MapManager getMapManager() {
        return mapManager;
    }

    public static DimensionManager getDimensionManager() {
        return dimensionManager;
    }

    public static ConfigManager.YmlConfig getConfig(String name) {
        return configManager.getConfig(name);
    }

    public static boolean isFloodgateAPI() {
        return floodgateAPI;
    }

    public static boolean shouldResourcePack() {
        return useResourcePack;
    }

    public static long getSeed() {
        World world = instance.getServer().getWorld("world");
        if (world == null) {
            return 0;
        } else {
            return world.getSeed();
        }
    }
}
