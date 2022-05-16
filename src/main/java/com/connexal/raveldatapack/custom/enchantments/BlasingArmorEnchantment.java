package com.connexal.raveldatapack.custom.enchantments;

import com.connexal.raveldatapack.utils.ItemsUtil;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlasingArmorEnchantment extends CustomEnchantment implements Listener {
    public BlasingArmorEnchantment() {
        super("blasing_armor", "Blasing Armor");
    }

    @Override
    public void create() {
        this.enchantment = this;
        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack item1 = player.getInventory().getBoots();
            ItemStack item2 = player.getInventory().getLeggings();
            ItemStack item3 = player.getInventory().getChestplate();
            ItemStack item4 = player.getInventory().getHelmet();

            int fire_time = 0;

            if (hasEnchantment(item1)) {
                fire_time += getEnchantmentLevel(item1);
            }
            if (hasEnchantment(item2)) {
                fire_time += getEnchantmentLevel(item2);
            }
            if (hasEnchantment(item3)) {
                fire_time += getEnchantmentLevel(item3);
            }
            if (hasEnchantment(item4)) {
                fire_time += getEnchantmentLevel(item4);
            }

            event.getDamager().setFireTicks(fire_time * 20);
        }
    }

    @Override
    public boolean canEnchantItemInternal(ItemStack item) {
        return ItemsUtil.isItemArmor(item);
    }

    @Override
    public int anvilMergeCost(int level) {
        return 10 * level;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR;
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
