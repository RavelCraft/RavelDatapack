package com.connexal.raveldatapack.listeners;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.blocks.CustomBlock;
import com.connexal.raveldatapack.managers.BlockManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockListener implements Listener {
    private final BlockManager blockManager = RavelDatapack.getBlockManager();
    private final Map<Location, CustomBlock> customPlacedBlocks = new HashMap<>();
    private final Map<UUID, Long> lastPlaceEvent = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Long last = lastPlaceEvent.get(event.getPlayer().getUniqueId());
        long now = System.currentTimeMillis();
        if (last == null) {
            this.lastPlaceEvent.put(event.getPlayer().getUniqueId(), now);
        } else {
            if (now - last < 100) {
                return;
            }
            this.lastPlaceEvent.replace(event.getPlayer().getUniqueId(), now);
        }

        if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null || event.getHand() == null) {
            return;
        }
        if (event.getItem() == null || !event.getItem().hasItemMeta() || !event.getItem().getItemMeta().hasCustomModelData()) {
            return;
        }

        Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        if (block.getType() != Material.AIR) {
            return;
        }

        CustomBlock customBlock = this.blockManager.getBlocks().get(event.getItem().getItemMeta().getCustomModelData());
        if (customBlock == null) {
            return;
        }

        if (block.getWorld().getNearbyEntities(new BoundingBox(block.getX(), block.getY(), block.getZ(), block.getX() + 1, block.getY() + 1, block.getZ() + 1)).size() > 0) {
            return;
        }

        this.customPlacedBlocks.put(block.getLocation(), customBlock);

        event.setCancelled(true);
        BlockState oldState = block.getState();

        block.setType(customBlock.getMaterial());
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 1);

        BlockPlaceEvent placeEvent = new BlockPlaceEvent(block, oldState, event.getClickedBlock(), event.getItem(), event.getPlayer(), true, event.getHand());
        RavelDatapack.getInstance().getServer().getPluginManager().callEvent(placeEvent);
        if (placeEvent.isCancelled()) {
            block.setType(oldState.getType());
            block.setBlockData(oldState.getBlockData());
            return;
        }

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (this.customPlacedBlocks.containsKey(event.getBlock().getLocation())) {
            CustomBlock customBlock = this.customPlacedBlocks.remove(event.getBlock().getLocation());

            MultipleFacing multipleFacing = (MultipleFacing) block.getBlockData();
            multipleFacing.setFace(BlockFace.UP, customBlock.isUp());
            multipleFacing.setFace(BlockFace.DOWN, customBlock.isDown());
            multipleFacing.setFace(BlockFace.NORTH, customBlock.isNorth());
            multipleFacing.setFace(BlockFace.EAST, customBlock.isEast());
            multipleFacing.setFace(BlockFace.SOUTH, customBlock.isSouth());
            multipleFacing.setFace(BlockFace.WEST, customBlock.isWest());
            block.setBlockData(multipleFacing);
        } else {
            if (event.getBlock().getType() != Material.BROWN_MUSHROOM_BLOCK && event.getBlock().getType() != Material.RED_MUSHROOM_BLOCK && event.getBlock().getType() != Material.MUSHROOM_STEM) {
                return;
            }

            MultipleFacing multipleFacing = (MultipleFacing) block.getBlockData();
            multipleFacing.setFace(BlockFace.UP, true);
            multipleFacing.setFace(BlockFace.DOWN, true);
            multipleFacing.setFace(BlockFace.NORTH, true);
            multipleFacing.setFace(BlockFace.EAST, true);
            multipleFacing.setFace(BlockFace.SOUTH, true);
            multipleFacing.setFace(BlockFace.WEST, true);
            block.setBlockData(multipleFacing);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Block block = event.getBlock();
        if (block.getType() != Material.BROWN_MUSHROOM_BLOCK && block.getType() != Material.RED_MUSHROOM_BLOCK && block.getType() != Material.MUSHROOM_STEM) {
            return;
        }

        CustomBlock customBlock = this.blockManager.findMatch(block);
        if (customBlock != null) {
            event.setDropItems(false);
            event.setExpToDrop(customBlock.getDropExperience());
            block.getWorld().dropItemNaturally(block.getLocation(), customBlock.createDrop());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMushroomPhysics(BlockPhysicsEvent event) {
        if ((event.getChangedType() == Material.BROWN_MUSHROOM_BLOCK) || (event.getChangedType() == Material.RED_MUSHROOM_BLOCK) || (event.getChangedType() == Material.MUSHROOM_STEM)) {
            event.setCancelled(true);
            event.getBlock().getState().update(true, false);
        }
    }

    @EventHandler
    public void onPickBlockEvent(InventoryClickEvent event) {
        if (event.getClick() != ClickType.CREATIVE || event.getAction() != InventoryAction.PLACE_ALL || event.getSlotType() != InventoryType.SlotType.QUICKBAR || event.getClickedInventory().getType() != InventoryType.PLAYER || event.isRightClick() || !event.isLeftClick()) {
            return;
        }
        if (event.getCursor() == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Block block = player.getTargetBlock(5);
        if (block == null || event.getCursor().getType() != block.getType()) {
            return;
        }
        if (block.getType() != Material.BROWN_MUSHROOM_BLOCK && block.getType() != Material.RED_MUSHROOM_BLOCK && block.getType() != Material.MUSHROOM_STEM) {
            return;
        }

        CustomBlock customBlock = this.blockManager.findMatch(block);
        if (customBlock != null) {
            event.setCancelled(true);
            player.getInventory().setItem(event.getSlot(), customBlock.createDrop());
        }
    }
}
