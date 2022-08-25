package com.connexal.raveldatapack.listeners;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.*;

public class EnchantmentListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVillagerAcquireTradeEvent(VillagerAcquireTradeEvent event) {
        if (RavelDatapack.getEnchantmentManager().getEnchantments().size() == 0) {
            return;
        }

        if (event.getEntity() instanceof Villager villager) {
            if (villager.getProfession().equals(Villager.Profession.LIBRARIAN) && villager.getVillagerLevel() == 1 && villager.getVillagerExperience() == 0) {
                RavelDatapack.getLog().info(villager.getVillagerLevel() + " " + villager.getVillagerExperience());
                Random rand = new Random();
                if (rand.nextInt(50) == 0) {
                    MerchantRecipe recipe = new MerchantRecipe(RavelDatapack.getEnchantmentManager().getRandomCustomEnchantment(), 1);
                    ItemStack emerald = new ItemStack(Material.EMERALD);
                    emerald.setAmount(40);
                    recipe.addIngredient(emerald);
                    recipe.addIngredient(new ItemStack(Material.BOOK));

                    List<MerchantRecipe> recipeList = new ArrayList<>(villager.getRecipes());
                    recipeList.clear();
                    recipeList.add(recipe);
                    villager.setRecipes(recipeList);

                    villager.setVillagerLevel(5);
                    event.setCancelled(true);
                } else {
                    villager.setVillagerExperience(1);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
        AnvilInventory inv = event.getInventory();

        ItemStack first = inv.getItem(0);
        ItemStack second = inv.getItem(1);
        ItemStack result = event.getResult();

        if (first == null || !RavelDatapack.getEnchantmentManager().isEnchantable(first) || first.getAmount() > 1) {
            return;
        }

        if ((second == null || second.getType().isAir() || !RavelDatapack.getEnchantmentManager().isEnchantable(second)) && (result != null && result.getType() == first.getType())) {
            ItemStack newResult = result.clone();
            RavelDatapack.getEnchantmentManager().getItemCustomEnchants(first).forEach((hasEach, hasLevel) -> {
                RavelDatapack.getEnchantmentManager().enchantItemStack(newResult, hasEach, hasLevel, true);
            });
            RavelDatapack.getEnchantmentManager().reformatItemNameColours(first, newResult);
            event.setResult(newResult);
            return;
        }

        if (second == null || second.getAmount() > 1 || !RavelDatapack.getEnchantmentManager().isEnchantable(second)) {
            return;
        }

        if (first.getType() == Material.ENCHANTED_BOOK && second.getType() != first.getType()) {
            return;
        }

        if (result == null || result.getType() == Material.AIR) {
            result = first.clone();
        }

        Map<CustomEnchantment, Integer> enchAdd = RavelDatapack.getEnchantmentManager().getItemCustomEnchants(first);
        int repairCost = inv.getRepairCost();

        if (second.getType() == Material.ENCHANTED_BOOK || second.getType() == first.getType()) {
            for (Map.Entry<CustomEnchantment, Integer> en : RavelDatapack.getEnchantmentManager().getItemCustomEnchants(second).entrySet()) {
                enchAdd.merge(en.getKey(), en.getValue(), (oldLvl, newLvl) -> (oldLvl.equals(newLvl)) ? (oldLvl + 1) : (Math.max(oldLvl, newLvl)));
            }
        }

        for (Map.Entry<CustomEnchantment, Integer> ent : enchAdd.entrySet()) {
            CustomEnchantment enchant = ent.getKey();
            int level = Math.min(enchant.getMaxLevel(), ent.getValue());
            if (RavelDatapack.getEnchantmentManager().enchantItemStack(result, enchant, level, false)) {
                repairCost += enchant.getAnvilMergeCost(level);
            }
        }

        if (!first.equals(result)) {
            RavelDatapack.getEnchantmentManager().updateItemLoreEnchants(result);
            RavelDatapack.getEnchantmentManager().reformatItemNameColours(first, result);
            event.setResult(result);

            int newRepairCost = repairCost;
            RavelDatapack.getInstance().getServer().getScheduler().runTask(RavelDatapack.getInstance(), () -> {
                inv.setRepairCost(newRepairCost);
            });
        }
    }

    private void updateGrindstone(Inventory inventory) {
        RavelDatapack.getInstance().getServer().getScheduler().runTask(RavelDatapack.getInstance(), () -> {
            ItemStack result = inventory.getItem(2);
            if (result == null || result.getType().isAir()) return;

            Map<CustomEnchantment, Integer> curses = new HashMap<>();
            for (int slot = 0; slot < 2; slot++) {
                ItemStack source = inventory.getItem(slot);
                if (source == null || source.getType().isAir()) continue;

                curses.putAll(RavelDatapack.getEnchantmentManager().getItemCustomEnchants(source));
            }
            curses.entrySet().removeIf(entry -> !entry.getKey().isCursed());
            curses.forEach((enchant, level) -> {
                RavelDatapack.getEnchantmentManager().enchantItemStack(result, enchant, level, true);
            });
            RavelDatapack.getEnchantmentManager().updateItemLoreEnchants(result);
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Inventory inv = event.getInventory();

        if (inv.getType() == InventoryType.GRINDSTONE) {
            if (event.getRawSlot() == 2) {
                return;
            }

            this.updateGrindstone(inv);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDragEvent(InventoryDragEvent event) {
        Inventory inv = event.getInventory();

        if (inv.getType() == InventoryType.GRINDSTONE) {
            this.updateGrindstone(inv);
        }
    }
}
