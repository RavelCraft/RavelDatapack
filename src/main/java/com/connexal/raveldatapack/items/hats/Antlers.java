package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Antlers extends CustomItem {
    public Antlers(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "antlers";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Antlers go brrr");

        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Antlers"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("   ", "W W", "W W");
        recipe.setIngredient('W', Material.OAK_LOG);
        this.instance.getServer().addRecipe(recipe);
    }
}
