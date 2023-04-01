package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomHatItem;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SantaHat {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = CustomHatItem.builder(customModelData, "santa_hat", ChatColor.AQUA.toString() + ChatColor.BOLD + "Santa Hat", Material.CLOCK)
                .lore("Become Santa")
                .build();

        adder.register(item);
    }
}
