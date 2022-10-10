package com.connexal.raveldatapack.items;

import com.connexal.raveldatapack.RavelDatapack;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
    private ItemStack itemStack;
    private final int customModelData;
    private final String namespaceKey;
    private final boolean isHat;
    private final boolean isNewItem; //If it is a completely new item, or just an extension of an existing item

    protected final RavelDatapack instance = RavelDatapack.getInstance();

    public CustomItem(int customModelData, String namespaceKey, boolean isHat, boolean isNewItem) {
        this.customModelData = customModelData;
        this.namespaceKey = namespaceKey;
        this.isHat = isHat;
        this.isNewItem = isNewItem;
    }

    public CustomItem(int customModelData, String namespaceKey) {
        this(customModelData, namespaceKey, false, true);
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

    public boolean isHat() {
        return this.isHat;
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

    public ItemMeta createToolMeta(double attackDamage, double attackSpeed) {
        return this.createToolMeta(attackDamage, attackSpeed, false);
    }

    public ItemMeta createToolMeta(double attackDamage, double attackSpeed, boolean unbreakable) {
        ItemMeta meta = this.itemStack.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (unbreakable) {
            meta.setUnbreakable(true);
        }

        this.setAttackDamage(meta, attackDamage, EquipmentSlot.HAND);
        this.setAttackSpeed(meta, attackSpeed, EquipmentSlot.HAND);

        String stringAttackDamage = " " + attackDamage + "";
        if (stringAttackDamage.endsWith(".0")) {
            stringAttackDamage = stringAttackDamage.substring(0, stringAttackDamage.length() - 2);
        }
        String stringAttackSpeed = " " + attackSpeed + "";
        if (stringAttackSpeed.endsWith(".0")) {
            stringAttackSpeed = stringAttackSpeed.substring(0, stringAttackSpeed.length() - 2);
        }

        this.setItemLore(meta, ChatColor.GRAY + "When in Main Hand:", ChatColor.DARK_GREEN + stringAttackDamage + " Attack Damage", ChatColor.DARK_GREEN + stringAttackSpeed + " Attack Speed");

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
}
