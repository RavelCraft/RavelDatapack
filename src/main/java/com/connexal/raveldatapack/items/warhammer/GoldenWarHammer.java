package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldenWarHammer extends CustomItem {
    public GoldenWarHammer(int customModelData) {
        super(customModelData, "golden_war_hammer");
    }

    @Override
    public void create() {
        this.createItem(Material.GOLDEN_PICKAXE);

        ItemMeta meta = this.createToolMeta(11, 0.5);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Golden War Hammer"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("III", "ISI", " S ");
        recipe.setIngredient('I', Material.GOLD_INGOT);
        recipe.setIngredient('S', Material.STICK);
        this.instance.getServer().addRecipe(recipe);
    }
}
