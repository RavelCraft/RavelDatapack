package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.JibstonePeakSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class JibstoneBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.SNOWY_PLAINS;
    }

    @Override
    public String getName() {
        return "Jibstone";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                boolean isPowdered = false;

                int tmp = random.nextInt(10);
                if (tmp < 4) {
                    isPowdered = true;
                    chunkData.setBlock(x, y, z, Material.POWDER_SNOW);
                } else {
                    chunkData.setBlock(x, y, z, Material.SNOW_BLOCK);
                }

                if (!isPowdered) {
                    tmp = random.nextInt(50);
                    if (tmp < 1) {
                        chunkData.setBlock(x, y + 1, z, Material.SNOW_BLOCK);
                    } else if (tmp < 2) {
                        chunkData.setBlock(x, y + 1, z, Material.POWDER_SNOW);
                    } else {
                        chunkData.setBlock(x, y + 1, z, Material.SNOW);
                    }
                }
            } else if (y > maxY - underCoverDepth) {
                switch (random.nextInt(6)) {
                    case 0 -> chunkData.setBlock(x, y, z, Material.COBBLED_DEEPSLATE);
                    case 1 -> chunkData.setBlock(x, y, z, Material.POLISHED_DEEPSLATE);
                    case 2 -> chunkData.setBlock(x, y, z, Material.SMOOTH_BASALT);
                    case 3 -> chunkData.setBlock(x, y, z, Material.MUD);
                    case 4 -> chunkData.setBlock(x, y, z, Material.DEEPSLATE);
                    case 5 -> chunkData.setBlock(x, y, z, Material.TUFF);
                }
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.SNOW ||
                replaceable == Material.SNOW_BLOCK ||
                replaceable == Material.POWDER_SNOW;

        boolean groundOk = ground == Material.SNOW_BLOCK ||
                ground == Material.POWDER_SNOW;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //None
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //None
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        if (random.nextBoolean()) {
            int x = (chunkX * 16) + random.nextInt(16);
            int z = (chunkZ * 16) + random.nextInt(16);
            Integer y = this.getSurfaceLevel(worldInfo, limitedRegion, x, z);

            if (y != null) {
                JibstonePeakSpawner.spawn(x, y, z, limitedRegion, random);
            }
        }
    }
}
