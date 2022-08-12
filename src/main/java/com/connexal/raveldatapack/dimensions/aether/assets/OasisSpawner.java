package com.connexal.raveldatapack.dimensions.aether.assets;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class OasisSpawner {
    public static final int DIAMETER = 8;
    public static final int HEIGHT_DIFF_ACCEPTED = 4;
    public static final int DEPTH = 2;

    public static final Material MATERIAL = Material.SAND;

    public static void spawn(int x, int y, int z, LimitedRegion limitedRegion) {
        for (int tmpY = 0; tmpY <= DEPTH; tmpY++) {
            for (int i = 0; i < DIAMETER; i++) {
                for (int j = 0; j < DIAMETER; j++) {
                    boolean isBottom = tmpY == DEPTH;

                    if (!isBottom) {
                        if ((i == 0 && j == 0) || (i == DIAMETER - 1 && j == DIAMETER - 1) || (i == DIAMETER - 1 && j == 0) || (i == 0 && j == DIAMETER - 1)) {
                            continue;
                        }
                    }

                    boolean isSide = i <= tmpY || j <= tmpY || i >= DIAMETER - (1 + tmpY) || j >= DIAMETER - (1 + tmpY);
                    boolean isCorner = (i == tmpY + 1 && j == tmpY + 1) || (i == DIAMETER - (2 + tmpY) && j == tmpY + 1) || (i == DIAMETER - (2 + tmpY) && j == DIAMETER - (2 + tmpY)) || (i == tmpY + 1 && j == DIAMETER - (2 + tmpY));

                    if (isBottom || isSide || isCorner) {
                        limitedRegion.setType(x + i, y - tmpY, z + j, MATERIAL);
                    } else {
                        limitedRegion.setType(x + i, y - tmpY, z + j, Material.WATER);
                    }
                }
            }
        }
    }
}
