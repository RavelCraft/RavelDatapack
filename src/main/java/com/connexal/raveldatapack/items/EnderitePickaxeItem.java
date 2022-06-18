package com.connexal.raveldatapack.items;

import com.connexal.raveldatapack.RavelDatapack;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderitePickaxeItem extends CustomItem {
    public EnderitePickaxeItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "enderite_pickaxe";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.NETHERITE_PICKAXE, 1);

        ItemMeta meta = this.createItemMeta(false, false);

        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Pickaxe"));
        this.setAttackDamage(meta, 7, EquipmentSlot.HAND);
        this.setAttackSpeed(meta, 1.2, EquipmentSlot.HAND);
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ItemStack ingot = RavelDatapack.getItemManager().getItem("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_PICKAXE);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot);
            SmithingRecipe recipe = new SmithingRecipe(NamespacedKey.minecraft(namespaceKey), this.itemStack, base, addition);
            RavelDatapack.getInstance().getServer().addRecipe(recipe);
        }
    }
}
