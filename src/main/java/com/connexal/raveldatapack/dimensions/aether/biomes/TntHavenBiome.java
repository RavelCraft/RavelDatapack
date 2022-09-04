package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class TntHavenBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.BIRCH_FOREST;
    }

    @Override
    public String getName() {
        return "Tnt Haven";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y > maxY - underCoverDepth) {
                int tmp = random.nextInt(8);
                if (tmp < 1) {
                    chunkData.setBlock(x, y, z, Material.TNT);
                } else if (tmp < 4) {
                    chunkData.setBlock(x, y, z, Material.RED_CONCRETE);
                } else if (tmp < 6) {
                    chunkData.setBlock(x, y, z, Material.RED_CONCRETE_POWDER);
                } else {
                    chunkData.setBlock(x, y, z, Material.RED_TERRACOTTA);
                }
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR;

        boolean groundOk = ground == Material.RED_TERRACOTTA ||
                ground == Material.RED_CONCRETE ||
                ground == Material.RED_CONCRETE_POWDER ||
                ground == Material.TNT;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextBoolean()) {
            int height = random.nextInt(4) + 1;
            for (int i = 0; i < height; i++) {
                limitedRegion.setType(x, y + i, z, Material.TNT);
            }
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No plants
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        // No structures
    }
}
