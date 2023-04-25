package com.connexal.raveldatapack.items.food;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomFoodItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class FruitSaladItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomFoodItem.builder(namespaceKey, ChatColor.WHITE + "Fruit Salad", Material.MUSHROOM_STEW, TexturePath.item(namespaceKey), 6, 13.2f)
                .residue(Material.BOWL)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape("ABS", " U ");
        recipe.setIngredient('U', Material.BOWL);
        recipe.setIngredient('B', new RecipeChoice.MaterialChoice(Material.SWEET_BERRIES, Material.GLOW_BERRIES));
        recipe.setIngredient('A', Material.APPLE);
        recipe.setIngredient('S', Material.MELON_SLICE);

        adder.register(item, recipe);
    }
}
