package com.connexal.raveldatapack.custom.hats;

import com.connexal.raveldatapack.custom.items.CustomItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SantaHat extends CustomItem {
    public SantaHat(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "santa_hat";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Become Santa");

        meta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Santa Hat");
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape(" S "," R ", "RRR");
        recipe.setIngredient('R', Material.RED_WOOL);
        recipe.setIngredient('S', Material.SNOWBALL);
        this.instance.getServer().addRecipe(recipe);
    }
}
