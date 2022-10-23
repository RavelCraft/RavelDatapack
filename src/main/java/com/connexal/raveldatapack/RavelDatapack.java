package com.connexal.raveldatapack;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.utils.YmlConfig;
import com.connexal.raveldatapack.commands.RavelBiomeCommand;
import com.connexal.raveldatapack.commands.RavelDatapackCommand;
import com.connexal.raveldatapack.commands.RavelSchematicCommand;
import com.connexal.raveldatapack.managers.ConfigManager;
import com.connexal.raveldatapack.managers.PluginMessageManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class RavelDatapack extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static RavelDatapack instance = null;

    private static PluginMessageManager pluginMessageManager = null;
    private static ConfigManager configManager = null;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        pluginMessageManager = new PluginMessageManager();
        configManager = new ConfigManager();

        log.info(String.format("[%s] Registering datapack components", getDescription().getName()));
        RavelDatapackAPI.init(this, configManager.getConfig("datapack"), new CustomAdderImpl());

        log.info(String.format("[%s] Registering events", getDescription().getName()));
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        log.info(String.format("[%s] Adding commands", getDescription().getName()));
        this.getCommand("raveldatapack").setExecutor(new RavelDatapackCommand());
        this.getCommand("ravelschematic").setExecutor(new RavelSchematicCommand());
        this.getCommand("ravelbiome").setExecutor(new RavelBiomeCommand());

        log.info(String.format("[%s] All done! Enabled Version %s.", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);

        pluginMessageManager.unregister();

        RavelDatapackAPI.delete();

        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
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

    public static YmlConfig getConfig(String name) {
        return configManager.getConfig(name);
    }
}
