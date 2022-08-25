package com.connexal.raveldatapack.dimensions.aether.assets;

import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class CrimsonMushroomSpawner {
    public static void spawn(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        switch (random.nextInt(2)) {
            case 0 -> type1(x, y, z, limitedRegion, random);
            case 1 -> type2(x, y, z, limitedRegion, random);
        }
    }

    private static void type1(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        int trunkHeight = random.nextInt(2) + 3;
        for (int i = 0; i < trunkHeight; i++) {
            limitedRegion.setType(x, y + i, z, Material.LIGHT_GRAY_CONCRETE);
        }

        int tmpY = y + trunkHeight;
        limitedRegion.setType(x, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 1, tmpY, z, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x, tmpY, z, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x, tmpY, z + 1, Material.NETHER_WART_BLOCK);

        tmpY--;
        limitedRegion.setType(x + 1, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 2, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 1, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 2, tmpY, z, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 2, tmpY, z + 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 1, tmpY, z + 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z + 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z + 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 1, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z + 2, Material.NETHER_WART_BLOCK);
    }

    private static void type2(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        int firstSegHeight = random.nextInt(1) + 3;
        for (int i = 0; i < firstSegHeight; i++) {
            limitedRegion.setType(x, y + i, z, Material.LIGHT_GRAY_CONCRETE);
        }

        switch (random.nextInt(4)) {
            case 0 -> x = x + 1;
            case 1 -> x = x - 1;
            case 2 -> z = z + 1;
            case 3 -> z = z - 1;
        }

        int secondSegHeight = random.nextInt(1) + 2;
        for (int i = -1; i < secondSegHeight; i++) {
            limitedRegion.setType(x, firstSegHeight + y + i, z, Material.LIGHT_GRAY_CONCRETE);
        }

        int trunkHeight = firstSegHeight + secondSegHeight;

        int tmpY = y + trunkHeight;
        for (int tmpX = -1; tmpX <= 1; tmpX++) {
            for (int tmpZ = -1; tmpZ <= 1; tmpZ++) {
                limitedRegion.setType(x + tmpX, tmpY, z + tmpZ, Material.NETHER_WART_BLOCK);
            }
        }

        tmpY--;
        for (int tmpZ = -1; tmpZ <= 1; tmpZ++) {
            limitedRegion.setType(x + 2, tmpY, z + tmpZ, Material.NETHER_WART_BLOCK);
            limitedRegion.setType(x - 2, tmpY, z + tmpZ, Material.NETHER_WART_BLOCK);
        }
        for (int tmpX = -1; tmpX <= 1; tmpX++) {
            limitedRegion.setType(x + tmpX, tmpY, z + 2, Material.NETHER_WART_BLOCK);
            limitedRegion.setType(x + tmpX, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        }

        tmpY--;
        limitedRegion.setType(x + 2, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 2, tmpY, z + 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 2, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z - 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z + 1, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 1, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 2, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 1, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x + 1, tmpY, z - 2, Material.NETHER_WART_BLOCK);
        limitedRegion.setType(x - 2, tmpY, z - 2, Material.NETHER_WART_BLOCK);

        tmpY--;
        if (random.nextBoolean()) {
            limitedRegion.setType(x - 2, tmpY, z - 2, Material.NETHER_WART_BLOCK);
            limitedRegion.setType(x + 2, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        } else {
            limitedRegion.setType(x + 2, tmpY, z - 2, Material.NETHER_WART_BLOCK);
            limitedRegion.setType(x - 2, tmpY, z + 2, Material.NETHER_WART_BLOCK);
        }
    }
}
