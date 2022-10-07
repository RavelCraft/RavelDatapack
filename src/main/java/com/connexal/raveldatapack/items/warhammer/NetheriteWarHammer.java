package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class NetheriteWarHammer extends CustomItem {
    public NetheriteWarHammer(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "netherite_war_hammer";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.NETHERITE_PICKAXE, 1);

        ItemMeta meta = this.createToolMeta(13, 0.4);

        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + "Netherite War Hammer"));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ItemStack diamondWarHammer = RavelDatapack.getItemManager().getItem("diamond_war_hammer");
        if (diamondWarHammer != null) {
            RecipeChoice base = new RecipeChoice.ExactChoice(diamondWarHammer);
            RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
            SmithingRecipe recipe = new SmithingRecipe(NamespacedKey.minecraft(namespaceKey), this.itemStack, base, addition);
            RavelDatapack.getInstance().getServer().addRecipe(recipe);
        }
    }
}
