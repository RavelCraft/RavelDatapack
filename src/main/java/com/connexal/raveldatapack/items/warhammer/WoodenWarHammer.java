package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class WoodenWarHammer extends CustomItem {
    public WoodenWarHammer(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "wooden_war_hammer";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.WOODEN_PICKAXE, 1);

        ItemMeta meta = this.createToolMeta(6, 0.65);

        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Wooden War Hammer"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("III", "ISI", " S ");
        RecipeChoice planks = new RecipeChoice.MaterialChoice(Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.DARK_OAK_PLANKS, Material.CRIMSON_PLANKS, Material.WARPED_PLANKS);
        recipe.setIngredient('I', planks);
        recipe.setIngredient('S', Material.STICK);
        instance.getServer().addRecipe(recipe);
    }
}
