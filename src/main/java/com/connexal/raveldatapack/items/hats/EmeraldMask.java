package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
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
        this.isHat = true;
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "You got that drip");

        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Emerald Mask"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);
    }
}
