package com.connexal.raveldatapack.custom.hats;

import com.connexal.raveldatapack.custom.items.CustomItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EmeraldMask extends CustomItem {
    public EmeraldMask(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "emerald_mask";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "You got that drip");

        meta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Emerald Mask");
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("LLL", "LEL", "LLL");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('E', Material.EMERALD);
        this.instance.getServer().addRecipe(recipe);
    }
}
