package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.RavelDatapack;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class AetherBiome {
    private static Map<Biome, AetherBiome> biomeMap = new HashMap<>();

    public static void registerBiome(AetherBiome biome) {
        if (biomeMap.containsKey(biome.getBiome())) {
            throw new IllegalArgumentException("Biome already registered " + biome.getBiome().toString());
        }
        biomeMap.put(biome.getBiome(), biome);
    }

    public static void drawStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).drawStackInternal(chunkData, x, z, minY, maxY, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
        for (int y = minY; y < maxY; y++) {
            chunkData.setBlock(x, y, z, getRandomBaseMaterial(random));
        }
    }

    public static boolean isSurfaceMaterial(Material replaceable, Material ground, Biome biome) {
        if (biomeMap.containsKey(biome)) {
            return biomeMap.get(biome).isSurfaceMaterialInternal(replaceable, ground);
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
        return false;
    }

    public static void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).spawnTreeInternal(limitedRegion, x, y, z, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
    }

    public static void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).spawnPlantInternal(limitedRegion, x, y, z, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
    }

    public static void spawnStructure(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).spawnStructureInternal(chunkData, chunkX, chunkZ, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
    }

    public abstract Biome getBiome();

    public abstract void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random);

    public abstract boolean isSurfaceMaterialInternal(Material replaceable, Material ground);

    public abstract void spawnTreeInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random);

    public abstract void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random);

    public abstract void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random);

    public static Material getRandomBaseMaterial(Random random) {
        int randomMaterial = random.nextInt(101);
        if (randomMaterial < 60) {
            return Material.STONE;
        } else if (randomMaterial < 90) {
            return Material.COBBLESTONE;
        } else if (randomMaterial < 95) {
            return Material.ANDESITE;
        } else {
            return Material.TUFF;
        }
    }

    public Integer getSurfaceLevel(ChunkGenerator.ChunkData chunkData, int x, int z) {
        int y = chunkData.getMaxHeight();

        while (y > chunkData.getMinHeight() + 1) {
            y -= 1;
            if (AetherBiome.isSurfaceMaterial(chunkData.getType(x, y, z), chunkData.getType(x, y - 1, z), this.getBiome())) {
                return y;
            }
        }

        return null;
    }
}
