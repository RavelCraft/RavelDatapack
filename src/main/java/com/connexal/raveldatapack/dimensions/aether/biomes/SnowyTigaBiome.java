package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class SnowyTigaBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.SNOWY_TAIGA;
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y + 1, z, Material.SNOW);
                chunkData.setBlock(x, y, z, AetherConstants.SNOWY_GRASS_BLOCK_DATA);
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
                replaceable == Material.SNOW ||
                replaceable == Material.GRASS;

        boolean groundOk = ground == Material.DIRT ||
                ground == Material.GRASS_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        Location location = new Location(null, x, y, z);
        limitedRegion.setType(x, y - 1, z, Material.DIRT);
        limitedRegion.setType(x, y, z, Material.AIR);
        limitedRegion.generateTree(location, random, random.nextInt(6) == 0 ? TreeType.TALL_REDWOOD : TreeType.REDWOOD);
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        limitedRegion.setType(x, y - 1, z, Material.GRASS_BLOCK);
        limitedRegion.setType(x, y, z, Material.GRASS);
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
