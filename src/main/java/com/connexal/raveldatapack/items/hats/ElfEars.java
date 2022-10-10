package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class ElfEars extends CustomItem {
    public ElfEars(int customModelData) {
        super(customModelData, "elf_ears", true, true);
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
