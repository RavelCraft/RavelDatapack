package com.connexal.raveldatapack.api.utils;

import org.bukkit.configuration.file.FileConfiguration;

public interface YmlConfig {
    FileConfiguration getConfig();

    void saveConfig();

    void reloadConfig();
}
