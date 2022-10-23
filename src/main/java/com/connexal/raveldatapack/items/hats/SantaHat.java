package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.api.items.CustomHatItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class SantaHat extends CustomHatItem {
    public SantaHat(int customModelData) {
        super(customModelData, "santa_hat");
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
