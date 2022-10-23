package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.blocks.CustomBlock;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

import java.util.HashMap;
import java.util.Map;

public class BlockManager {
    private final Map<Integer, CustomBlock> blocks = new HashMap<>();

    private int registeredCount = 0;

    public int init() {
        this.registerCustomItem(new CustomBlock("Test Block", 68295, false, false, false, false, false, true, CustomBlock.Parent.MUSHROOM_STEM));

        return this.registeredCount;
    }

    public void registerCustomItem(CustomBlock block) {
        if (RavelDatapack.getInstance().getConfig().contains("block." + block.getNamespaceKey())) {
            if (!RavelDatapack.getInstance().getConfig().getBoolean("block." + block.getNamespaceKey())) {
                return;
            }
        } else {
            RavelDatapack.getInstance().getConfig().set("block." + block.getNamespaceKey(), false);
            RavelDatapack.getInstance().saveConfig();
            return;
        }

        this.blocks.put(block.getCustomModelData(), block);
        this.registeredCount++;
    }

    public Map<Integer, CustomBlock> getBlocks() {
        return this.blocks;
    }

    public CustomBlock findMatch(Block block) {
        MultipleFacing multipleFacing = (MultipleFacing) block.getBlockData();

        for (CustomBlock customBlock : this.blocks.values()) {
            if (customBlock.getMaterial() == block.getType() &&
                    customBlock.isUp() == multipleFacing.hasFace(BlockFace.UP) &&
                    customBlock.isDown() == multipleFacing.hasFace(BlockFace.DOWN) &&
                    customBlock.isNorth() == multipleFacing.hasFace(BlockFace.NORTH) &&
                    customBlock.isEast() == multipleFacing.hasFace(BlockFace.EAST) &&
                    customBlock.isSouth() == multipleFacing.hasFace(BlockFace.SOUTH) &&
                    customBlock.isWest() == multipleFacing.hasFace(BlockFace.WEST)) {
                return customBlock;
            }
        }

        return null;
    }
}
