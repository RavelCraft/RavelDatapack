package com.connexal.raveldatapack.items.enderite;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class EnderiteIngotItem {
    public static void register(CustomAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomItem.Builder(customModelData, "enderite_ingot", ChatColor.WHITE + "Enderite Ingot", Material.NETHERITE_INGOT)
                .build();

        adder.register(item);

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape("EEE", "EIE", "EEE");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('I', Material.ENDER_EYE);
        adder.register(recipe);
    }
}
