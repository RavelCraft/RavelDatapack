package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {
    private final Map<Integer, CustomItem> items = new HashMap<>();

    private int registeredCount = 0;

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

        this.registerCustomItem(new SpeedBoostItem(202483));

        return registeredCount;
    }

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

        if (this.items.containsKey(item.getCustomModelData())) {
            RavelDatapack.getInstance().getLogger().warning("Custom model data " + item.getCustomModelData() + " is already registered for " + item.getNamespaceKey() + "!");
            return;
        }

        item.create();

        this.items.put(item.getCustomModelData(), item);
        registeredCount++;
    }

    public Map<Integer, CustomItem> getItems() {
        return this.items;
    }

    public Integer getCustomModelData(String namespaceKey) {
        for (CustomItem item : this.items.values()) {
            if (item.getNamespaceKey().equals(namespaceKey)) {
                return item.getCustomModelData();
            }
        }

        return null;
    }

    public ItemStack getItem(String namespaceKey) {
        for (CustomItem item : this.items.values()) {
            if (item.getNamespaceKey().equals(namespaceKey)) {
                return item.getItemStack();
            }
        }

        return null;
    }

    public ItemStack getItem(Integer customModelData) {
        CustomItem item = this.items.get(customModelData);
        return item == null ? null : item.getItemStack();
    }
}
