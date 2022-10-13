package com.connexal.raveldatapack.listeners;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SmithingListener implements Listener {
    @EventHandler
    public void onPrepareSmithingEvent(PrepareSmithingEvent event) {
        ItemStack equipment = event.getInventory().getInputEquipment();
        ItemStack mineral = event.getInventory().getInputMineral();

        if (equipment == null || mineral == null) { //Don't do anything if either of the slots are empty
            return;
        }

        CustomItem customEquipment = null;
        if (equipment.hasItemMeta() && equipment.getItemMeta().hasCustomModelData()) {
            customEquipment = RavelDatapack.getItemManager().getItems().get(equipment.getItemMeta().getCustomModelData());
        }
        CustomItem customMineral = null;
        if (mineral.hasItemMeta() && mineral.getItemMeta().hasCustomModelData()) {
            customMineral = RavelDatapack.getItemManager().getItems().get(mineral.getItemMeta().getCustomModelData());
        }

        Recipe targetRecipe = null;
        Iterator<Recipe> recipeList = RavelDatapack.getInstance().getServer().recipeIterator();
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
            RavelDatapack.getLog().info("Found recipe for " + equipment.getType().toString() + " and " + mineral.getType().toString());

            ItemStack result = targetRecipe.getResult();
            ItemMeta resultMeta = result.getItemMeta();
            if (resultMeta == null) {
                resultMeta = RavelDatapack.getInstance().getServer().getItemFactory().getItemMeta(result.getType());
            }

            Map<Enchantment, Integer> inputEnchantments = equipment.getEnchantments();
            for (Map.Entry<Enchantment, Integer> entry : inputEnchantments.entrySet()) {
                resultMeta.addEnchant(entry.getKey(), entry.getValue(), true);
                RavelDatapack.getLog().info("Added enchantment " + entry.getKey().getKey().getKey() + " to result");
            }
            RavelDatapack.getEnchantmentManager().updateItemLoreEnchants(result);
            if (!equipment.getItemMeta().getDisplayName().startsWith(ChatColor.COLOR_CHAR + "")) {
                resultMeta.setDisplayName(equipment.getItemMeta().getDisplayName());
            }

            result.setItemMeta(resultMeta);
            event.setResult(result);
        } else if ((customEquipment != null && customEquipment.isNewItem()) || (customMineral != null && customMineral.isNewItem())) {
            RavelDatapack.getLog().info("BLOCKED recipe for " + equipment.getType().toString() + " and " + mineral.getType().toString());
            event.setResult(null);
        }
    }
}
