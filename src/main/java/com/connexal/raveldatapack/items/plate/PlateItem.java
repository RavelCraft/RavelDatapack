package com.connexal.raveldatapack.items.plate;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class PlateItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomItem.builder(namespaceKey, ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Plate", Material.CLOCK, TexturePath.item(namespaceKey))
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape(" P ", "PPP", " P ");
        RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
        recipe.setIngredient('P', planks);

        adder.register(item, recipe);
    }
}
