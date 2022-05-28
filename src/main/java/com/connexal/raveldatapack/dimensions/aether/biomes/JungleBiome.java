package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class JungleBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.JUNGLE;
    }

    @Override
    public void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, Material.GRASS_BLOCK);
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.DIRT);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomBaseMaterial(random));
            }
        }
    }

    @Override
    public boolean isSurfaceMaterialInternal(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.GRASS;

        boolean groundOk = ground == Material.DIRT ||
                ground == Material.GRASS_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTreeInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        Location location = new Location(null, x, y, z);
        int randomTree = random.nextInt(8);
        if (randomTree == 0) {
            limitedRegion.generateTree(location, random, TreeType.SMALL_JUNGLE);
        } else if (randomTree == 1) {
            limitedRegion.generateTree(location, random, TreeType.JUNGLE_BUSH);
        } else if (randomTree <= 4) {
            limitedRegion.generateTree(location, random, TreeType.COCOA_TREE);
        } else {
            limitedRegion.generateTree(location, random, TreeType.JUNGLE);
        }
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        int randomPlant = random.nextInt(6);
        if (randomPlant <= 2) {
            int tmpY = y + random.nextInt(5) + 4;
            for (int i = y; i < tmpY; i++) {
                if (limitedRegion.getType(x, i, z) == Material.AIR || limitedRegion.getType(x, i, z) == Material.GRASS) {
                    limitedRegion.setType(x, i, z, Material.BAMBOO);
                } else {
                    break;
                }
            }
        } else if (randomPlant == 3) {
            limitedRegion.setType(x, y, z, Material.BAMBOO_SAPLING);
        } else {
            limitedRegion.setType(x, y, z, Material.GRASS);
        }
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        //No structures
    }
}