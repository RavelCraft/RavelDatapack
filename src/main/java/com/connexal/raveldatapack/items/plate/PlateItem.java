package com.connexal.raveldatapack.items.plate;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class PlateItem extends CustomItem {
    public PlateItem(int customModelData) {
        super(customModelData, "plate");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Plate"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape(" P ", "PPP", " P ");
        RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
        recipe.setIngredient('P', planks);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);
    }
}
