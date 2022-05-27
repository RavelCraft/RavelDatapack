package com.connexal.raveldatapack.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemsUtil {
    /**
     * Get if an {@link ItemStack} is a tool
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
     * Get the enderite upgrade of an item
     * @param item The {@link Material} of the item
     * @return The custom model data of the item's upgrade or 0 if there is no upgrade
     */
    public static Integer getEnderiteUpgrade(Material item) {
        //TODO: todo
        if (item == Material.NETHERITE_AXE) {
            return 246060;
        } else if (item == Material.NETHERITE_HOE) {
            return 246061;
        } else if (item == Material.NETHERITE_PICKAXE) {
            return 246062;
        } else if (item == Material.NETHERITE_SHOVEL) {
            return 246063;
        } else if (item == Material.NETHERITE_SWORD) {
            return 246064;
        } else {
            return 0;
        }
    }

    /**
     * Get the custom model data of an {@link ItemStack}
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
