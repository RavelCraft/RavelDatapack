package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class NetheriteWarHammer extends CustomItem {
    public NetheriteWarHammer(int customModelData) {
        super(customModelData, "netherite_war_hammer");
    }

    @Override
    public void create() {
        this.createItem(Material.NETHERITE_PICKAXE);

        ItemMeta meta = this.createToolMeta(13, 0.4);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Netherite War Hammer"));
        this.setItemMeta(meta);

        ItemStack diamondWarHammer = RavelDatapack.getItemManager().getItem("diamond_war_hammer");
        if (diamondWarHammer != null) {
            RecipeChoice base = new RecipeChoice.ExactChoice(diamondWarHammer);
            RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
            SmithingRecipe recipe = new SmithingRecipe(this.getNamespacedKey(), this.getItemStack(), base, addition);
            this.instance.getServer().addRecipe(recipe);
        }
    }
}
