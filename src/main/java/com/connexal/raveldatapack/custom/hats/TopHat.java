package com.connexal.raveldatapack.custom.hats;

import com.connexal.raveldatapack.custom.items.CustomItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TopHat extends CustomItem {
    public TopHat(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "top_hat";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Put this on to look fancy");

        meta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Top Hat");
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape(" B ", " L ", "BBB");
        recipe.setIngredient('B', Material.BLACK_WOOL);
        recipe.setIngredient('L', Material.LEATHER);
        this.instance.getServer().addRecipe(recipe);
    }
}
