package com.connexal.raveldatapack.dimensions.aether;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Leaves;

public class AetherConstants {
    public static final double AMPLITUDE = 0.5;
    public static final double FREQUENCY = 0.9;
    public static final double SACALE = 0.010D;
    public static final int START_DRAWING_HEIGHT = 5;

    public static final int ISLAND_HEIGHT = 15;
    public static final int ISLAND_LEVEL = 62;
    public static final int ISLAND_BOTTOM_MULTIPLIER = 4;

    public static final int TREE_DENSITY = 12;
    public static final int PLANT_DENSITY = 50;

    public static final BlockData DEAD_FIRE_CORAL_BLOCK_DATA;
    public static final BlockData SNOWY_GRASS_BLOCK_DATA;
    public static final BlockData JUNGLE_LEAVES_BLOCK_DATA;
    public static final BlockData BAMBOO_LEAVES_BLOCK_DATA;

    static {
        DEAD_FIRE_CORAL_BLOCK_DATA = Material.DEAD_FIRE_CORAL.createBlockData();
        ((Waterlogged) DEAD_FIRE_CORAL_BLOCK_DATA).setWaterlogged(false);

        SNOWY_GRASS_BLOCK_DATA = Material.GRASS_BLOCK.createBlockData();
        ((Snowable) SNOWY_GRASS_BLOCK_DATA).setSnowy(true);

        JUNGLE_LEAVES_BLOCK_DATA = Material.JUNGLE_LEAVES.createBlockData();
        ((Leaves) JUNGLE_LEAVES_BLOCK_DATA).setPersistent(true);

        BAMBOO_LEAVES_BLOCK_DATA = Material.BAMBOO.createBlockData();
        ((Bamboo) BAMBOO_LEAVES_BLOCK_DATA).setLeaves(Bamboo.Leaves.LARGE);
    }
}
