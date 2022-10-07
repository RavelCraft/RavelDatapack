package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldenWarHammer extends CustomItem {
    public GoldenWarHammer(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "golden_war_hammer";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.GOLDEN_PICKAXE, 1);

        ItemMeta meta = this.createToolMeta(11, 0.5);

        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Golden War Hammer"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("III", "ISI", " S ");
        recipe.setIngredient('I', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.STICK);
        instance.getServer().addRecipe(recipe);
    }
}
