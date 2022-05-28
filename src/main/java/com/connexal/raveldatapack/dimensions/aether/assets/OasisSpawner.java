package com.connexal.raveldatapack.dimensions.aether.assets;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class OasisSpawner {
    public static final int DIAMETER = 8;
    public static final int HEIGHT_DIFF_ACCEPTED = 4;
    public static final int DEAPTH = 2;

    public static final Material MATERIAL = Material.SAND;

    public static void spawnOasis(int x, int y, int z, ChunkGenerator.ChunkData chunkData, Random random) {
        for (int tmpY = 0; tmpY <= DEAPTH; tmpY++) {
            for (int i = 0; i < DIAMETER; i++) {
                for (int j = 0; j < DIAMETER; j++) {
                    boolean isBottom = tmpY == DEAPTH;

                    if (!isBottom) {
                        if ((i == 0 && j == 0) || (i == DIAMETER - 1 && j == DIAMETER - 1) || (i == DIAMETER - 1 && j == 0) || (i == 0 && j == DIAMETER - 1)) {
                            continue;
                        }
                    }

                    boolean isSide = i <= tmpY || j <= tmpY || i >= DIAMETER - (1 + tmpY) || j >= DIAMETER - (1 + tmpY);
                    boolean isCorner = (i == tmpY + 1 && j == tmpY + 1) || (i == DIAMETER - (2 + tmpY) && j == tmpY + 1) || (i == DIAMETER - (2 + tmpY) && j == DIAMETER - (2 + tmpY)) || (i == tmpY + 1 && j == DIAMETER - (2 + tmpY));

                    if (isBottom || isSide || isCorner) {
                        chunkData.setBlock(x + i, y - tmpY, z + j, MATERIAL);
                    } else {
                        chunkData.setBlock(x + i, y - tmpY, z + j, Material.WATER);
                    }
                }
            }
        }
    }
}
