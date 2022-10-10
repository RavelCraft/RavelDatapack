package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class PartyHat extends CustomItem {
    public PartyHat(int customModelData) {
        super(customModelData, "party_hat", true, true);
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Put this hat on to have a party!");
        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Party Hat"));
        this.setItemMeta(meta);
    }
}
