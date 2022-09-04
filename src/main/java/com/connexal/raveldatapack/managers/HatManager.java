package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import com.connexal.raveldatapack.items.hats.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class HatManager implements Listener {
    private final Map<Integer, CustomItem> hats = new HashMap<>();

    private int registeredCount = 0;

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

        if (this.hats.containsKey(item.getCustomModelData())) {
            RavelDatapack.getInstance().getLogger().warning("Custom model data " + item.getCustomModelData() + " is already registered for " + item.getNamespaceKey() + "!");
            return;
        }

        item.create();

        this.hats.put(item.getCustomModelData(), item);
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
    public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
        event.setCancelled(this.onInventoryClickEventInternal(event.getClickedInventory(), event.getCursor(), event.getSlotType(), event.getSlot(), event.getCurrentItem(), event.getWhoClicked()));
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        event.setCancelled(this.onInventoryClickEventInternal(event.getClickedInventory(), event.getCursor(), event.getSlotType(), event.getSlot(), event.getCurrentItem(), event.getWhoClicked()));
    }

    private boolean onInventoryClickEventInternal(Inventory inventory, ItemStack cursor, InventoryType.SlotType slotType, int slot, ItemStack current, HumanEntity whoClicked) {
        if (inventory == null) {
            return false;
        }
        if (!inventory.getType().equals(InventoryType.PLAYER) || slotType != InventoryType.SlotType.ARMOR) {
            return false;
        }
        if (cursor == null) {
            return false;
        }
        if (!this.isCustomHat(cursor)) {
            return false;
        }

        if (slot != 39) {
            return true;
        }

        if (current == null || current.getType() == Material.AIR) {
            whoClicked.getInventory().setHelmet(cursor.clone());
            cursor.setAmount(0);
        } else {
            ItemStack newCursor = current.clone();
            whoClicked.getInventory().setHelmet(cursor.clone());
            whoClicked.setItemOnCursor(newCursor);
        }

        return true;
    }

    public Map<Integer, CustomItem> getItems() {
        return this.hats;
    }

    public boolean isCustomHat(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        if (!item.getItemMeta().hasCustomModelData()) {
            return false;
        }

        int customModelData = item.getItemMeta().getCustomModelData();
        for (CustomItem customItem : this.hats.values()) {
            if (customItem.getCustomModelData() == customModelData) {
                return true;
            }
        }

        return false;
    }
}
