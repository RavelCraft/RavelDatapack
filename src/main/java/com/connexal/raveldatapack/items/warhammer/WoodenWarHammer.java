package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class WoodenWarHammer {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "wooden_war_hammer", ChatColor.WHITE + "Wooden War Hammer", Material.WOODEN_PICKAXE, 6, 0.65)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape("III", "ISI", " S ");
        RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
        recipe.setIngredient('I', planks);
        recipe.setIngredient('S', Material.STICK);

        adder.register(item, recipe);
    }
}
