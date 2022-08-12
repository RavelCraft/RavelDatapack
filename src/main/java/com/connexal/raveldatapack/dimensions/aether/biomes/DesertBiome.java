package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import com.connexal.raveldatapack.dimensions.aether.assets.CactusSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class DesertBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.DESERT;
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, random.nextBoolean() ? Material.SAND : Material.SMOOTH_SANDSTONE);
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.SANDSTONE);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH ||
                replaceable == Material.DEAD_FIRE_CORAL;

        boolean groundOk = ground == Material.SAND ||
                ground == Material.SMOOTH_SANDSTONE;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            CactusSpawner.spawn(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            if (random.nextBoolean()) {
                limitedRegion.setType(x, y, z, Material.DEAD_BUSH);
            } else {
                limitedRegion.setBlockData(x, y, z, AetherConstants.DEAD_FIRE_CORAL_BLOCK_DATA);
            }
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
