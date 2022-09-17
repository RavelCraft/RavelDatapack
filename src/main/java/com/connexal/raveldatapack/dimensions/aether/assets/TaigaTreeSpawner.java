package com.connexal.raveldatapack.dimensions.aether.assets;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

public class TaigaTreeSpawner {
    public static void spawn(int x, int y, int z, LimitedRegion limitedRegion) {
        for (int i = 0; i < 7; i++) {
            limitedRegion.setType(x, y + i, z, Material.SPRUCE_LOG);
        }

        spawnLeaves(x, y + 1, z, limitedRegion, 3);
        spawnLeaves(x, y + 2, z, limitedRegion, 4);
        spawnLeaves(x, y + 3, z, limitedRegion, 3);
        spawnLeaves(x, y + 4, z, limitedRegion, 2);
        spawnLeaves(x, y + 5, z, limitedRegion, 3);
        spawnLeaves(x, y + 6, z, limitedRegion, 2);
        limitedRegion.setBlockData(x, y + 7, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
    }

    private static void spawnLeaves(int x, int y, int z, LimitedRegion limitedRegion, int radius) {
        if (radius >= 2) {
            limitedRegion.setBlockData(x + 1, y, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 1, y, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x, y, z + 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x, y, z - 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
        }
        if (radius >= 3) {
            limitedRegion.setBlockData(x + 1, y, z + 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x + 1, y, z - 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 1, y, z + 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 1, y, z - 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x + 2, y, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 2, y, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x, y, z + 2, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x, y, z - 2, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
        }
        if (radius >= 4) {
            limitedRegion.setBlockData(x + 1, y, z + 2, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x + 1, y, z - 2, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 1, y, z + 2, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 1, y, z - 2, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x + 2, y, z + 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x + 2, y, z - 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 2, y, z + 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 2, y, z - 1, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x + 3, y, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x - 3, y, z, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x, y, z + 3, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
            limitedRegion.setBlockData(x, y, z - 3, AetherConstants.SPRUCE_LEAVES_BLOCK_DATA);
        }
    }
}
