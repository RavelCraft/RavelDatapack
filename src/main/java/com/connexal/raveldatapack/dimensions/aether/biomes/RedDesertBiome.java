package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.CactusSpawner;
import com.github.imdabigboss.easydatapack.api.exceptions.SchematicException;
import com.github.imdabigboss.easydatapack.api.utils.schematics.Schematic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class RedDesertBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.SAVANNA_PLATEAU;
    }

    @Override
    public String getName() {
        return "Red Desert";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, random.nextBoolean() ? Material.RED_SAND : Material.SMOOTH_RED_SANDSTONE);
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.RED_SANDSTONE);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH;

        boolean groundOk = ground == Material.RED_SAND ||
                ground == Material.SMOOTH_RED_SANDSTONE;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            if (limitedRegion.getType(x, y - 1, z) != Material.RED_SAND) { //Cactus can only go on sand
                limitedRegion.setType(x, y - 1, z, Material.RED_SAND);
            }
            CactusSpawner.spawn(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            limitedRegion.setType(x, y, z, Material.DEAD_BUSH);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        if (random.nextInt(10) != 0) {
            return;
        }

        Schematic schematic;
        if (random.nextBoolean()) {
            schematic = this.getSchematicFromCache("redDesertHouse1");
        } else {
            schematic = this.getSchematicFromCache("redDesertHouse2");
        }

        Location location = this.getAcceptableStructureSpawn(worldInfo, limitedRegion, chunkX * 16, chunkZ * 16, schematic.getBaseWidth(), schematic.getBaseDepth(), 1);
        if (location == null) {
            return;
        }

        try {
            schematic.pasteSchematic(limitedRegion, location.getBlockX(), location.getBlockY(), location.getBlockZ());
        } catch (SchematicException e) {
            e.printStackTrace();
        }
    }
}
