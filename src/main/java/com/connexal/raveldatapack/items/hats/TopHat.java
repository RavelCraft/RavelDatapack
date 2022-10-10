package com.connexal.raveldatapack.items.hats;

import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class TopHat extends CustomItem {
    public TopHat(int customModelData) {
        super(customModelData, "top_hat", true, true);
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Put this on to look fancy");
        meta.displayName(Component.text(ChatColor.AQUA.toString() + ChatColor.BOLD + "Top Hat"));
        this.setItemMeta(meta);
    }
}
