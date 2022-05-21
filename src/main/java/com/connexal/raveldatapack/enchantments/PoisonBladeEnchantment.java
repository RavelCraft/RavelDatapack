package com.connexal.raveldatapack.enchantments;

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
import org.jetbrains.annotations.NotNull;

public class PoisonBladeEnchantment extends CustomEnchantment implements Listener {
    public PoisonBladeEnchantment() {
        super("poison_blade", "Poison Blade");
    }

    @Override
    public void create() {
        this.enchantment = this;
        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!this.hasEnchantment(item)) {
                return;
            }

            if (event.getEntity() instanceof LivingEntity entity) {

                int time = this.getEnchantmentLevel(item);
                if (time == 0) {
                    time = 1;
                }

                entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time * 2 * 20, 255, false, true));
            }
        }
    }

    @Override
    public boolean canEnchantItemInternal(ItemStack item) {
        return item.getType().name().endsWith("_SWORD");
    }

    @Override
    public int getAnvilMergeCost(int level) {
        return 10 * level;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.VERY_RARE;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
