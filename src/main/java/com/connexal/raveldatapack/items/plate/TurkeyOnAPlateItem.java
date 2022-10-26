package com.connexal.raveldatapack.items.plate;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomHatItem;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class TurkeyOnAPlateItem {
    public static void register(CustomAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomHatItem.Builder(customModelData, "turkey_on_a_plate", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Turkey on a Plate", Material.CLOCK)
                .build();

        adder.register(item);

        ItemStack plate = EasyDatapackAPI.getItemManager().getItemStack("plate");
        if (plate != null) {
            ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
            recipe.shape(" C ", " P ", "   ");
            recipe.setIngredient('C', Material.COOKED_CHICKEN);
            RecipeChoice plate_ingredient = new RecipeChoice.ExactChoice(plate);
            recipe.setIngredient('P', plate_ingredient);
            adder.register(recipe);
        }
    }
}
