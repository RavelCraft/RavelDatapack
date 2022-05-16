package com.connexal.raveldatapack.custom.items;

import com.connexal.raveldatapack.RavelDatapack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderiteHoeItem extends CustomItem {
    public EnderiteHoeItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "enderite_hoe";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.NETHERITE_HOE, 1);

        ItemMeta meta = this.createItemMeta(false, false);

        meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Hoe");
        this.setAttackDamage(meta, 1, EquipmentSlot.HAND);
        this.setAttackSpeed(meta, 4, EquipmentSlot.HAND);
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ItemStack ingot = RavelDatapack.getItemManager().getItem("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_HOE);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot);
            SmithingRecipe recipe = new SmithingRecipe(NamespacedKey.minecraft(namespaceKey), this.itemStack, base, addition);
            RavelDatapack.getInstance().getServer().addRecipe(recipe);
        }
    }
}
