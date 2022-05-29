package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class SnowyPlainsBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.SNOWY_PLAINS;
    }

    @Override
    public void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y + 1, z, Material.SNOW);
                chunkData.setBlock(x, y, z, AetherConstants.SNOWY_GRASS_BLOCK_DATA);
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
                replaceable == Material.SNOW ||
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
        if (random.nextBoolean()) {
            limitedRegion.setType(x, y, z, Material.GRASS);
            limitedRegion.setType(x, y - 1, z, Material.GRASS_BLOCK);
        }
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        //No structures
    }
}
