package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.items.CustomToolItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class DiamondWarHammer extends CustomToolItem {
    public DiamondWarHammer(int customModelData) {
        super(customModelData, "diamond_war_hammer");
    }

    @Override
    public void create() {
        this.createItem(Material.DIAMOND_PICKAXE);

        ItemMeta meta = this.createToolMeta(12, 0.45);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Diamond War Hammer"));
        this.setItemMeta(meta);

        this.allowEnchantment(Enchantment.DAMAGE_ALL);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("III", "ISI", " S ");
        recipe.setIngredient('I', Material.DIAMOND);
        recipe.setIngredient('S', Material.STICK);
        RavelDatapackAPI.getRecipeManager().registerRecipe(recipe);
    }
}
