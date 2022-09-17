package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.TaigaTreeSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class TaigaBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.TAIGA;
    }

    @Override
    public String getName() {
        return "Taiga";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, Material.GRASS_BLOCK);
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
                replaceable == Material.GRASS;

        boolean groundOk = ground == Material.DIRT ||
                ground == Material.COARSE_DIRT ||
                ground == Material.GRASS_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextBoolean()) {
            TaigaTreeSpawner.spawn(x, y, z, limitedRegion);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            limitedRegion.setType(x, y, z, Material.FERN);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        Integer y = this.getSurfaceLevel(worldInfo, limitedRegion, x, z);

        if (y == null) {
            return;
        }
        y--;

        limitedRegion.setType(x, y, z, Material.COARSE_DIRT);
        if (random.nextBoolean()) {
            limitedRegion.setType(x + 1, y, z, Material.COARSE_DIRT);
            if (random.nextBoolean()) {
                limitedRegion.setType(x + 1, y, z + 1, Material.COARSE_DIRT);
            }
        }
        if (random.nextBoolean()) {
            limitedRegion.setType(x - 1, y, z, Material.COARSE_DIRT);
            if (random.nextBoolean()) {
                limitedRegion.setType(x - 1, y, z + 1, Material.COARSE_DIRT);
            }
        }
        if (random.nextBoolean()) {
            limitedRegion.setType(x, y, z + 1, Material.COARSE_DIRT);
            if (random.nextBoolean()) {
                limitedRegion.setType(x + 1, y, z - 1, Material.COARSE_DIRT);
            }
        }
        if (random.nextBoolean()) {
            limitedRegion.setType(x, y, z - 1, Material.COARSE_DIRT);
            if (random.nextBoolean()) {
                limitedRegion.setType(x - 1, y, z - 1, Material.COARSE_DIRT);
            }
        }
    }
}
