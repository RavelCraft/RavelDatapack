package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.api.items.CustomHatItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class Antlers extends CustomHatItem {
    public Antlers(int customModelData) {
        super(customModelData, "antlers");
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
