package com.connexal.raveldatapack.items.enderite;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.items.CustomToolItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class EnderiteSwordItem extends CustomToolItem {
    public EnderiteSwordItem(int customModelData) {
        super(customModelData, "enderite_sword");
    }

    @Override
    public void create() {
        this.createItem(Material.NETHERITE_SWORD);

        ItemMeta meta = this.createToolMeta(10, 1.6);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Sword"));
        this.setItemMeta(meta);

        ItemStack ingot = RavelDatapackAPI.getItemManager().getItem("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_SWORD);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot);
            SmithingRecipe recipe = new SmithingRecipe(this.getNamespacedKey(), this.getItemStack(), base, addition);
            RavelDatapackAPI.getRecipeManager().registerRecipe(recipe);
        }
    }
}
