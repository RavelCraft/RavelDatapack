package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class MangroveForestBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.JUNGLE;
    }

    @Override
    public String getName() {
        return "Mangrove Forest";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, Material.MOSS_BLOCK);
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
                replaceable == Material.GRASS ||
                replaceable == Material.SMALL_DRIPLEAF ||
                replaceable == Material.MOSS_CARPET;

        boolean groundOk = ground == Material.DIRT ||
                ground == Material.MOSS_BLOCK;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        limitedRegion.generateTree(new Location(limitedRegion.getWorld(), x, y, z), random, TreeType.MANGROVE);
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        int randomPlant = random.nextInt(10);
        if (randomPlant < 2) {
            limitedRegion.setType(x, y, z, Material.SMALL_DRIPLEAF);
            limitedRegion.setBlockData(x, y + 1, z, AetherConstants.UPPER_SMALL_DRIPLEAF_BLOCK_DATA);
        } else if (randomPlant < 4) {
            limitedRegion.setType(x, y, z, Material.MOSS_CARPET);
        } else if (randomPlant < 5) {
            limitedRegion.setType(x, y, z, Material.FERN);
        } else if (randomPlant < 6) {
            limitedRegion.setType(x, y, z, Material.AZALEA);
        } else {
            limitedRegion.setType(x, y, z, Material.GRASS);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        // No structures
    }

    @Override
    public boolean spawnEntity(Location location, EntityType original, Entity spawned) {
        if (spawned instanceof Monster) {
            return false;
        }

        location.getWorld().spawnEntity(location, EntityType.FROG);
        return true;
    }
}
