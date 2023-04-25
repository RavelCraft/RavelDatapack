package com.connexal.raveldatapack.items.plate;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class TurkeyOnAPlateItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomItem.builder(namespaceKey, ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Turkey on a Plate", Material.CLOCK, TexturePath.item(namespaceKey))
                .build();

        CustomItem plate = EasyDatapackAPI.getItemManager().getCustomItem("plate");
        if (plate != null) {
            ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
            recipe.shape(" C ", " P ", "   ");
            recipe.setIngredient('C', Material.COOKED_CHICKEN);
            RecipeChoice plate_ingredient = new RecipeChoice.ExactChoice(plate.createItemStack());
            recipe.setIngredient('P', plate_ingredient);

            adder.register(item, recipe);
        } else {
            adder.register(item);
        }
    }
}
