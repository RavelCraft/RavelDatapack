package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BoltItem extends CustomItem {
    public BoltItem(int customModelData) {
        super(customModelData, "bolt");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Amo for the Bolter");
        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt"));
        this.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.addIngredient(2, Material.IRON_INGOT);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);
    }
}
