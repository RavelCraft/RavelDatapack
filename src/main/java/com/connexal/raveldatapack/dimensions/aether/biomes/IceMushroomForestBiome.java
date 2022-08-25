package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.IceForestMushroomSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class IceMushroomForestBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.SNOWY_TAIGA;
    }

    @Override
    public String getName() {
        return "Ice Mushroom Forest";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                if (random.nextInt(10) < 6) {
                    chunkData.setBlock(x, y + 1, z, Material.SNOW);
                    chunkData.setBlock(x, y, z, Material.SNOW_BLOCK);
                } else {
                    chunkData.setBlock(x, y, z, Material.POWDER_SNOW);
                }
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.SNOW_BLOCK);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.SNOW;

        boolean groundOk = ground == Material.SNOW_BLOCK ||
                ground == Material.POWDER_SNOW;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            IceForestMushroomSpawner.spawn(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No plants
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
