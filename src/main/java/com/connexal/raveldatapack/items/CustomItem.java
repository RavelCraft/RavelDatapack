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
    protected int customModelData;
    protected String namespaceKey;
    protected boolean isHat = false;
    protected final RavelDatapack instance = RavelDatapack.getInstance();

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

    public boolean isHat() {
        return this.isHat;
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

        return meta;
    }

    public ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    public void setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
    }

    public void setItemLore(ItemMeta meta, String... lore) {
        this.setItemLore(meta, true, lore);
    }

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

    public void setAttackDamage(ItemMeta meta, double damage, EquipmentSlot slot) {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage - 2, AttributeModifier.Operation.ADD_NUMBER, slot));
    }

    public void setAttackSpeed(ItemMeta meta, double speed, EquipmentSlot slot) {
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", speed - 4, AttributeModifier.Operation.ADD_NUMBER, slot));
    }
}
