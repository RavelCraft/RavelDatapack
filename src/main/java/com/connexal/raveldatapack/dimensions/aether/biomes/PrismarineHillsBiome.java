package com.connexal.raveldatapack.dimensions.aether.biomes;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class PrismarineHillsBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.TAIGA;
    }

    @Override
    public String getName() {
        return "Prismarine Hills";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                int tmp = random.nextInt(4);
                if (tmp < 1) {
                    chunkData.setBlock(x, y, z, Material.PRISMARINE_BRICKS);
                } else if (tmp < 2) {
                    chunkData.setBlock(x, y, z, Material.LIGHT_BLUE_STAINED_GLASS);
                } else {
                    chunkData.setBlock(x, y, z, Material.PRISMARINE);
                }
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.PRISMARINE);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR;

        boolean groundOk = ground == Material.PRISMARINE_BRICKS ||
                ground == Material.LIGHT_BLUE_STAINED_GLASS ||
                ground == Material.PRISMARINE;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No trees
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
