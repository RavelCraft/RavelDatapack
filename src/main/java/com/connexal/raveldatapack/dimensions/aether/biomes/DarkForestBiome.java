package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class DarkForestBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.DARK_FOREST;
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
        if (random.nextBoolean()) {
            Location location = new Location(null, x, y, z);
            limitedRegion.generateTree(location, random, TreeType.DARK_OAK);
        }
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        int randomPlant = random.nextInt(20);
        if (randomPlant == 0) {
            limitedRegion.setType(x, y, z, random.nextBoolean() ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM);
        } else {
            limitedRegion.setType(x, y, z, Material.GRASS);
        }
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        //No structures
    }
}
