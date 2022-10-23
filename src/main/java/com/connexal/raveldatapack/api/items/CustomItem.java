package com.connexal.raveldatapack.api.items;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CustomItem {
    protected ItemStack itemStack;
    protected final int customModelData;
    protected final String namespaceKey;
    protected final boolean isNewItem; //If it is a completely new item, or just an extension of an existing item

    public CustomItem(int customModelData, String namespaceKey, boolean isNewItem) {
        this.customModelData = customModelData;
        this.namespaceKey = namespaceKey;
        this.isNewItem = isNewItem;
    }

    public CustomItem(int customModelData, String namespaceKey) {
        this(customModelData, namespaceKey, true);
    }

    public abstract void create();

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }

    public String getNamespaceKey() {
        return this.namespaceKey;
    }

    public NamespacedKey getNamespacedKey() {
        return NamespacedKey.minecraft(this.namespaceKey);
    }

    public boolean isNewItem() {
        return this.isNewItem;
    }

    public void createItem(Material base) {
        this.itemStack = new ItemStack(base, 1);
    }

    public ItemMeta createItemMeta() {
        return this.createItemMeta(true, true);
    }

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

        meta.setCustomModelData(this.customModelData);

        return meta;
    }

    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    public void setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
    }

    public void setItemLore(ItemMeta meta, String... lore) {
        this.setItemLore(true, meta, true, lore);
    }

    public void setItemLore(boolean replaceExisting, ItemMeta meta, boolean addSpacing, String... lore) {
        List<Component> loreList;
        if (replaceExisting) {
            loreList = new ArrayList<>();
        } else {
            loreList = meta.lore();
        }

        if (addSpacing) {
            loreList.add(Component.text(""));
        }

        for (String line : lore) {
            loreList.add(Component.text(ChatColor.WHITE + line));
        }
        meta.lore(loreList);
    }

    public void setAttackDamage(ItemMeta meta, double damage, EquipmentSlot slot) {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", damage - 0.5, AttributeModifier.Operation.ADD_NUMBER, slot));
    }

    public void setAttackSpeed(ItemMeta meta, double speed, EquipmentSlot slot) {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", (4 - speed) * -1, AttributeModifier.Operation.ADD_NUMBER, slot));
    }

    public void allowEnchantment(Enchantment enchantment) {

    }
}
