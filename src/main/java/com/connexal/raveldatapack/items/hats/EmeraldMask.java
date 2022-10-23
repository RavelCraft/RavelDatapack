package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.api.items.CustomHatItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class EmeraldMask extends CustomHatItem {
    public EmeraldMask(int customModelData) {
        super(customModelData, "emerald_mask");
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
