package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class BoltItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomItem.builder(namespaceKey, ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt", Material.CLOCK, TexturePath.item(namespaceKey))
                .lore("Amo for the Bolter")
                .hideFlags(true)
                .build();

        ShapelessRecipe recipe = new ShapelessRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.addIngredient(2, Material.IRON_INGOT);
        adder.register(item, recipe);
    }
}
