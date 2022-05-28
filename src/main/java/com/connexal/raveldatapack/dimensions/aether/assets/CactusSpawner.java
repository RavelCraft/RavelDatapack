package com.connexal.raveldatapack.dimensions.aether.assets;

import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class CactusSpawner {
    public static void spawnCactus(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        int height;
        for (height = 0; height < random.nextInt(4) + 2; height++) {
            limitedRegion.setType(x, y + height, z, Material.CACTUS);
        }

        if (random.nextInt(2) == 0) {
            int tmpY = y + (height - 1);

            if (random.nextBoolean()) { //X or Z axis
                if (random.nextBoolean()) { //X +
                    spawnCactusInternal(x + 1, tmpY, z, limitedRegion, random);
                }
                if (random.nextBoolean()) { //X -
                    spawnCactusInternal(x - 1, tmpY, z, limitedRegion, random);
                }
            } else {
                if (random.nextBoolean()) { //Z +
                    spawnCactusInternal(x, tmpY, z + 1, limitedRegion, random);
                }
                if (random.nextBoolean()) { //Z -
                    spawnCactusInternal(x, tmpY, z - 1, limitedRegion, random);
                }
            }
        }
    }

    private static void spawnCactusInternal(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        for (int i = 0; i < random.nextInt(2) + 3; i++) {
            limitedRegion.setType(x, y + i, z, Material.CACTUS);
        }
    }
}
