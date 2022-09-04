package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class BurnedForestBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.FOREST;
    }

    @Override
    public String getName() {
        return "Burned Forest";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                int tmp = random.nextInt(6);
                if (tmp < 1) {
                    chunkData.setBlock(x, y, z, Material.MAGMA_BLOCK);
                } else if (tmp < 3) {
                    chunkData.setBlock(x, y, z, Material.BLACK_CONCRETE_POWDER);
                } else {
                    chunkData.setBlock(x, y, z, Material.COAL_BLOCK);
                }
            } else if (y > maxY - underCoverDepth) {
                int tmp = random.nextInt(6);
                if (tmp < 3) {
                    chunkData.setBlock(x, y, z, Material.BLACK_CONCRETE_POWDER);
                } else {
                    chunkData.setBlock(x, y, z, Material.COAL_BLOCK);
                }
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH;

        boolean groundOk = ground == Material.BLACK_CONCRETE_POWDER ||
                ground == Material.MAGMA_BLOCK ||
                ground == Material.COAL_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextBoolean()) {
            int height = random.nextInt(2) + 2;
            for (int i = 0; i < height; i++) {
                limitedRegion.setType(x, y + i, z, Material.DARK_OAK_LOG);
            }
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(8) == 0 && limitedRegion.getType(x, y - 1, z) != Material.MAGMA_BLOCK) {
            limitedRegion.setType(x, y, z, Material.DEAD_BUSH);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
