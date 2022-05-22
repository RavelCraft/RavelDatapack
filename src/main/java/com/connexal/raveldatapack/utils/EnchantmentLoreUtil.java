package com.connexal.raveldatapack.utils;

import com.connexal.raveldatapack.RavelDatapack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EnchantmentLoreUtil {
    public static final String LORE_FIX_PREFIX = "ravel_magic-";

    private static final String TAG_SPLITTER = "__x__";
    private static final Map<String, NamespacedKey> LORE_KEYS_CACHE = new HashMap<>();

    public static int addToLore(List<Component> lore, int position, String value) {
        if (position >= lore.size() || position < 0) {
            lore.add(Component.text(value));
        } else {
            lore.add(position, Component.text(value));
        }
        return position < 0 ? position : position + 1;
    }

    public static void addLore(ItemStack item, String id, String text, int position) {
        String[] lines = text.split(TAG_SPLITTER);
        addLore(item, id, Arrays.asList(lines), position);
    }

    public static void addLore(ItemStack item, String id, List<String> text, int position) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        List<Component> lore = meta.lore();
        if (lore == null) lore = new ArrayList<>();

        text = StringUtil.color(text);
        StringBuilder loreTag = new StringBuilder();

        delLore(item, id);
        for (String line : text) {
            position = addToLore(lore, position, line);

            if (loreTag.length() > 0)
                loreTag.append(TAG_SPLITTER);
            loreTag.append(line);
        }

        meta.lore(lore);
        item.setItemMeta(meta);

        addLoreTag(item, id, loreTag.toString());
    }

    public static void delLore(ItemStack item, String id) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        List<Component> lore = meta.lore();
        if (lore == null) {
            return;
        }

        int index = getLoreIndex(item, id, 0);
        if (index < 0) {
            return;
        }

        int lastIndex = getLoreIndex(item, id, 1);
        int diff = lastIndex - index;

        for (int i = 0; i < (diff + 1); i++) {
            lore.remove(index);
        }

        meta.lore(lore);
        item.setItemMeta(meta);

        delLoreTag(item, id);
    }

    public static int getLoreIndex(ItemStack item, String id) {
        return getLoreIndex(item, id, 0);
    }

    public static int getLoreIndex(ItemStack item, String id, int type) {
        String storedText = StringUtil.colorOff(PDCUtil.getStringData(item, getLoreKey(id)));
        if (storedText.isEmpty() || storedText.isBlank()) return -1;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return -1;

        List<String> lore = meta.getLore();
        if (lore == null) return -1;

        String[] lines = storedText.split(TAG_SPLITTER);
        String lastText = null;
        int count = 0;

        if (type == 0) {
            for (String line : lines) {
                lastText = StringUtil.colorOff(line);
                if (!lastText.isEmpty()) {
                    break;
                }
                count--;
            }
        } else {
            for (int i = lines.length; i > 0; i--) {
                lastText = StringUtil.colorOff(lines[i - 1]);
                if (!lastText.isEmpty()) {
                    break;
                }
                count++;
            }
        }

        if (lastText == null) {
            return -1;
        }

        int index = lore.indexOf(ChatColor.GRAY + lastText) + count;

        if (index < 0) {
            delLoreTag(item, id);
        }
        return index;
    }

    private static NamespacedKey getLoreKey(String id) {
       String lid = id.toLowerCase();
        return LORE_KEYS_CACHE.computeIfAbsent(lid, key -> new NamespacedKey(RavelDatapack.getInstance(), LORE_FIX_PREFIX + lid));
    }

    public static void addLoreTag(ItemStack item, String id, String text) {
        addLoreTag(item, getLoreKey(id), text);
    }

    public static void addLoreTag(ItemStack item, NamespacedKey key, String text) {
        PDCUtil.setData(item, key, text);
    }

    public static void delLoreTag(ItemStack item, String id) {
        delLoreTag(item, getLoreKey(id));
    }

    public static void delLoreTag(ItemStack item, NamespacedKey key) {
        PDCUtil.removeData(item, key);
    }
}
