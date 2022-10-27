package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomHatItem;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class PartyHat {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomHatItem.Builder(customModelData, "party_hat", ChatColor.AQUA.toString() + ChatColor.BOLD + "Party Hat", Material.CLOCK)
                .lore("It's party time!")
                .build();

        adder.register(item);
    }
}
