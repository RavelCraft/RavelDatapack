package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.PalmTreeSpawner;
import com.connexal.raveldatapack.utils.schematics.Schematic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class OasisBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.BEACH;
    }

    @Override
    public String getName() {
        return "Oasis";
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
        boolean replaceableOk = replaceable == Material.AIR;

        boolean groundOk = ground == Material.SAND ||
                ground == Material.SMOOTH_SANDSTONE;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            PalmTreeSpawner.spawn(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No plants
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(4) == 0) {
            Schematic schematic = this.getSchematicFromCache("oasisPool");

            Location location = this.getAcceptableStructureSpawn(worldInfo, limitedRegion, chunkX * 16, chunkZ * 16, schematic.getBaseWidth(), schematic.getBaseDepth(), 1);
            if (location == null) {
                return;
            }

            schematic.pasteSchematic(limitedRegion, location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
    }
}
