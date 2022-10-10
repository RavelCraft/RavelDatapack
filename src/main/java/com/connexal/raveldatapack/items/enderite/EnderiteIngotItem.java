package com.connexal.raveldatapack.items.enderite;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderiteIngotItem extends CustomItem {
    public EnderiteIngotItem(int customModelData) {
        super(customModelData, "enderite_ingot");
    }

    @Override
    public void create() {
        this.createItem(Material.NETHERITE_INGOT);
        ItemMeta meta = this.createItemMeta(false, false);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Ingot"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("EEE", "EIE", "EEE");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('I', Material.ENDER_EYE);
        this.instance.getServer().addRecipe(recipe);
    }
}
