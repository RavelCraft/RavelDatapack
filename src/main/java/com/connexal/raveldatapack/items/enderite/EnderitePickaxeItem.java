package com.connexal.raveldatapack.items.enderite;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class EnderitePickaxeItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "enderite_pickaxe", ChatColor.WHITE + "Enderite Pickaxe", Material.NETHERITE_PICKAXE, 7, 1.2)
                .build();

        ItemStack ingot = EasyDatapackAPI.getItemManager().getItemStack("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_PICKAXE);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot);
            adder.register(item, new SmithingRecipe(item.getNamespacedKey(), item.getItemStack(), base, addition));
        } else {
            adder.register(item);
        }
    }
}
