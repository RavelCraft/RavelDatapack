package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class PlainsBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.PLAINS;
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
        //No trees
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        int randomPlant = random.nextInt(101);
        if (randomPlant > 90) {
            limitedRegion.setType(x, y, z, Material.CORNFLOWER);
        } else if (randomPlant > 80) {
            limitedRegion.setType(x, y, z, Material.LILY_OF_THE_VALLEY);
        } else if (randomPlant > 70) {
            limitedRegion.setType(x, y, z, Material.ALLIUM);
        } else {
            limitedRegion.setType(x, y, z, Material.GRASS);
        }
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        //No structures
    }
}
