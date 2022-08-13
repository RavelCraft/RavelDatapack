package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class JungleBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.JUNGLE;
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
                ground == Material.GRASS_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        Location location = new Location(null, x, y, z);
        int randomTree = random.nextInt(8);
        if (randomTree == 0) {
            limitedRegion.generateTree(location, random, TreeType.SMALL_JUNGLE);
        } else if (randomTree == 1) {
            limitedRegion.generateTree(location, random, TreeType.JUNGLE_BUSH);
        } else if (randomTree <= 4) {
            limitedRegion.generateTree(location, random, TreeType.COCOA_TREE);
        } else {
            limitedRegion.generateTree(location, random, TreeType.JUNGLE);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        int randomPlant = random.nextInt(6);
        if (randomPlant <= 2) {
            int baseY = y + random.nextInt(2) + 4;
            int topY = baseY + random.nextInt(2) + 1;

            for (int i = y; i < baseY; i++) {
                if (limitedRegion.getType(x, i, z) == Material.AIR || limitedRegion.getType(x, i, z) == Material.GRASS) {
                    limitedRegion.setType(x, i, z, Material.BAMBOO);
                } else {
                    break;
                }
            }
            for (int i = baseY; i < topY; i++) {
                if (limitedRegion.getType(x, i, z) == Material.AIR || limitedRegion.getType(x, i, z) == Material.GRASS) {
                    limitedRegion.setBlockData(x, i, z, AetherConstants.BAMBOO_LEAVES_BLOCK_DATA);
                } else {
                    break;
                }
            }
        } else if (randomPlant == 3) {
            limitedRegion.setType(x, y, z, Material.BAMBOO_SAPLING);
        } else {
            limitedRegion.setType(x, y, z, Material.GRASS);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
