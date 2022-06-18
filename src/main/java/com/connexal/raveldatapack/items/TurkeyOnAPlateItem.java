package com.connexal.raveldatapack.items;

import com.connexal.raveldatapack.RavelDatapack;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TurkeyOnAPlateItem extends CustomItem {
    public TurkeyOnAPlateItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "turkey_on_a_plate";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Turkey on a Plate"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ItemStack plate = RavelDatapack.getItemManager().getItem("plate");
        if (plate != null) {
            ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
            recipe.shape(" C ", " P ", "   ");
            recipe.setIngredient('C', Material.COOKED_CHICKEN);
            RecipeChoice plate_ingredient = new RecipeChoice.ExactChoice(plate);
            recipe.setIngredient('P', plate_ingredient);
            instance.getServer().addRecipe(recipe);
        }
    }
}
