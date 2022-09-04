package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.CrystalTreeSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class CrystalFieldsBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.JUNGLE;
    }

    @Override
    public String getName() {
        return "Crystal Fields";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                if (random.nextInt(4) == 0) {
                    chunkData.setBlock(x, y, z, Material.BUDDING_AMETHYST);

                    switch (random.nextInt(5)) {
                        case 0 -> chunkData.setBlock(x, y + 1, z, Material.SMALL_AMETHYST_BUD);
                        case 1 -> chunkData.setBlock(x, y + 1, z, Material.MEDIUM_AMETHYST_BUD);
                        case 2 -> chunkData.setBlock(x, y + 1, z, Material.LARGE_AMETHYST_BUD);
                    }
                } else {
                    chunkData.setBlock(x, y, z, Material.AMETHYST_BLOCK);
                }
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.AMETHYST_BLOCK);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.SMALL_AMETHYST_BUD ||
                replaceable == Material.MEDIUM_AMETHYST_BUD ||
                replaceable == Material.LARGE_AMETHYST_BUD;

        boolean groundOk = ground == Material.AMETHYST_BLOCK ||
                ground == Material.BUDDING_AMETHYST;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            CrystalTreeSpawner.spawn(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //None
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
