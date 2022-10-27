package com.connexal.raveldatapack.enchantments;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEnchantmentException;
import com.github.imdabigboss.easydatapack.api.utils.ItemsUtil;
import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BlazingArmorEnchantment implements Listener {
    private final CustomEnchantment enchantment;

    public BlazingArmorEnchantment(CustomEnchantment enchantment) {
        this.enchantment = enchantment;
    }

    public static void register(CustomRegistry.CustomRegistryAdder adder) throws CustomEnchantmentException {
        CustomEnchantment enchantment = new CustomEnchantment.Builder("Blazing Armor", "balzing_armor", BlazingArmorEnchantment::canEnchantItem, EnchantmentTarget.ARMOR)
                .anvilMergeCost(level -> 10 * level)
                .tradeCost(level -> 14 * level)
                .tradeable(false)
                .discoverable(false)
                .rarity(EnchantmentRarity.VERY_RARE)
                .maxLevel(3)
                .eventListener(BlazingArmorEnchantment.class)
                .build();

        adder.register(enchantment);
    }

    private static boolean canEnchantItem(ItemStack item) {
        return ItemsUtil.isItemArmor(item);
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack item1 = player.getInventory().getBoots();
            ItemStack item2 = player.getInventory().getLeggings();
            ItemStack item3 = player.getInventory().getChestplate();
            ItemStack item4 = player.getInventory().getHelmet();

            int fire_time = 0;

            if (this.enchantment.hasEnchantment(item1)) {
                fire_time += this.enchantment.getEnchantmentLevel(item1);
            }
            if (this.enchantment.hasEnchantment(item2)) {
                fire_time += this.enchantment.getEnchantmentLevel(item2);
            }
            if (this.enchantment.hasEnchantment(item3)) {
                fire_time += this.enchantment.getEnchantmentLevel(item3);
            }
            if (this.enchantment.hasEnchantment(item4)) {
                fire_time += this.enchantment.getEnchantmentLevel(item4);
            }

            event.getDamager().setFireTicks(fire_time * 20);
        }
    }
}
