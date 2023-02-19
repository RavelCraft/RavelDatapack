package com.connexal.raveldatapack.enchantments;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEnchantmentException;
import com.github.imdabigboss.easydatapack.api.utils.ItemsUtil;
import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonBladeEnchantment implements Listener {
    private final CustomEnchantment enchantment;

    public PoisonBladeEnchantment(CustomEnchantment enchantment) {
        this.enchantment = enchantment;
    }

    public static void register(CustomRegistry.CustomRegistryAdder adder) throws CustomEnchantmentException {
        CustomEnchantment enchantment = new CustomEnchantment.Builder("Poison Blade", "poison_blade", ItemsUtil::isItemASword, EnchantmentTarget.WEAPON)
                .anvilMergeCost(level -> 10 * level)
                .tradeCost(level -> 12 * level)
                .tradeable(false)
                .discoverable(false)
                .rarity(EnchantmentRarity.VERY_RARE)
                .maxLevel(3)
                .eventListener(PoisonBladeEnchantment.class)
                .build();

        adder.register(enchantment);
    }

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!this.enchantment.hasEnchantment(item)) {
                return;
            }

            if (event.getEntity() instanceof LivingEntity entity) {

                int time = this.enchantment.getEnchantmentLevel(item);
                if (time == 0) {
                    time = 1;
                }

                entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time * 2 * 20, 255, false, true));
            }
        }
    }
}
