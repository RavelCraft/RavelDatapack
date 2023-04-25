package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;

public class ChopperItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.GOLD.toString() + ChatColor.BOLD + "Chopper", Material.CLOCK, TexturePath.item(namespaceKey), 8, 1)
                .playerHitEntityEvent(ChopperItem::playerHitEntityEvent)
                .lore("Slice up your enemies")
                .attributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("generic.movementSpeed", 1.3, AttributeModifier.Operation.MULTIPLY_SCALAR_1))
                .hideFlags(true)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape("NNN", "III", "III");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('I', Material.IRON_INGOT);

        adder.register(item, recipe);
    }

    private static void playerHitEntityEvent(EntityDamageByEntityEvent event) {
        for (Player tmp : RavelDatapack.getInstance().getServer().getOnlinePlayers()) {
            tmp.playSound(event.getDamager().getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
        }
    }
}
