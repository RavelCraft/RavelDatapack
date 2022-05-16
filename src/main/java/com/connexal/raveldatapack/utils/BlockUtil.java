package com.connexal.raveldatapack.utils;

import com.connexal.raveldatapack.RavelDatapack;
import com.google.common.collect.Sets;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class BlockUtil {
    public static final Set<Material> UNBREAKABLE_BLOCKS = Sets.newHashSet(Material.BEDROCK, Material.BARRIER, Material.NETHER_PORTAL, Material.END_PORTAL_FRAME, Material.END_PORTAL, Material.END_GATEWAY);

    public static void breakBlock(Player player, Block block, ItemStack item) {
        BlockBreakEvent event = new BlockBreakEvent(block, player);
        RavelDatapack.getInstance().getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            if (event.isDropItems() && player.getGameMode() != GameMode.CREATIVE) {
                block.breakNaturally(item);
            } else {
                block.setType(Material.AIR);
            }
        }
    }
}
