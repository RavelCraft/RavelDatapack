package com.connexal.raveldatapack.items.warhammer;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class NetheriteWarHammer {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.WHITE + "Netherite War Hammer", Material.NETHERITE_PICKAXE, TexturePath.item(namespaceKey), 13, 0.4)
                .allowedEnchantment(Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD, Enchantment.DAMAGE_ARTHROPODS)
                .build();

        CustomItem diamondWarHammer = EasyDatapackAPI.getItemManager().getCustomItem("diamond_war_hammer");
        if (diamondWarHammer != null) {
            RecipeChoice base = new RecipeChoice.ExactChoice(diamondWarHammer.createItemStack());
            RecipeChoice addition = new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT);
            SmithingRecipe recipe = new SmithingRecipe(item.getNamespacedKey(), item.createItemStack(), base, addition);

            adder.register(item, recipe);
        } else {
            adder.register(item);
        }
    }
}
