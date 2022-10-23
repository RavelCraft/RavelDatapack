package com.connexal.raveldatapack.api.managers;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.items.CustomHatItem;
import com.connexal.raveldatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemManager implements Listener {
    private final Map<Integer, CustomItem> items = new HashMap<>();

    public void registerCustomItem(CustomItem item) {
        if (RavelDatapackAPI.getConfig().contains("item." + item.getNamespaceKey())) {
            if (!RavelDatapackAPI.getConfig().getBoolean("item." + item.getNamespaceKey())) {
                return;
            }
        } else {
            RavelDatapackAPI.getConfig().set("item." + item.getNamespaceKey(), false);
            RavelDatapackAPI.saveConfig();
            return;
        }

        if (this.items.containsKey(item.getCustomModelData())) {
            RavelDatapackAPI.getLogger().warning("Custom model data " + item.getCustomModelData() + " is already registered for " + item.getNamespaceKey() + "!");
            return;
        }

        item.create();

        this.items.put(item.getCustomModelData(), item);
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

    @EventHandler
    public void onPrepareSmithingEvent(PrepareSmithingEvent event) {
        ItemStack equipment = event.getInventory().getInputEquipment();
        ItemStack mineral = event.getInventory().getInputMineral();

        if (equipment == null || mineral == null) { //Don't do anything if either of the slots are empty
            return;
        }

        CustomItem customEquipment = null;
        if (equipment.hasItemMeta() && equipment.getItemMeta().hasCustomModelData()) {
            customEquipment = this.getItems().get(equipment.getItemMeta().getCustomModelData());
        }
        CustomItem customMineral = null;
        if (mineral.hasItemMeta() && mineral.getItemMeta().hasCustomModelData()) {
            customMineral = this.getItems().get(mineral.getItemMeta().getCustomModelData());
        }

        Recipe targetRecipe = null;
        Iterator<Recipe> recipeList = RavelDatapackAPI.getServer().recipeIterator();
        while (recipeList.hasNext()) {
            if (recipeList.next() instanceof SmithingRecipe smithingRecipe) {
                List<Material> baseMaterial = null;
                List<ItemStack> baseItem = null;
                List<Material> additionMaterial = null;
                List<ItemStack> additionItem = null;

                if (smithingRecipe.getBase() instanceof RecipeChoice.ExactChoice baseChoice) {
                    if (baseChoice.getChoices().size() > 0) {
                        baseItem = baseChoice.getChoices();
                    }
                } else if (smithingRecipe.getBase() instanceof RecipeChoice.MaterialChoice baseChoice) {
                    if (baseChoice.getChoices().size() > 0) {
                        baseMaterial = baseChoice.getChoices();
                    }
                }

                if (smithingRecipe.getAddition() instanceof RecipeChoice.ExactChoice additionChoice) {
                    if (additionChoice.getChoices().size() > 0) {
                        additionItem = additionChoice.getChoices();
                    }
                } else if (smithingRecipe.getAddition() instanceof RecipeChoice.MaterialChoice additionChoice) {
                    if (additionChoice.getChoices().size() > 0) {
                        additionMaterial = additionChoice.getChoices();
                    }
                }

                if (customEquipment == null || !customEquipment.isNewItem()) {
                    if (baseMaterial != null) {
                        if (!baseMaterial.contains(equipment.getType())) {
                            continue;
                        }
                    } else if (baseItem != null) {
                        if (!baseItem.contains(equipment)) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (baseItem != null) {
                        boolean found = false;
                        for (ItemStack item : baseItem) {
                            if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
                                if (item.getItemMeta().getCustomModelData() == customEquipment.getCustomModelData()) {
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if (!found) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }

                if (customMineral == null || !customMineral.isNewItem()) {
                    if (additionMaterial != null) {
                        if (!additionMaterial.contains(mineral.getType())) {
                            continue;
                        }
                    } else if (additionItem != null) {
                        if (!additionItem.contains(mineral)) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (additionItem != null) {
                        boolean found = false;
                        for (ItemStack item : additionItem) {
                            if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
                                if (item.getItemMeta().getCustomModelData() == customMineral.getCustomModelData()) {
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if (!found) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }

                targetRecipe = smithingRecipe;
                break;
            }
        }

        if (targetRecipe != null) {
            ItemStack result = targetRecipe.getResult();
            ItemMeta resultMeta = result.getItemMeta();
            if (resultMeta == null) {
                resultMeta = RavelDatapackAPI.getServer().getItemFactory().getItemMeta(result.getType());
            }

            Map<Enchantment, Integer> inputEnchantments = equipment.getEnchantments();
            for (Map.Entry<Enchantment, Integer> entry : inputEnchantments.entrySet()) {
                resultMeta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            RavelDatapackAPI.getEnchantmentManager().updateItemLoreEnchants(result);
            if (!equipment.getItemMeta().getDisplayName().startsWith(ChatColor.COLOR_CHAR + "")) {
                resultMeta.setDisplayName(equipment.getItemMeta().getDisplayName());
            }

            result.setItemMeta(resultMeta);
            event.setResult(result);
        } else if ((customEquipment != null && customEquipment.isNewItem()) || (customMineral != null && customMineral.isNewItem())) {
            event.setResult(null);
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

    public boolean isCustomHat(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        if (!item.getItemMeta().hasCustomModelData()) {
            return false;
        }

        int customModelData = item.getItemMeta().getCustomModelData();
        for (CustomItem customItem : this.items.values()) {
            if (customItem instanceof CustomHatItem && customItem.getCustomModelData() == customModelData) {
                return true;
            }
        }

        return false;
    }
}
