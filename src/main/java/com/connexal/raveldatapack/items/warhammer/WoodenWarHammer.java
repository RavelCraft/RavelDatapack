package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class WoodenWarHammer extends CustomItem {
    public WoodenWarHammer(int customModelData) {
        super(customModelData, "wooden_war_hammer");
    }

    @Override
    public void create() {
        this.createItem(Material.WOODEN_PICKAXE);

        ItemMeta meta = this.createToolMeta(6, 0.65);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Wooden War Hammer"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("III", "ISI", " S ");
        RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
        recipe.setIngredient('I', planks);
        recipe.setIngredient('S', Material.STICK);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);
    }
}
