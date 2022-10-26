package com.connexal.raveldatapack.items.nope;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ShapedRecipe;

public class PikeItem {
    public static void register(CustomAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "pike", ChatColor.GOLD.toString() + ChatColor.BOLD + "Pike", Material.CLOCK, 6, 1)
                .playerHitEntityEvent(PikeItem::playerHitEntityEvent)
                .lore("Pierce your enemies' armor")
                .build();

        adder.register(item);

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape("DDD", "DND", "DDD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        adder.register(recipe);
    }

    private static void playerHitEntityEvent(EntityDamageByEntityEvent event) {
        event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
        event.setDamage(6);
    }
}
