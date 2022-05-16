package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.custom.hats.*;
import com.connexal.raveldatapack.custom.items.CustomItem;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class HatManager implements Listener {
    private final Map<String, CustomItem> hats = new HashMap<>();

    private int registeredCount = 0;

    /**
     * Initialise all the custom hats
     * @return The number of custom hats initialised
     */
    public int init() {
        this.registerCustomHat(new PartyHat(175207));
        this.registerCustomHat(new SantaHat(528514));
        this.registerCustomHat(new TopHat(267405));

        this.registerCustomHat(new ElfEars(845619));
        this.registerCustomHat(new Antlers(798452));
        this.registerCustomHat(new BunnyEars(628506));

        this.registerCustomHat(new EmeraldMask(724045));

        RavelDatapack.getInstance().getServer().getPluginManager().registerEvents(this, RavelDatapack.getInstance());

        return registeredCount;
    }

    /**
     * Register a custom hat
     * @param item The custom hat to register
     * @see CustomItem
     */
    public void registerCustomHat(CustomItem item) {
        if (RavelDatapack.getInstance().getConfig().contains("hat." + item.getNamespaceKey())) {
            if (!RavelDatapack.getInstance().getConfig().getBoolean("hat." + item.getNamespaceKey())) {
                return;
            }
        } else {
            RavelDatapack.getInstance().getConfig().set("hat." + item.getNamespaceKey(), false);
            RavelDatapack.getInstance().saveConfig();
            return;
        }

        item.create();

        this.hats.put(item.getNamespaceKey(), item);
        registeredCount++;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getAction() != Action.RIGHT_CLICK_AIR && playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack hand = playerInteractEvent.getItem();
        if (hand == null) {
            return;
        }
        if (!this.isCustomHat(hand)) {
            return;
        }

        Player player = playerInteractEvent.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        if (playerInventory.getHelmet() == null) {
            playerInteractEvent.setCancelled(true);

            ItemStack newItem = hand.clone();
            newItem.setAmount(1);
            playerInventory.setHelmet(newItem);
            if (player.getGameMode() != GameMode.CREATIVE) {
                hand.setAmount(hand.getAmount() - 1);
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent inventoryClickEvent) {
        Inventory inventory = inventoryClickEvent.getClickedInventory();
        ItemStack cursor = inventoryClickEvent.getCursor();
        if (inventory == null) {
            return;
        }
        if (!inventory.getType().equals(InventoryType.PLAYER) || inventoryClickEvent.getSlotType() != InventoryType.SlotType.ARMOR) {
            return;
        }
        if (cursor == null) {
            return;
        }
        if (!this.isCustomHat(cursor)) {
            return;
        }

        if (inventoryClickEvent.getSlot() != 39) {
            inventoryClickEvent.setCancelled(true);
            return;
        }

        ItemStack current = inventoryClickEvent.getCurrentItem();
        inventoryClickEvent.setCancelled(true);

        if (current == null || current.getType() == Material.AIR) {
            inventoryClickEvent.getWhoClicked().getInventory().setHelmet(cursor.clone());
            cursor.setAmount(0);
        } else {
            ItemStack newCursor = current.clone();
            inventoryClickEvent.getWhoClicked().getInventory().setHelmet(cursor.clone());
            inventoryClickEvent.getWhoClicked().setItemOnCursor(newCursor);
        }
    }

    /**
     * Get all the custom hats
     * @return A {@link Map<String, CustomItem>} of all the custom hats registered (by namespace key)
     */
    public Map<String, CustomItem> getItems() {
        return this.hats;
    }

    /**
     * Check if an {@link ItemStack} is a custom hat
     * @param item The {@link ItemStack} to check
     * @return True if the {@link ItemStack} is a custom hat, false otherwise
     */
    public boolean isCustomHat(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        if (!item.getItemMeta().hasCustomModelData()) {
            return false;
        }
        Integer customModelData = item.getItemMeta().getCustomModelData();

        for (CustomItem customItem : this.hats.values()) {
            if (customItem.getCustomModelData().equals(customModelData)) {
                return true;
            }
        }

        return false;
    }
}
