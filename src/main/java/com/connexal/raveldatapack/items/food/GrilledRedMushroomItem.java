package com.connexal.raveldatapack.items.food;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomFoodItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;

public class GrilledRedMushroomItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomFoodItem.builder(namespaceKey, ChatColor.WHITE + "Grilled Red Mushroom", Material.COOKED_BEEF, TexturePath.item(namespaceKey), 3, 7.2f)
                .build();

        adder.register(item, new FurnaceRecipe(item.getNamespacedKey(), item.createItemStack(), Material.RED_MUSHROOM, 0.10f, 100));
    }
}
