package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.utils.YmlConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final RavelDatapack plugin = RavelDatapack.getInstance();
    private final Map<String, YmlConfig> configs = new HashMap<>();

    public ConfigManager() {
        YmlConfig config = new YmlConfigImpl(this.plugin.getConfig());
        config.saveConfig();

        configs.put("config", config);
    }

    public YmlConfig getConfig(String name) {
        if (configs.containsKey(name)) {
            return configs.get(name);
        } else {
            YmlConfig config = new YmlConfigImpl(this.plugin, name + ".yml");
            config.saveConfig();

            configs.put(name, config);

            return config;
        }
    }

    public YmlConfig createConfig(File file, String name) {
        if (configs.containsKey(name)) {
            return null;
        } else {
            YmlConfig config = new YmlConfigImpl(this.plugin, file);
            config.saveConfig();

            configs.put(name, config);

            return config;
        }
    }

    public void saveConfigs() {
        for (YmlConfig config : configs.values()) {
            config.saveConfig();
        }
    }

    public static class YmlConfigImpl implements YmlConfig {
        private FileConfiguration configuration;
        private File file = null;

        public YmlConfigImpl(RavelDatapack plugin, File file) {
            this.file = file;
            if (!this.file.exists()) {
                plugin.saveResource(file.getName(), false);
            }

            this.configuration = new YamlConfiguration();

            try {
                this.configuration.load(this.file);
            } catch (IOException | InvalidConfigurationException e) {
                RavelDatapack.getLog().severe("Unable to create config file " + file.getAbsolutePath() + ": " + e.getMessage());
            }
        }

        public YmlConfigImpl(RavelDatapack plugin, String configName) {
            this(plugin, new File(plugin.getDataFolder(), configName));
        }

        public YmlConfigImpl(FileConfiguration config) {
            this.configuration = config;
        }

        @Override
        public FileConfiguration getConfig() {
            return configuration;
        }

        @Override
        public void saveConfig() {
            if (file == null) {
                RavelDatapack.getInstance().saveConfig();
            } else {
                try {
                    configuration.save(file);
                } catch (IOException e) {
                    RavelDatapack.getLog().severe("Unable to save config file " + file.getAbsolutePath() + ": " + e.getMessage());
                }
            }
        }

        @Override
        public void reloadConfig() {
            if (file != null) {
                this.configuration = new YamlConfiguration();

                try {
                    this.configuration.load(this.file);
                } catch (IOException | InvalidConfigurationException e) {
                    RavelDatapack.getLog().severe("Unable to create config file " + file.getAbsolutePath() + ": " + e.getMessage());
                }
            }
        }
    }
}
