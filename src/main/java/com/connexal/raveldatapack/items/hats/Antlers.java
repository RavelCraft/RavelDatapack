package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class Antlers extends CustomItem {
    public Antlers(int customModelData) {
        super(customModelData, "antlers", true, true);
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Antlers go brrr");
        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Antlers"));
        this.setItemMeta(meta);
    }
}
