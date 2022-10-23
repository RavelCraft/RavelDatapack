package com.connexal.raveldatapack.api.managers;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.enchantments.CustomEnchantment;
import com.connexal.raveldatapack.api.utils.EnchantmentLoreUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.WanderingTrader;
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
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnchantmentManager implements Listener {
    private final Map<String, CustomEnchantment> enchantments = new HashMap<>();

    public boolean registerCustomEnchantment(CustomEnchantment enchantment) {
        if (RavelDatapackAPI.getConfig().contains("enchantments." + enchantment.getKey())) {
            if (!RavelDatapackAPI.getConfig().getBoolean("enchantments." + enchantment.getKey())) {
                return false;
            }
        } else {
            RavelDatapackAPI.getConfig().set("enchantments." + enchantment.getKey(), false);
            RavelDatapackAPI.saveConfig();
            return false;
        }

        enchantment.create();

        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);
        enchantments.put(enchantment.getNamespace(), enchantment);

        if (!registered) {
            return this.registerEnchantment(enchantment);
        } else {
            RavelDatapackAPI.getLogger().info("Enchantment already registered: \"" + enchantment.getKey() + "\"");
            return false;
        }
    }

    private boolean registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            Enchantment.registerEnchantment(enchantment);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            RavelDatapackAPI.getLogger().severe("Failed to register enchantment \"" + enchantment.getKey() + "\": Field value failure.");
            registered = false;
        } catch (IllegalStateException e) {
            RavelDatapackAPI.getLogger().severe("Failed to register enchantment \"" + enchantment.getKey() + "\": Not accepting new.");
            registered = false;
        }

        return registered;
    }

    public void unregisterEnchantments() {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : this.enchantments.values()) {
                byKey.remove(enchantment.getKey());
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : this.enchantments.values()) {
                byName.remove(enchantment.getName());
            }
        } catch (Exception ignored) {
        }
    }

    public boolean enchantItemStack(ItemStack item, CustomEnchantment enchantment, int level, boolean force) {
        if (item == null) {
            return false;
        }
        if (!item.hasItemMeta()) {
            item.setItemMeta(RavelDatapackAPI.getServer().getItemFactory().getItemMeta(item.getType()));
        }
        if (!force && !enchantment.canEnchantItem(item)) {
            return false;
        }

        this.removeEnchantItemStack(item, enchantment);

        ItemMeta meta = item.getItemMeta();
        if (meta instanceof EnchantmentStorageMeta enchantMeta) {
            if (!enchantMeta.addStoredEnchant(enchantment, level, true)) {
                return false;
            }
        } else {
            if (!meta.addEnchant(enchantment, level, true)) {
                return false;
            }
        }
        item.setItemMeta(meta);

        this.updateItemLoreEnchants(item);

        return true;
    }

    public void removeEnchantItemStack(ItemStack item, CustomEnchantment enchantment) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasEnchant(enchantment)) {
            return;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.remove(enchantment.getKey());

        if (meta instanceof EnchantmentStorageMeta storageMeta) {
            storageMeta.removeStoredEnchant(enchantment);
        } else {
            meta.removeEnchant(enchantment);
        }

        item.setItemMeta(meta);
    }

    public boolean isEnchantable(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return false;
        }

        return item.getType() == Material.ENCHANTED_BOOK || Stream.of(EnchantmentTarget.values()).anyMatch(target -> target.includes(item));
    }

    public Map<Enchantment, Integer> getItemEnchants(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return (meta instanceof EnchantmentStorageMeta meta2) ? meta2.getStoredEnchants() : meta.getEnchants();
    }

    public Map<CustomEnchantment, Integer> getItemCustomEnchants(ItemStack item) {
        return this.getItemEnchants(item).entrySet().stream()
                .filter(entry -> entry.getKey() instanceof CustomEnchantment)
                .map(entry -> new AbstractMap.SimpleEntry<>((CustomEnchantment) entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, nev) -> nev, LinkedHashMap::new));
    }

    public void updateItemLoreEnchants(ItemStack item) {
        for (CustomEnchantment enchantment : this.getEnchantments()) {
            EnchantmentLoreUtil.delLore(item, enchantment.getKey().getKey());
        }

        Map<CustomEnchantment, Integer> customEnchantments = getItemCustomEnchants(item).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (has, add) -> add, LinkedHashMap::new));

        for (Map.Entry<CustomEnchantment, Integer> entry : customEnchantments.entrySet()) {
            CustomEnchantment enchantment = entry.getKey();
            int level = entry.getValue();

            EnchantmentLoreUtil.addLore(item, enchantment.getKey().getKey(), ChatColor.GRAY + enchantment.formatEnchantmentName(level), 0);
        }
    }

    public void reformatItemNameColours(ItemStack original, ItemStack result) {
        if (!original.hasItemMeta() || !result.hasItemMeta()) {
            return;
        }
        ItemMeta originalMeta = original.getItemMeta();
        ItemMeta resultMeta = result.getItemMeta();

        if (!originalMeta.hasDisplayName()) {
            return;
        }

        String originalName = resultMeta.getDisplayName();

        String name;
        if (resultMeta.hasDisplayName()) {
            String tmpName = originalName;
            name = resultMeta.getDisplayName();

            while (tmpName.contains(ChatColor.COLOR_CHAR + "")) {
                int index = tmpName.indexOf(ChatColor.COLOR_CHAR + "");

                if (index == 0) {
                    tmpName = tmpName.substring(2);
                    name = name.substring(1);
                } else {
                    tmpName = tmpName.substring(0, index) + name.substring(index + 2);
                    name = name.substring(0, index) + name.substring(index + 1);
                }
            }
        } else {
            name = ChatColor.stripColor(originalName);
        }

        resultMeta.setDisplayName(name);
        result.setItemMeta(resultMeta);
    }

    public List<CustomEnchantment> getEnchantments() {
        return new ArrayList<>(enchantments.values());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVillagerAcquireTradeEvent(VillagerAcquireTradeEvent event) {
        if (this.getEnchantments().size() == 0) {
            return;
        }

        if (event.getEntity() instanceof WanderingTrader trader) {
            event.setCancelled(true);

            List<MerchantRecipe> recipes = new ArrayList<>();
            Random random = new Random();

            for (CustomEnchantment enchantment : this.getEnchantments()) {
                int level = random.nextInt(enchantment.getStartLevel(), enchantment.getMaxLevel() + 1);

                MerchantRecipe recipe = new MerchantRecipe(enchantment.getBook(level), 0, random.nextInt(5) + 10, true);
                recipe.addIngredient(new ItemStack(Material.BOOK));
                recipe.addIngredient(new ItemStack(Material.EMERALD, enchantment.getTradeCost(level)));
                recipes.add(recipe);
            }

            trader.setRecipes(recipes);

            trader.setCanDrinkMilk(true);
            trader.setCanDrinkPotion(true);
            trader.setDespawnDelay(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
        AnvilInventory inv = event.getInventory();

        ItemStack first = inv.getItem(0);
        ItemStack second = inv.getItem(1);
        ItemStack result = event.getResult();

        if (first == null || !this.isEnchantable(first) || first.getAmount() > 1) {
            return;
        }

        if ((second == null || second.getType().isAir() || !this.isEnchantable(second)) && (result != null && result.getType() == first.getType())) {
            ItemStack newResult = result.clone();
            this.getItemCustomEnchants(first).forEach((hasEach, hasLevel) -> {
                this.enchantItemStack(newResult, hasEach, hasLevel, true);
            });
            this.reformatItemNameColours(first, newResult);
            event.setResult(newResult);
            return;
        }

        if (second == null || second.getAmount() > 1 || !this.isEnchantable(second)) {
            return;
        }

        if (first.getType() == Material.ENCHANTED_BOOK && second.getType() != first.getType()) {
            return;
        }

        if (result == null || result.getType() == Material.AIR) {
            result = first.clone();
        }

        Map<CustomEnchantment, Integer> enchAdd = this.getItemCustomEnchants(first);
        int repairCost = inv.getRepairCost();

        if (second.getType() == Material.ENCHANTED_BOOK || second.getType() == first.getType()) {
            for (Map.Entry<CustomEnchantment, Integer> en : this.getItemCustomEnchants(second).entrySet()) {
                enchAdd.merge(en.getKey(), en.getValue(), (oldLvl, newLvl) -> (oldLvl.equals(newLvl)) ? (oldLvl + 1) : (Math.max(oldLvl, newLvl)));
            }
        }

        for (Map.Entry<CustomEnchantment, Integer> ent : enchAdd.entrySet()) {
            CustomEnchantment enchant = ent.getKey();
            int level = Math.min(enchant.getMaxLevel(), ent.getValue());
            if (this.enchantItemStack(result, enchant, level, false)) {
                repairCost += enchant.getAnvilMergeCost(level);
            }
        }

        if (!first.equals(result)) {
            this.updateItemLoreEnchants(result);
            this.reformatItemNameColours(first, result);
            event.setResult(result);

            int newRepairCost = repairCost;
            RavelDatapackAPI.getServer().getScheduler().runTask(RavelDatapackAPI.getPlugin(), () -> {
                inv.setRepairCost(newRepairCost);
            });
        }
    }

    private void updateGrindstone(Inventory inventory) {
        RavelDatapackAPI.getServer().getScheduler().runTask(RavelDatapackAPI.getPlugin(), () -> {
            ItemStack result = inventory.getItem(2);
            if (result == null || result.getType().isAir()) return;

            Map<CustomEnchantment, Integer> curses = new HashMap<>();
            for (int slot = 0; slot < 2; slot++) {
                ItemStack source = inventory.getItem(slot);
                if (source == null || source.getType().isAir()) continue;

                curses.putAll(this.getItemCustomEnchants(source));
            }
            curses.entrySet().removeIf(entry -> !entry.getKey().isCursed());
            curses.forEach((enchant, level) -> {
                this.enchantItemStack(result, enchant, level, true);
            });
            this.updateItemLoreEnchants(result);
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
