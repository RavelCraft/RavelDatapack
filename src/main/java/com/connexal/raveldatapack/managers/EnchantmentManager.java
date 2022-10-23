package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.enchantments.BlazingArmorEnchantment;
import com.connexal.raveldatapack.enchantments.CustomEnchantment;
import com.connexal.raveldatapack.enchantments.PoisonBladeEnchantment;
import com.connexal.raveldatapack.enchantments.TelekinesisEnchantment;
import com.connexal.raveldatapack.utils.EnchantmentLoreUtil;
import com.connexal.raveldatapack.utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnchantmentManager {
    private final Map<String, CustomEnchantment> enchantments = new HashMap<>();
    private int registeredCount = 0;

    public int init() {
        this.registerCustomEnchantment(new TelekinesisEnchantment());
        this.registerCustomEnchantment(new PoisonBladeEnchantment());
        this.registerCustomEnchantment(new BlazingArmorEnchantment());

        return registeredCount;
    }

    public boolean registerCustomEnchantment(CustomEnchantment enchantment) {
        if (RavelDatapack.getInstance().getConfig().contains("enchantments." + enchantment.getKey())) {
            if (!RavelDatapack.getInstance().getConfig().getBoolean("enchantments." + enchantment.getKey())) {
                return false;
            }
        } else {
            RavelDatapack.getInstance().getConfig().set("enchantments." + enchantment.getKey(), false);
            RavelDatapack.getInstance().saveConfig();
            return false;
        }

        enchantment.create();

        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);
        enchantments.put(enchantment.getNamespace(), enchantment);

        if (!registered) {
            return this.registerEnchantment(enchantment);
        } else {
            RavelDatapack.getLog().info("Enchantment already registered: \"" + enchantment.getKey() + "\"");
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
            registeredCount++;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            RavelDatapack.getLog().severe("Failed to register enchantment \"" + enchantment.getKey() + "\": Field value failure.");
            registered = false;
        } catch (IllegalStateException e) {
            RavelDatapack.getLog().severe("Failed to register enchantment \"" + enchantment.getKey() + "\": Not accepting new.");
            registered = false;
        }

        return registered;
    }

    public boolean enchantItemStack(ItemStack item, CustomEnchantment enchantment, int level, boolean force) {
        if (item == null) {
            return false;
        }
        if (!item.hasItemMeta()) {
            item.setItemMeta(RavelDatapack.getInstance().getServer().getItemFactory().getItemMeta(item.getType()));
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

            EnchantmentLoreUtil.addLore(item, enchantment.getKey().getKey(), ChatColor.GRAY + this.formatEnchantmentName(enchantment, level), 0);
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

    public String formatEnchantmentName(CustomEnchantment enchantment, int level) {
        if (enchantment == null) {
            return "";
        }

        if (enchantment.getMaxLevel() == 1) {
            return enchantment.getName();
        } else {
            return enchantment.getName() + " " + StringUtil.toRoman(level);
        }
    }

    public ItemStack getRandomCustomEnchantment() {
        Random rand = new Random();
        int random = rand.nextInt(RavelDatapack.getEnchantmentManager().getEnchantments().size());
        CustomEnchantment enchantment = RavelDatapack.getEnchantmentManager().getEnchantments().get(random);

        return enchantment.getBook(rand.nextInt(enchantment.getMaxLevel()) + 1);
    }

    public List<CustomEnchantment> getEnchantments() {
        return new ArrayList<>(enchantments.values());
    }

    public Enchantment getEnchantmentByKey(String key) {
        return enchantments.get(key);
    }
}
