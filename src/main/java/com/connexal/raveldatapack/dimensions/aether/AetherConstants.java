package com.connexal.raveldatapack.dimensions.aether;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Leaves;

public class AetherConstants {
    public static final double AMPLITUDE_1 = 0.5;
    public static final double FREQUENCY_1 = 0.9;
    public static final double SACALE_1 = 0.008D;

    public static final double AMPLITUDE_2 = 0.2;
    public static final double FREQUENCY_2 = 1.4;
    public static final double SACALE_2 = 0.01D;

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
    public static final BlockData AZALEA_LEAVES_BLOCK_DATA;
    public static final BlockData FLOWERING_AZALEA_LEAVES_BLOCK_DATA;
    public static final BlockData BOTTOM_POLISHED_BLACKSTONE_BUTTON_DATA;

    static {
        DEAD_FIRE_CORAL_BLOCK_DATA = Material.DEAD_FIRE_CORAL.createBlockData();
        ((Waterlogged) DEAD_FIRE_CORAL_BLOCK_DATA).setWaterlogged(false);

        SNOWY_GRASS_BLOCK_DATA = Material.GRASS_BLOCK.createBlockData();
        ((Snowable) SNOWY_GRASS_BLOCK_DATA).setSnowy(true);

        JUNGLE_LEAVES_BLOCK_DATA = Material.JUNGLE_LEAVES.createBlockData();
        ((Leaves) JUNGLE_LEAVES_BLOCK_DATA).setPersistent(true);

        BAMBOO_LEAVES_BLOCK_DATA = Material.BAMBOO.createBlockData();
        ((Bamboo) BAMBOO_LEAVES_BLOCK_DATA).setLeaves(Bamboo.Leaves.LARGE);

        BOTTOM_POLISHED_BLACKSTONE_BUTTON_DATA = Material.POLISHED_BLACKSTONE_BUTTON.createBlockData();
        ((FaceAttachable) BOTTOM_POLISHED_BLACKSTONE_BUTTON_DATA).setAttachedFace(AttachedFace.FLOOR);

        AZALEA_LEAVES_BLOCK_DATA = Material.AZALEA_LEAVES.createBlockData();
        ((Leaves) AZALEA_LEAVES_BLOCK_DATA).setPersistent(true);

        FLOWERING_AZALEA_LEAVES_BLOCK_DATA = Material.FLOWERING_AZALEA_LEAVES.createBlockData();
        ((Leaves) FLOWERING_AZALEA_LEAVES_BLOCK_DATA).setPersistent(true);
    }
}
