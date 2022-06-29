package com.connexal.raveldatapack.utils.schematics;

import org.bukkit.block.data.BlockData;

public class Schematic {
    private final BlockData[] blocks;
    private final int width;
    private final int height;
    private final int depth;

    public Schematic(BlockData[] blocks, int width, int height, int depth) {
        this.blocks = blocks;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public BlockData[] getBlocks() {
        return blocks;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }
}
