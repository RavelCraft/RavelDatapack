package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class SantaHat extends CustomItem {
    public SantaHat(int customModelData) {
        super(customModelData, "santa_hat", true, true);
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Become Santa");
        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Santa Hat"));
        this.setItemMeta(meta);
    }
}
