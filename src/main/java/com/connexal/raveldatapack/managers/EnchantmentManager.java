package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.enchantments.BlasingArmorEnchantment;
import com.connexal.raveldatapack.enchantments.CustomEnchantment;
import com.connexal.raveldatapack.enchantments.PoisonBladeEnchantment;
import com.connexal.raveldatapack.enchantments.TelekinesisEnchantment;
import com.connexal.raveldatapack.utils.EnchantmentLoreUtil;
import com.connexal.raveldatapack.utils.StringUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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
    private final HashMap<String, CustomEnchantment> enchantments = new HashMap<>();
    private int registeredCount = 0;

    /**
     * Initialise all the custom enchantments
     *
     * @return The number of custom enchantments initialised
     */
    public int init() {
        this.registerCustomEnchantment(new TelekinesisEnchantment());
        this.registerCustomEnchantment(new PoisonBladeEnchantment());
        this.registerCustomEnchantment(new BlasingArmorEnchantment());

        return registeredCount;
    }

    /**
     * Register a custom enchantment
     *
     * @param enchantment The {@link CustomEnchantment} to register
     * @return True if the enchantment was registered, false otherwise
     */
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

    /**
     * Register an enchantment
     *
     * @param enchantment The {@link Enchantment} to register
     * @return True if the enchantment was registered, false otherwise
     */
    public boolean registerEnchantment(Enchantment enchantment) {
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

    /**
     * Enchants an {@link ItemStack} with the specified {@link Enchantment}
     *
     * @param item        The {@link ItemStack} to enchant
     * @param enchantment The {@link Enchantment} to enchant the {@link ItemStack} with
     * @param level       The level of the enchantment
     * @param force       If true, the enchantment will be applied even if the {@link ItemStack} doesn't support it
     * @return The enchanted {@link ItemStack}
     */
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
        if (meta instanceof EnchantmentStorageMeta enchatMeta) {
            if (!enchatMeta.addStoredEnchant(enchantment, level, true)) {
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

    /**
     * Remove an {@link Enchantment} from an {@link ItemStack}
     *
     * @param item        The {@link ItemStack} to remove the {@link Enchantment} from
     * @param enchantment The {@link Enchantment} to remove
     * @return The {@link ItemStack} with the {@link Enchantment} removed
     */
    public boolean removeEnchantItemStack(ItemStack item, CustomEnchantment enchantment) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasEnchant(enchantment)) {
            return false;
        }

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.remove(enchantment.getKey());

        if (meta instanceof EnchantmentStorageMeta storageMeta) {
            storageMeta.removeStoredEnchant(enchantment);
        } else {
            meta.removeEnchant(enchantment);
        }

        item.setItemMeta(meta);
        return true;
    }

    /**
     * Remove all {@link Enchantment}s from an {@link ItemStack}
     *
     * @param item The {@link ItemStack} to remove all {@link Enchantment}s from
     * @return The {@link ItemStack} with all {@link Enchantment}s removed
     */
    public ItemStack removeEnchantItemStack(ItemStack item) {
        Set<Enchantment> enchantments = item.getEnchantments().keySet();
        for (Enchantment e : enchantments) {
            if (e instanceof CustomEnchantment customEnchantment) {
                this.removeEnchantItemStack(item, customEnchantment);
            } else {
                item.removeEnchantment(e);
            }
        }
        return item;
    }

    /**
     * Convert an {@link Enchantment} to a {@link CustomEnchantment}
     *
     * @param enchantment The {@link Enchantment} to convert
     * @return The {@link CustomEnchantment} or null if the {@link Enchantment} is not a {@link CustomEnchantment}
     */
    public CustomEnchantment toCustomEnchantment(Enchantment enchantment) {
        if (enchantment instanceof CustomEnchantment customEnchantment) {
            return customEnchantment;
        } else {
            return null;
        }
    }

    /**
     * Get if an {@link Enchantment} is a {@link CustomEnchantment}
     *
     * @param enchantment The {@link Enchantment} to check
     * @return True if the {@link Enchantment} is a {@link CustomEnchantment}, false otherwise
     */
    public boolean isEnchantCustom(Enchantment enchantment) {
        for (CustomEnchantment e : this.getEnchantments()) {
            if (enchantment.getKey().equals(e.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get if an {@link ItemStack} is enchantable
     *
     * @param item The {@link ItemStack} to check
     * @return True if it can be enchanted, false otherwise
     */
    public boolean isEnchantable(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return false;
        }

        return item.getType() == Material.ENCHANTED_BOOK || Stream.of(EnchantmentTarget.values()).anyMatch(target -> target.includes(item));
    }

    /**
     * Get the enchantments of an {@link ItemStack}
     *
     * @param item The {@link ItemStack} to get the enchantments of
     * @return A {@link Map<Enchantment, Integer>}
     */
    public Map<Enchantment, Integer> getItemEnchants(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return (meta instanceof EnchantmentStorageMeta meta2) ? meta2.getStoredEnchants() : meta.getEnchants();
    }

    /**
     * Gets the {@link CustomEnchantment}s of an {@link ItemStack}
     *
     * @param item The {@link ItemStack} to get the {@link CustomEnchantment}s of
     * @return A {@link Map<CustomEnchantment, Integer>}
     */
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

        LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().build();
        String originalName = serializer.serialize(originalMeta.displayName());

        String name;
        if (resultMeta.hasDisplayName()) {
            String tmpName = originalName;
            name = serializer.serialize(resultMeta.displayName());

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

        resultMeta.displayName(Component.text(name));
        result.setItemMeta(resultMeta);
    }

    /**
     * Format an {@link CustomEnchantment} to a {@link String}
     *
     * @param enchantment The {@link CustomEnchantment} to format
     * @param level       The level of the enchantment
     * @return The formatted {@link String}
     */
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

    /**
     * Get a random {@link CustomEnchantment}
     *
     * @return A random {@link CustomEnchantment}
     */
    public ItemStack getRandomCustomEnchantment() {
        Random rand = new Random();
        int random = rand.nextInt(RavelDatapack.getEnchantmentManager().getEnchantments().size());
        CustomEnchantment enchantment = RavelDatapack.getEnchantmentManager().getEnchantments().get(random);

        return enchantment.getBook(rand.nextInt(enchantment.getMaxLevel()) + 1);
    }

    /**
     * Get all the registered {@link CustomEnchantment}s
     *
     * @return A {@link List<CustomEnchantment>} of all the registered {@link CustomEnchantment}s
     */
    public List<CustomEnchantment> getEnchantments() {
        return new ArrayList<>(enchantments.values());
    }

    /**
     * Get a {@link CustomEnchantment} by its key
     *
     * @param key The key of the {@link CustomEnchantment}
     * @return The {@link CustomEnchantment} or null if it doesn't exist
     */
    public Enchantment getEnchantmentByKey(String key) {
        return enchantments.get(key);
    }
}
