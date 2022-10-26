package com.connexal.raveldatapack.items.hats;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomHatItem;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class EmeraldMask {
    public static void register(CustomAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomHatItem.Builder(customModelData, "emerald_mask", ChatColor.AQUA.toString() + ChatColor.BOLD + "Emerald Mask", Material.CLOCK)
                .lore("You got that drip")
                .build();

        adder.register(item);
    }
}
