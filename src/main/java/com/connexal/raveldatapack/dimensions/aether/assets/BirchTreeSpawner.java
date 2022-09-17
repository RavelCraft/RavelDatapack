package com.connexal.raveldatapack.dimensions.aether.assets;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class BirchTreeSpawner {
    public static void spawn(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        int height = random.nextInt(2) + 4;

        //Leaves
        int tmpX = x - 1;
        int tmpZ = z - 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                limitedRegion.setBlockData(tmpX + i, y + height + 1, tmpZ + j, AetherConstants.BIRCH_LEAVES_BLOCK_DATA);
            }
        }
        tmpX = x - 2;
        tmpZ = z - 2;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                limitedRegion.setBlockData(tmpX + i, y + height, tmpZ + j, AetherConstants.BIRCH_LEAVES_BLOCK_DATA);
            }
        }

        //Trunk
        for (int i = 0; i <= height; i++) {
            limitedRegion.setType(x, y + i, z, Material.BIRCH_LOG);
        }

        //Leaves part 2
        tmpX = x - 1;
        tmpZ = z - 3;
        for (int i = 0; i < 3; i++) {
            limitedRegion.setBlockData(tmpX + i, y + height, tmpZ, AetherConstants.BIRCH_LEAVES_BLOCK_DATA);
        }
        tmpX = x - 3;
        tmpZ = z - 1;
        for (int i = 0; i < 3; i++) {
            limitedRegion.setBlockData(tmpX, y + height, tmpZ + i, AetherConstants.BIRCH_LEAVES_BLOCK_DATA);
        }
        tmpX = x - 1;
        tmpZ = z + 3;
        for (int i = 0; i < 3; i++) {
            limitedRegion.setBlockData(tmpX + i, y + height, tmpZ, AetherConstants.BIRCH_LEAVES_BLOCK_DATA);
        }
        tmpX = x + 3;
        tmpZ = z - 1;
        for (int i = 0; i < 3; i++) {
            limitedRegion.setBlockData(tmpX, y + height, tmpZ + i, AetherConstants.BIRCH_LEAVES_BLOCK_DATA);
        }
    }
}
