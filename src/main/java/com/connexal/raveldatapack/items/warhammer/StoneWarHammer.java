package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class StoneWarHammer {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "stone_war_hammer", ChatColor.WHITE + "Stone War Hammer", Material.STONE_PICKAXE, 9, 0.6)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape("III", "ISI", " S ");
        recipe.setIngredient('I', Material.COBBLESTONE);
        recipe.setIngredient('S', Material.STICK);

        adder.register(item, recipe);
    }
}
