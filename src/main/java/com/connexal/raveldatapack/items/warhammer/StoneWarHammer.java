package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class StoneWarHammer extends CustomItem {
    public StoneWarHammer(int customModelData) {
        super(customModelData, "stone_war_hammer");
    }

    @Override
    public void create() {
        this.createItem(Material.STONE_PICKAXE);

        ItemMeta meta = this.createToolMeta(9, 0.6);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Stone War Hammer"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("III", "ISI", " S ");
        recipe.setIngredient('I', Material.COBBLESTONE);
        recipe.setIngredient('S', Material.STICK);
        this.instance.getServer().addRecipe(recipe);
    }
}
