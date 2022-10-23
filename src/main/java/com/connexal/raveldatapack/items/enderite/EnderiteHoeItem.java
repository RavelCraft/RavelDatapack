package com.connexal.raveldatapack.items.enderite;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.items.CustomToolItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderiteHoeItem extends CustomToolItem {
    public EnderiteHoeItem(int customModelData) {
        super(customModelData, "enderite_hoe");
    }

    @Override
    public void create() {
        this.createItem(Material.NETHERITE_HOE);

        ItemMeta meta = this.createToolMeta(1, 4);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Hoe"));
        this.setItemMeta(meta);

        ItemStack ingot = RavelDatapackAPI.getItemManager().getItem("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_HOE);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot);
            SmithingRecipe recipe = new SmithingRecipe(this.getNamespacedKey(), this.getItemStack(), base, addition);
            RavelDatapackAPI.getRecipeManager().registerRecipe(recipe);
        }
    }
}
