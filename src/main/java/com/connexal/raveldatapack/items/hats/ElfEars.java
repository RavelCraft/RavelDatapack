package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomHatItem;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ElfEars {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomHatItem.Builder(customModelData, "elf_ears", ChatColor.AQUA.toString() + ChatColor.BOLD + "Elf Ears", Material.CLOCK)
                .lore("Become an elf")
                .build();

        adder.register(item);
    }
}
