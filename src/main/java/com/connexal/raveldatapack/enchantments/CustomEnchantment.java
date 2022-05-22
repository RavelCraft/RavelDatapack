package com.connexal.raveldatapack.enchantments;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.utils.StringUtil;
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
    protected final RavelDatapack instance;
    protected Enchantment enchantment;

    protected List<Enchantment> conflictingEnchants = new ArrayList<>();

    public CustomEnchantment(String namespace, String name) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.namespace = namespace;
        this.instance = RavelDatapack.getInstance();
    }

    /**
     * Create the enchantment
     */
    public abstract void create();

    /**
     * Get the enchanted book {@link ItemStack}
     * @param level The enchantment level
     * @return The enchanted book
     */
    public ItemStack getBook(int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.GRAY + RavelDatapack.getEnchantmentManager().formatEnchantmentName(this, this.getMaxLevel())));
        meta.lore(lore);

        book.setItemMeta(meta);

        EnchantmentStorageMeta enchMeta = (EnchantmentStorageMeta) book.getItemMeta();
        enchMeta.addStoredEnchant(this, level, false);
        book.setItemMeta(enchMeta);

        return book;
    }

    /**
     * Test to see if an {@link ItemStack} has the enchantment
     * @param item The item to test
     * @return True if the item has the enchantment
     */
    protected boolean hasEnchantment(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (!item.hasItemMeta()) {
            return false;
        }
        return item.getItemMeta().hasEnchant(this.enchantment);
    }

    /**
     * Get the enchantment level of an enchanted book {@link ItemStack}
     * @param item The enchanted book
     * @return The enchantment level
     */
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
    public @NotNull NamespacedKey getKey() {
        return super.getKey();
    }

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
}
