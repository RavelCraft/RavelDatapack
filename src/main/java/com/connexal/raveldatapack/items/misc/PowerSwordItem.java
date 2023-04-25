package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerSwordItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.GOLD.toString() + ChatColor.BOLD + "Power Sword", Material.NETHERITE_SWORD, TexturePath.item(namespaceKey), 18, 1)
                .playerHitEntityEvent(PowerSwordItem::playerHitEntityEvent)
                .lore("The sword of the gods", "- Summons lighting", "- Gives blindness")
                .enchantment(Enchantment.SWEEPING_EDGE, 1)
                .unbreakable(true)
                .hideFlags(true)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape(" N ", " N ", " B ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        adder.register(item, recipe);
    }

    private static void playerHitEntityEvent(EntityDamageByEntityEvent event) {
        event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
        if (event.getEntity() instanceof Player target) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 255, false, false));
        }
    }
}
