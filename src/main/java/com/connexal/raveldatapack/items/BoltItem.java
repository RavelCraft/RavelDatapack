package com.connexal.raveldatapack.items;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BoltItem extends CustomItem {
    public BoltItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "bolt";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Amo for the Bolter");

        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.addIngredient(2, Material.IRON_INGOT);
        this.instance.getServer().addRecipe(recipe);
    }
}
