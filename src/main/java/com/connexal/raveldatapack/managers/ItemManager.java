package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemManager {
    private final HashMap<String, CustomItem> items = new HashMap<>();

    private int registeredCount = 0;

    /**
     * Initialise all custom items
     *
     * @return The number of custom items initialised
     */
    public int init() {
        this.registerCustomItem(new BolterItem(295304));
        this.registerCustomItem(new BoltItem(450256));
        this.registerCustomItem(new BoltPistolItem(485103));

        this.registerCustomItem(new ChopperItem(695205));

        this.registerCustomItem(new PowerSwordItem(806528));

        this.registerCustomItem(new StormbreakerItem(952356));
        this.registerCustomItem(new ThunderHammerItem(426754));
        this.registerCustomItem(new FireballItem(367026));

        this.registerCustomItem(new PikeItem(209846));

        this.registerCustomItem(new PlateItem(256289));
        this.registerCustomItem(new TurkeyOnAPlateItem(259822));

        this.registerCustomItem(new EnderiteIngotItem(350687));
        this.registerCustomItem(new EnderiteAxeItem(246060));
        this.registerCustomItem(new EnderiteHoeItem(246061));
        this.registerCustomItem(new EnderitePickaxeItem(246062));
        this.registerCustomItem(new EnderiteShovelItem(246063));
        this.registerCustomItem(new EnderiteSwordItem(246064));

        this.registerCustomItem(new SuperHammerItem(79786));

        return registeredCount;
    }

    /**
     * Register a {@link CustomItem}
     *
     * @param item {@link CustomItem} to register
     */
    public void registerCustomItem(CustomItem item) {
        if (RavelDatapack.getInstance().getConfig().contains("item." + item.getNamespaceKey())) {
            if (!RavelDatapack.getInstance().getConfig().getBoolean("item." + item.getNamespaceKey())) {
                return;
            }
        } else {
            RavelDatapack.getInstance().getConfig().set("item." + item.getNamespaceKey(), false);
            RavelDatapack.getInstance().saveConfig();
            return;
        }

        item.create();

        this.items.put(item.getNamespaceKey(), item);
        registeredCount++;
    }

    /**
     * Get all the custom items
     *
     * @return A {@link Map<String, CustomItem>} of all the custom items registered (by namespace key)
     */
    public HashMap<String, CustomItem> getItems() {
        return this.items;
    }

    public Integer getCustomModelData(String namespaceKey) {
        CustomItem item = this.items.get(namespaceKey);
        return item.getCustomModelData();
    }

    /**
     * Get an {@link ItemStack} of a {@link CustomItem} by its namespace key
     *
     * @param namespaceKey Namespace key of the {@link ItemStack}
     * @return The {@link ItemStack}
     */
    public ItemStack getItem(String namespaceKey) {
        if (!this.items.containsKey(namespaceKey)) {
            return null;
        }

        CustomItem item = this.items.get(namespaceKey);
        return item.getItemStack();
    }

    /**
     * Get the {@link ItemStack} of a {@link CustomItem} by its custom model data
     *
     * @param customModelData Custom model data of the {@link CustomItem}
     * @return The {@link ItemStack}
     */
    public ItemStack getItem(Integer customModelData) {
        for (CustomItem item : this.items.values()) {
            if (item.getCustomModelData().equals(customModelData)) {
                return item.getItemStack();
            }
        }

        return null;
    }

    /**
     * Get the {@link CustomItem} by its namespace key by its custom model data
     *
     * @param customModelData Custom model data of the {@link CustomItem}
     * @return The namespace key
     */
    public String getNamespaceKey(Integer customModelData) {
        for (CustomItem item : this.items.values()) {
            if (item.getCustomModelData().equals(customModelData)) {
                return item.getNamespaceKey();
            }
        }

        return null;
    }

    /**
     * Get if an {@link ItemStack} is a custom item
     *
     * @param item The {@link ItemStack} to check
     * @return True if the {@link ItemStack} is a custom item
     */
    public boolean isCustomItem(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        if (!item.getItemMeta().hasCustomModelData()) {
            return false;
        }
        Integer customModelData = item.getItemMeta().getCustomModelData();

        for (CustomItem customItem : this.items.values()) {
            if (customItem.getCustomModelData().equals(customModelData)) {
                return true;
            }
        }

        return false;
    }
}
