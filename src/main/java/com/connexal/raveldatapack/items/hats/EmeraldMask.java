package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class EmeraldMask extends CustomItem {
    public EmeraldMask(int customModelData) {
        super(customModelData, "emerald_mask", true, true);
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "You got that drip");
        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Emerald Mask"));
        this.setItemMeta(meta);
    }
}
