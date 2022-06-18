package com.connexal.raveldatapack.items;

import com.connexal.raveldatapack.RavelDatapack;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CustomItem {
    protected ItemStack itemStack;
    protected Integer customModelData;
    protected String namespaceKey;
    protected final RavelDatapack instance = RavelDatapack.getInstance();

    /**
     * Create the {@link ItemStack}
     */
    public abstract void create();

    /**
     * Get the generated {@link ItemStack}
     *
     * @return The item
     */
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    /**
     * Get the custom model data
     *
     * @return The custom model data
     */
    public Integer getCustomModelData() {
        return this.customModelData;
    }

    /**
     * Get the namespace key
     *
     * @return The namespace key
     */
    public String getNamespaceKey() {
        return this.namespaceKey;
    }

    /**
     * Create an {@link ItemMeta}
     *
     * @return The created {@link ItemMeta}
     */
    public ItemMeta createItemMeta() {
        return this.createItemMeta(true, true);
    }

    /**
     * Create an {@link ItemMeta}
     *
     * @param hideFlags   Hide the item information
     * @param unbreakable Is the item unbreakable
     * @return The created item meta
     */
    public ItemMeta createItemMeta(boolean hideFlags, boolean unbreakable) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (hideFlags) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        if (unbreakable) {
            meta.setUnbreakable(true);
        }

        return meta;
    }

    /**
     * Get the {@link ItemMeta}
     *
     * @return The {@link ItemMeta}
     */
    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    /**
     * Set the {@link ItemMeta}
     *
     * @param itemMeta The {@link ItemMeta}
     */
    public void setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
    }

    /**
     * Set the item's lore
     *
     * @param meta The {@link ItemMeta}
     * @param lore The lore
     */
    public void setItemLore(ItemMeta meta, String... lore) {
        this.setItemLore(meta, true, lore);
    }

    /**
     * Set the item's lore
     *
     * @param meta       The {@link ItemMeta}
     * @param addSpacing Add spacing between the lore and the item name/enchantments
     * @param lore       The lore
     */
    public void setItemLore(ItemMeta meta, boolean addSpacing, String... lore) {
        List<Component> loreList = new ArrayList<>();
        if (addSpacing) {
            loreList.add(Component.text(""));
        }

        for (String line : lore) {
            loreList.add(Component.text(ChatColor.WHITE + line));
        }
        meta.lore(loreList);
    }

    /**
     * Set the item's attack damage
     *
     * @param meta   The {@link ItemMeta}
     * @param damage The damage to set
     * @param slot   The {@link EquipmentSlot} that the item must be in to make this work
     */
    public void setAttackDamage(ItemMeta meta, double damage, EquipmentSlot slot) {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage - 2, AttributeModifier.Operation.ADD_NUMBER, slot));
    }

    /**
     * Set the item's attack speed
     *
     * @param meta  The {@link ItemMeta}
     * @param speed The speed to set
     * @param slot  The {@link EquipmentSlot} that the item must be in to make this work
     */
    public void setAttackSpeed(ItemMeta meta, double speed, EquipmentSlot slot) {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", speed - 4, AttributeModifier.Operation.ADD_NUMBER, slot));
    }
}
