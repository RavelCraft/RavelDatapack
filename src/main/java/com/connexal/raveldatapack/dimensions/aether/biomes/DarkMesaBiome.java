package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.*;

public class DarkMesaBiome extends AetherBiome {
    private final Map<Integer, Material> layerMap = new HashMap<>();

    public DarkMesaBiome() {
        List<Material> layerList = new ArrayList<>();
        layerList.add(Material.PACKED_MUD);
        layerList.add(Material.PACKED_MUD);
        layerList.add(Material.SOUL_SOIL);
        layerList.add(Material.SOUL_SOIL);
        layerList.add(Material.SOUL_SOIL);
        layerList.add(Material.COARSE_DIRT);
        layerList.add(Material.COARSE_DIRT);
        layerList.add(Material.SOUL_SAND);
        layerList.add(Material.PACKED_MUD);
        layerList.add(Material.SOUL_SAND);
        layerList.add(Material.PACKED_MUD);
        layerList.add(Material.PACKED_MUD);
        layerList.add(Material.DIRT);

        for (int i = 0; i < layerList.size(); i++) {
            layerMap.put(AetherConstants.ISLAND_LEVEL + i, layerList.get(i));
        }
    }

    @Override
    public Biome getVanillaBiome() {
        return Biome.ERODED_BADLANDS;
    }

    @Override
    public String getName() {
        return "Dark Mesa";
    }

    private Material getLayerMaterial(int y) {
        return layerMap.getOrDefault(y, Material.PACKED_MUD);
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, this.getLayerMaterial(y));
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR ||
                replaceable == Material.DEAD_BUSH;

        boolean groundOk = ground == Material.PACKED_MUD ||
                ground == Material.SOUL_SOIL ||
                ground == Material.COARSE_DIRT ||
                ground == Material.SOUL_SAND ||
                ground == Material.DIRT;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No trees
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            limitedRegion.setType(x, y, z, Material.DEAD_BUSH);
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
