package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.*;

public class DarkMesaBiome extends AetherBiome {
    private List<Material> layerList = new ArrayList<>();
    private Map<Integer, Material> layerMap = new HashMap<>();

    public DarkMesaBiome() {
        layerList.add(Material.BLACK_TERRACOTTA);
        layerList.add(Material.BLACK_TERRACOTTA);
        layerList.add(Material.YELLOW_TERRACOTTA);
        layerList.add(Material.YELLOW_TERRACOTTA);
        layerList.add(Material.YELLOW_TERRACOTTA);
        layerList.add(Material.BROWN_TERRACOTTA);
        layerList.add(Material.BROWN_TERRACOTTA);
        layerList.add(Material.WHITE_TERRACOTTA);
        layerList.add(Material.BLACK_TERRACOTTA);
        layerList.add(Material.WHITE_TERRACOTTA);
        layerList.add(Material.BLACK_TERRACOTTA);
        layerList.add(Material.BLACK_TERRACOTTA);
        layerList.add(Material.ORANGE_TERRACOTTA);

        for (int i = 0; i < layerList.size(); i++) {
            layerMap.put(AetherConstants.ISLAND_LEVEL + i, layerList.get(i));
        }
    }

    private Material getLayerMaterial(int y) {
        return layerMap.getOrDefault(y, Material.BLACK_TERRACOTTA);
    }
    @Override
    public Biome getBiome() {
        return Biome.ERODED_BADLANDS;
    }

    @Override
    public void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, this.getLayerMaterial(y));
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomBaseMaterial(random));
            }
        }
    }

    @Override
    public boolean isSurfaceMaterialInternal(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH;

        boolean groundOk = ground == Material.BLACK_TERRACOTTA ||
                ground == Material.WHITE_TERRACOTTA ||
                ground == Material.ORANGE_TERRACOTTA ||
                ground == Material.YELLOW_TERRACOTTA ||
                ground == Material.BROWN_TERRACOTTA;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTreeInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No trees
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            limitedRegion.setType(x, y, z, Material.DEAD_BUSH);
        }
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        //No structures
    }
}
