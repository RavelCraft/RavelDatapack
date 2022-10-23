package com.connexal.raveldatapack.items.plate;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TurkeyOnAPlateItem extends CustomItem {
    public TurkeyOnAPlateItem(int customModelData) {
        super(customModelData, "turkey_on_a_plate");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Turkey on a Plate"));
        this.setItemMeta(meta);

        ItemStack plate = RavelDatapackAPI.getItemManager().getItem("plate");
        if (plate != null) {
            ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
            recipe.shape(" C ", " P ", "   ");
            recipe.setIngredient('C', Material.COOKED_CHICKEN);
            RecipeChoice plate_ingredient = new RecipeChoice.ExactChoice(plate);
            recipe.setIngredient('P', plate_ingredient);
            RavelDatapackAPI.getRecipeManager().registerRecipe(recipe);
        }
    }
}
