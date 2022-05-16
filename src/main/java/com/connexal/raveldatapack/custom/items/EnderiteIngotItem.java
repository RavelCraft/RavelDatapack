package com.connexal.raveldatapack.custom.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderiteIngotItem extends CustomItem {
    public EnderiteIngotItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "enderite_ingot";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta(false, false);

        meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Ingot");
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("EEE", "EIE", "EEE");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('I', Material.ENDER_EYE);
        this.instance.getServer().addRecipe(recipe);
    }
}
