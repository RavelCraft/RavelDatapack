package com.connexal.raveldatapack.enchantments;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.types.enchantments.CustomEnchantment;
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
        CustomEnchantment enchantment = CustomEnchantment.builder("Blazing Armor", "balzing_armor", ItemsUtil::isItemArmor, EnchantmentTarget.ARMOR)
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

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack item1 = player.getInventory().getBoots();
            ItemStack item2 = player.getInventory().getLeggings();
            ItemStack item3 = player.getInventory().getChestplate();
            ItemStack item4 = player.getInventory().getHelmet();

            int fireTime = 0;

            if (item1 != null && this.enchantment.hasEnchantment(item1)) {
                fireTime += this.enchantment.getEnchantmentLevel(item1);
            }
            if (item2 != null && this.enchantment.hasEnchantment(item2)) {
                fireTime += this.enchantment.getEnchantmentLevel(item2);
            }
            if (item3 != null && this.enchantment.hasEnchantment(item3)) {
                fireTime += this.enchantment.getEnchantmentLevel(item3);
            }
            if (item4 != null && this.enchantment.hasEnchantment(item4)) {
                fireTime += this.enchantment.getEnchantmentLevel(item4);
            }

            event.getDamager().setFireTicks(fireTime * 20);
        }
    }
}
