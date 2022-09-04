package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class PartyHat extends CustomItem {
    public PartyHat(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "party_hat";
        this.isHat = true;
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Put this hat on to have a party!");

        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Party Hat"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("   ", " R ", "WRW");
        recipe.setIngredient('R', Material.RED_WOOL);
        recipe.setIngredient('W', Material.WHITE_WOOL);
        this.instance.getServer().addRecipe(recipe);
    }
}
