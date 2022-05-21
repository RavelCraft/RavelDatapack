package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BunnyEars extends CustomItem {
    public BunnyEars(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "bunny_ears";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Become a bunny");

        meta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Bunny Ears");
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("   ", "H H", "   ");
        recipe.setIngredient('H', Material.RABBIT_HIDE);
        this.instance.getServer().addRecipe(recipe);
    }
}
