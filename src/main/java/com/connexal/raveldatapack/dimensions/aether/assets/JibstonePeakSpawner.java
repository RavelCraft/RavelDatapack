package com.connexal.raveldatapack.dimensions.aether.assets;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class JibstonePeakSpawner {
    public static void spawn(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        int height = switch (random.nextInt(5)) {
            case 0 -> 1;
            case 1, 2 -> 2;
            case 3, 4 -> 3;
            default -> throw new IllegalStateException("Unexpected value when generating peak height");
        };

        createMainStack(x, y, z, limitedRegion, random, height);
        
        createSideStack(x + 1, y, z, limitedRegion, random, height);
        createSideStack(x - 1, y, z, limitedRegion, random, height);
        createSideStack(x, y, z + 1, limitedRegion, random, height);
        createSideStack(x, y, z - 1, limitedRegion, random, height);
    }

    private static void createMainStack(int x, int y, int z, LimitedRegion limitedRegion, Random random, int height) {
        for (int i = 0; i < height; i++) {
            limitedRegion.setType(x, y + i, z, getRandomMaterial(random));
        }

        placeButton(x, y + height, z, limitedRegion, random);
    }

    private static void createSideStack(int x, int y, int z, LimitedRegion limitedRegion, Random random, int height) {
        int startY = 0;
        for (int i = -3; i < 0; i++) {
            Material type = limitedRegion.getType(x, y + i, z);
            if (type == Material.AIR || type == Material.SNOW) {
                startY--;
            }
        }

        if (startY <= -3) { //Don't make a stack with no ground beneath it
            return;
        }

        int doneHeight = 0;
        for (int i = startY; i < height; i++) {
            if (random.nextBoolean()) {
                limitedRegion.setType(x, y + i, z, getRandomMaterial(random));

                if (i >= 0) {
                    doneHeight++;
                }
            } else {
                break;
            }
        }

        placeButton(x, y + doneHeight, z, limitedRegion, random);
    }

    private static void placeButton(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        Material underType = limitedRegion.getType(x, y - 1, z);
        if (underType == Material.AIR || underType == Material.SNOW) {
            return;
        }

        if (random.nextInt(4) == 0) {
            limitedRegion.setBlockData(x, y, z, AetherConstants.BOTTOM_POLISHED_BLACKSTONE_BUTTON_DATA);
        }
    }

    private static Material getRandomMaterial(Random random) {
        return switch (random.nextInt(6)) {
            case 0 -> Material.COBBLED_DEEPSLATE;
            case 1 -> Material.POLISHED_DEEPSLATE;
            case 2 -> Material.SMOOTH_BASALT;
            case 3 -> Material.MUD;
            case 4 -> Material.DEEPSLATE;
            case 5 -> Material.TUFF;
            default -> throw new IllegalStateException("Unexpected value when generating random material");
        };
    }
}
