package com.connexal.raveldatapack.api.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomToolItem extends CustomItem {
    public CustomToolItem(int customModelData, String namespaceKey) {
        super(customModelData, namespaceKey, true);
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
}
