package com.connexal.raveldatapack.custom.items;

import com.connexal.raveldatapack.utils.BlockUtil;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SuperHammerItem extends CustomItem implements Listener {
    private List<Location> blocksToProcessLoc = new ArrayList<>();

    public SuperHammerItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "super_hammer";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.NETHERITE_PICKAXE, 1);

        ItemMeta meta = this.createItemMeta(true, false);

        this.setItemLore(meta, "Break a 3x3x3 area of blocks");

        meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Super Hammer");
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        this.setAttackDamage(meta, 5, EquipmentSlot.HAND);
        this.setAttackSpeed(meta, 1, EquipmentSlot.HAND);
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape(" NN"," BN", "B  ");
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('B', Material.BLAZE_ROD);
        this.instance.getServer().addRecipe(recipe);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.blocksToProcessLoc.contains(event.getBlock().getLocation())) {
            this.blocksToProcessLoc.remove(event.getBlock().getLocation());
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getItemMeta() == null) {
            return;
        }
        if (!item.getItemMeta().hasCustomModelData()) {
            return;
        }

        if (item.getItemMeta().getCustomModelData() == this.getCustomModelData()) {
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

                        this.blocksToProcessLoc.add(blockLocation);
                        BlockUtil.breakBlock(player, block, item);
                    }
                }
            }
        }
    }
}
