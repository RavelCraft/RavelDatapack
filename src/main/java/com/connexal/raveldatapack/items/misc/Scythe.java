package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.Vector;

public class Scythe {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "scythe", ChatColor.GOLD.toString() + ChatColor.BOLD + "Scythe", Material.DIAMOND_HOE, 8, 0.5)
                .playerHitEntityEvent(Scythe::playerHitEntityEvent)
                .allowedEnchantment(Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS, Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD, Enchantment.DAMAGE_ARTHROPODS, Enchantment.KNOCKBACK)
                .lore("Harvest your enemies'", "souls!", "(and your crops)")
                .enchantment(Enchantment.SWEEPING_EDGE, 1)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape("NN ", " BN", " B ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        adder.register(item, recipe);
    }

    private static void playerHitEntityEvent(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Player player = (Player) event.getDamager();

        Vector direction = player.getEyeLocation().subtract(entity.getLocation()).toVector();
        entity.setVelocity(direction.normalize().multiply(4.0));
    }
}
