package com.connexal.raveldatapack.blocks;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.blocks.CustomBlock;
import com.github.imdabigboss.easydatapack.api.types.items.CustomBlockPlacerItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TestBlock {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        String name = ChatColor.WHITE + "Test Block";

        CustomBlockPlacerItem item = (CustomBlockPlacerItem) CustomBlockPlacerItem.builder(namespaceKey, name, Material.CLOCK, TexturePath.blockItem(namespaceKey))
                .build();
        CustomBlock block = CustomBlock.builder(name, namespaceKey, item, TexturePath.block(namespaceKey))
                .build();

        adder.register(item, block);
    }
}
