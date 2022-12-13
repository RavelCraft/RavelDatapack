package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class BoltItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomItem.Builder(customModelData, "bolt", ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt", Material.CLOCK)
                .lore("Amo for the Bolter")
                .hideFlags(true)
                .build();

        ShapelessRecipe recipe = new ShapelessRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.addIngredient(2, Material.IRON_INGOT);
        adder.register(item, recipe);
    }
}
