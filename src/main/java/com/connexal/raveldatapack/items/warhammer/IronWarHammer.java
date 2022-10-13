package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class IronWarHammer extends CustomItem {
    public IronWarHammer(int customModelData) {
        super(customModelData, "iron_war_hammer");
    }

    @Override
    public void create() {
        this.createItem(Material.IRON_PICKAXE);

        ItemMeta meta = this.createToolMeta(10, 0.55);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Iron War Hammer"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("III", "ISI", " S ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('S', Material.STICK);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);
    }
}
