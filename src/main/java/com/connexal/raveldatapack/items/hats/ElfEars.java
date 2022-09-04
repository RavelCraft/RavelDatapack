package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ElfEars extends CustomItem {
    public ElfEars(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "elf_ears";
        this.isHat = true;
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Become an elf");

        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Elf Ears"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.addIngredient(1, Material.PORKCHOP);
        this.instance.getServer().addRecipe(recipe);
    }
}
