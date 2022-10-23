package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.api.items.CustomHatItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class ElfEars extends CustomHatItem {
    public ElfEars(int customModelData) {
        super(customModelData, "elf_ears");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Become an elf");
        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Elf Ears"));
        this.setItemMeta(meta);
    }
}
