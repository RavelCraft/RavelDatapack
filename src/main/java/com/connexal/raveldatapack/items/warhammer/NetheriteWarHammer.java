package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class NetheriteWarHammer {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "netherite_war_hammer", ChatColor.WHITE + "Netherite War Hammer", Material.NETHERITE_PICKAXE, 13, 0.4)
                .allowedEnchantment(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD, Enchantment.DAMAGE_ARTHROPODS)
                .build();

        ItemStack diamondWarHammer = EasyDatapackAPI.getItemManager().getItemStack("diamond_war_hammer");
        if (diamondWarHammer != null) {
            RecipeChoice base = new RecipeChoice.ExactChoice(diamondWarHammer);
            RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
            SmithingRecipe recipe = new SmithingRecipe(item.getNamespacedKey(), item.getItemStack(), base, addition);

            adder.register(item, recipe);
        } else {
            adder.register(item);
        }
    }
}
