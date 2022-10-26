package com.connexal.raveldatapack.items.nope;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class BoltItem {
    public static void register(CustomAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomItem.Builder(customModelData, "bolt", ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt", Material.CLOCK)
                .lore("Amo for the Bolter")
                .hideFlags(true)
                .build();

        adder.register(item);

        ShapelessRecipe recipe = new ShapelessRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.addIngredient(2, Material.IRON_INGOT);
        adder.register(recipe);
    }
}
