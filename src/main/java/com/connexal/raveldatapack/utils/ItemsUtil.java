package com.connexal.raveldatapack.utils;

import org.bukkit.inventory.ItemStack;

public class ItemsUtil {
    /**
     * Get if an {@link ItemStack} is a tool
     *
     * @param item The {@link ItemStack} to check
     * @return True if the item is a tool, false if not
     */
    public static boolean isItemATool(ItemStack item) {
        if (item == null) {
            return false;
        }
        String name = item.getType().toString();

        return name.endsWith("_SHOVEL") || name.endsWith("_PICKAXE") || name.endsWith("_AXE");
    }

    /**
     * Get if an {@link ItemStack} is a weapon
     *
     * @param item The {@link ItemStack} to check
     * @return True if the item is a weapon, false if not
     */
    public static boolean isItemAWeapon(ItemStack item) {
        if (item == null) {
            return false;
        }
        String name = item.getType().toString();

        return name.endsWith("_SWORD");
    }

    /**
     * Get if an {@link ItemStack} is armor
     *
     * @param item The {@link ItemStack} to check
     * @return True if the item is armor, false if not
     */
    public static boolean isItemArmor(ItemStack item) {
        if (item == null) {
            return false;
        }
        String name = item.getType().toString();

        return name.endsWith("_HELMET") || name.endsWith("_CHESTPLATE") || name.endsWith("_LEGGINGS") || name.endsWith("_BOOTS");
    }

    /**
     * Get the custom model data of an {@link ItemStack}
     *
     * @param item The {@link ItemStack}
     * @return The custom model data
     */
    public static Integer getCustomModelData(ItemStack item) {
        if (!item.hasItemMeta()) {
            return 0;
        }
        if (!item.getItemMeta().hasCustomModelData()) {
            return 0;
        }

        return item.getItemMeta().getCustomModelData();
    }
}
