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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;

public class ThunderHammerItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.RED.toString() + ChatColor.BOLD + "Thunder Hammer", Material.CLOCK, TexturePath.item(namespaceKey), 35, 0.5)
                .playerHitEntityEvent(ThunderHammerItem::playerHitEntityEvent)
                .lore("Show off", "- Slow but strong", "- Knockback 5")
                .enchantment(Enchantment.KNOCKBACK, 5)
                .unbreakable(true)
                .hideFlags(true)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape("  N", " B ", "B  ");
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        adder.register(item, recipe);
    }

    private static void playerHitEntityEvent(EntityDamageByEntityEvent event) {
        for (Player tmp : RavelDatapack.getInstance().getServer().getOnlinePlayers()) {
            tmp.playSound(event.getEntity().getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
        }
    }
}
