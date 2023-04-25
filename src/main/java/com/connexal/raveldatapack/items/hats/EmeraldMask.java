package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomHatItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class EmeraldMask {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomHatItem.builder(namespaceKey, ChatColor.AQUA.toString() + ChatColor.BOLD + "Emerald Mask", Material.CLOCK, TexturePath.item(namespaceKey))
                .lore("You got that drip")
                .build();

        adder.register(item);
    }
}
