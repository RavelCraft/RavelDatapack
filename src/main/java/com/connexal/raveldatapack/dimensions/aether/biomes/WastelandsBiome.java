package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.utils.schematics.Schematics;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class WastelandsBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.WOODED_BADLANDS;
    }

    @Override
    public void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, random.nextBoolean() ? Material.GRAVEL : Material.COARSE_DIRT);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomBaseMaterial(random));
            }
        }
    }

    @Override
    public boolean isSurfaceMaterialInternal(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH ||
                replaceable == Material.DEAD_FIRE_CORAL;

        boolean groundOk = ground == Material.GRAVEL ||
                ground == Material.COARSE_DIRT;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTreeInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No trees
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No plants
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        if (random.nextBoolean()) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            Integer y = this.getSurfaceLevel(chunkData, x, z);

            if (y != null) {
                for (int i = 0; i < random.nextInt(3) + 2; i++) {
                    chunkData.setBlock(x, y + i, z, Material.BONE_BLOCK);
                }
            }

            int diffX = 0;
            int diffZ = 0;
            if (random.nextBoolean()) {
                diffX = random.nextInt(3) + 2;
            }
            if (random.nextBoolean()) {
                diffZ = random.nextInt(3) + 2;
            }
            if (diffX != 0 && diffZ != 0) {
                Integer y2 = this.getSurfaceLevel(chunkData, x + diffX, z + diffZ);
                if (y2 != null) {
                    for (int i = 0; i < random.nextInt(3) + 2; i++) {
                        chunkData.setBlock(x + diffX, y2 + i, z + diffZ, Material.BONE_BLOCK);
                    }
                }
            }
        }
    }
}
