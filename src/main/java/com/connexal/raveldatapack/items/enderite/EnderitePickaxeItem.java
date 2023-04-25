package com.connexal.raveldatapack.items.enderite;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class EnderitePickaxeItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.WHITE + "Enderite Pickaxe", Material.NETHERITE_PICKAXE, TexturePath.item(namespaceKey), 7, 1.2)
                .build();

        CustomItem ingot = EasyDatapackAPI.getItemManager().getCustomItem("enderite_ingot");
        if (ingot != null) {
            RecipeChoice base = new RecipeChoice.MaterialChoice(Material.NETHERITE_PICKAXE);
            RecipeChoice addition = new RecipeChoice.ExactChoice(ingot.createItemStack());
            adder.register(item, new SmithingRecipe(item.getNamespacedKey(), item.createItemStack(), base, addition));
        } else {
            RavelDatapack.getInstance().getLogger().severe("Could not find the enderite ingot item!");
            adder.register(item);
        }
    }
}
