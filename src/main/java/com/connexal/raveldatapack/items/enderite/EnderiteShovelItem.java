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

public class EnderiteShovelItem extends CustomToolItem {
    public EnderiteShovelItem(int customModelData) {
        super(customModelData, "enderite_shovel");
    }

    @Override
    public void create() {
        this.createItem(Material.NETHERITE_SHOVEL);

        ItemMeta meta = this.createToolMeta(7.5, 1);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Enderite Shovel"));
        this.setItemMeta(meta);

        ItemStack ingot = RavelDatapackAPI.getItemManager().getItem("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_SHOVEL);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot);
            SmithingRecipe recipe = new SmithingRecipe(this.getNamespacedKey(), this.getItemStack(), base, addition);
            RavelDatapackAPI.getRecipeManager().registerRecipe(recipe);
        }
    }
}
