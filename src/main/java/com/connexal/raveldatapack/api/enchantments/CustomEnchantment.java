package com.connexal.raveldatapack.api.enchantments;

import com.connexal.raveldatapack.api.utils.StringUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class CustomEnchantment extends Enchantment {
    private final String name;
    private final String namespace;
    private final Enchantment enchantment;

    protected final List<Enchantment> conflictingEnchants = new ArrayList<>();

    public CustomEnchantment(String namespace, String name) {
        super(NamespacedKey.minecraft(namespace));

        this.name = name;
        this.namespace = namespace;
        this.enchantment = this;
    }

    public abstract void create();

    public String formatEnchantmentName(int level) {
        if (this.getMaxLevel() == 1) {
            return this.getName();
        } else {
            return this.getName() + " " + StringUtil.toRoman(level);
        }
    }

    public ItemStack getBook(int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.GRAY + this.formatEnchantmentName(level)));
        meta.lore(lore);

        book.setItemMeta(meta);

        EnchantmentStorageMeta enchMeta = (EnchantmentStorageMeta) book.getItemMeta();
        enchMeta.addStoredEnchant(this, level, false);
        book.setItemMeta(enchMeta);

        return book;
    }

    protected boolean hasEnchantment(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (!item.hasItemMeta()) {
            return false;
        }
        return item.getItemMeta().hasEnchant(this.enchantment);
    }

    protected int getEnchantmentLevel(ItemStack item) {
        if (!hasEnchantment(item)) {
            return 0;
        }
        return item.getItemMeta().getEnchantLevel(this.enchantment);
    }

    public abstract @NotNull EnchantmentTarget getItemTarget();

    public abstract int getMaxLevel();

    public abstract boolean canEnchantItemInternal(ItemStack item);

    public abstract int getAnvilMergeCost(int level);

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return this.conflictingEnchants.contains(other);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        if (item.getType() == Material.AIR) {
            return false;
        }
        if (item.getType() == Material.ENCHANTED_BOOK || item.getType() == Material.BOOK) {
            return true;
        }

        return canEnchantItemInternal(item);
    }

    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public @NotNull Component displayName(int i) {
        return Component.text(this.name);
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return Set.of();
    }

    @Override
    public @NotNull String translationKey() {
        return "enchantment." + this.namespace + "." + this.name;
    }

    public abstract int getTradeCost(int level);
}
