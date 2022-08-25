package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;

import java.util.Random;

public class RosePlainsBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.MEADOW;
    }

    @Override
    public String getName() {
        return "Rose plains";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                if (random.nextInt(3) == 0) {
                    chunkData.setBlock(x, y, z, Material.MOSS_BLOCK);
                } else {
                    chunkData.setBlock(x, y, z, Material.GRASS_BLOCK);
                }

                if (random.nextInt(4) == 0) {
                    chunkData.setBlock(x, y + 1, z, Material.MOSS_CARPET);
                }
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.DIRT);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.GRASS ||
                replaceable == Material.MOSS_CARPET;

        boolean groundOk = ground == Material.MOSS_BLOCK ||
                ground == Material.GRASS_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No trees
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        int randomPlant = random.nextInt(100) + 1;
        if (randomPlant < 40) {
            limitedRegion.setType(x, y, z, Material.ROSE_BUSH);
            limitedRegion.setType(x, y + 1, z, Material.ROSE_BUSH);
        } else if (randomPlant < 60) {
            limitedRegion.setType(x, y, z, Material.MOSS_CARPET);
        } else if (randomPlant < 65) {
            limitedRegion.setBlockData(x, y, z, AetherConstants.AZALEA_LEAVES_BLOCK_DATA);
        } else if (randomPlant < 70) {
            limitedRegion.setBlockData(x, y, z, AetherConstants.FLOWERING_AZALEA_LEAVES_BLOCK_DATA);
        } else if (randomPlant < 85) {
            limitedRegion.setType(x, y, z, Material.AZALEA);
        } else {
            limitedRegion.setType(x, y, z, Material.FLOWERING_AZALEA);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
