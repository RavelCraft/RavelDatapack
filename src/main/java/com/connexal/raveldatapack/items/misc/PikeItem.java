package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ShapedRecipe;

public class PikeItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.GOLD.toString() + ChatColor.BOLD + "Pike", Material.CLOCK, TexturePath.item(namespaceKey), 6, 1)
                .playerHitEntityEvent(PikeItem::playerHitEntityEvent)
                .lore("Pierce your enemies' armor")
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape("DDD", "DND", "DDD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);

        adder.register(item, recipe);
    }

    private static void playerHitEntityEvent(EntityDamageByEntityEvent event) {
        event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
        event.setDamage(6);
    }
}
