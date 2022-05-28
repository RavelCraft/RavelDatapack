package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.CactusSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class RedDesertBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.SAVANNA_PLATEAU;
    }

    @Override
    public void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, random.nextBoolean() ? Material.RED_SAND : Material.SMOOTH_RED_SANDSTONE);
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.RED_SANDSTONE);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomBaseMaterial(random));
            }
        }
    }

    @Override
    public boolean isSurfaceMaterialInternal(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH;

        boolean groundOk = ground == Material.RED_SAND ||
                ground == Material.SMOOTH_RED_SANDSTONE;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTreeInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            CactusSpawner.spawnCactus(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            limitedRegion.setType(x, y, z, Material.DEAD_BUSH);
        }
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        //No structures
    }
}
