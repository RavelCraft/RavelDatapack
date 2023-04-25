package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import com.github.imdabigboss.easydatapack.api.utils.BlockUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class SuperHammerItem {
    private static final List<Location> blocksToProcessLoc = new ArrayList<>();
    private static final Server server = RavelDatapack.getInstance().getServer();

    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.RED.toString() + ChatColor.BOLD + "Super Hammer", Material.NETHERITE_PICKAXE, TexturePath.item(namespaceKey), 5, 1)
                .playerBreakBlockEvent(SuperHammerItem::blockBreakEvent)
                .lore("Break a 3x3x3 area of blocks")
                .hideFlags(true)
                .unbreakable(true)
                .enchantment(Enchantment.KNOCKBACK, 2)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape(" NN", " BN", "B  ");
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        adder.register(item, recipe);
    }

    private static void blockBreakEvent(BlockBreakEvent event) {
        if (blocksToProcessLoc.contains(event.getBlock().getLocation())) {
            blocksToProcessLoc.remove(event.getBlock().getLocation());
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        Location location = event.getBlock().getLocation();
        for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    Location blockLocation = block.getLocation();
                    if (blockLocation.equals(location)) {
                        continue;
                    }

                    if (block.isLiquid() || BlockUtil.UNBREAKABLE_BLOCKS.contains(block.getType())) {
                        continue;
                    }

                    blocksToProcessLoc.add(blockLocation);
                    BlockUtil.breakBlock(server, player, block, item);
                }
            }
        }
    }
}
