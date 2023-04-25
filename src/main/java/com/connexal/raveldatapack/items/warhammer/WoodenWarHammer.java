package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class WoodenWarHammer {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.WHITE + "Wooden War Hammer", Material.WOODEN_PICKAXE, TexturePath.item(namespaceKey), 6, 0.65)
                .allowedEnchantment(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD, Enchantment.DAMAGE_ARTHROPODS)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape("III", "ISI", " S ");
        RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
        recipe.setIngredient('I', planks);
        recipe.setIngredient('S', Material.STICK);

        adder.register(item, recipe);
    }
}
