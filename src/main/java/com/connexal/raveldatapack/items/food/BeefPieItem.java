package com.connexal.raveldatapack.items.food;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomFoodItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;

public class BeefPieItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomFoodItem.builder(namespaceKey, ChatColor.WHITE + "Beef Pie", Material.COOKED_BEEF, TexturePath.item(namespaceKey), 14, 22.0f)
                .build();

        ShapelessRecipe recipe = new ShapelessRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.addIngredient(Material.COOKED_BEEF);
        recipe.addIngredient(Material.BREAD);
        recipe.addIngredient(Material.EGG);

        adder.register(item, recipe);
    }
}