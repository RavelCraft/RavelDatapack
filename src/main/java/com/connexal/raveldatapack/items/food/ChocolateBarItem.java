package com.connexal.raveldatapack.items.food;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomFoodItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class ChocolateBarItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomFoodItem.builder(namespaceKey, ChatColor.WHITE + "Chocolate Bar", Material.COOKED_PORKCHOP, TexturePath.item(namespaceKey), 3, 8.0f)
                .residue(Material.PAPER)
                .build();

        ShapelessRecipe recipe = new ShapelessRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.addIngredient(Material.PAPER);
        recipe.addIngredient(Material.SUGAR);
        recipe.addIngredient(Material.COCOA_BEANS);
        recipe.addIngredient(Material.MILK_BUCKET);

        adder.register(item, recipe);
    }
}
