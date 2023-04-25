package com.connexal.raveldatapack.dimensions.aether.populators;

import com.connexal.raveldatapack.dimensions.aether.biomes.AetherBiome;
import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomChunkPopulator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AetherStructurePopulator extends CustomChunkPopulator {
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;

        Map<Biome, Integer> biomeAmount = new HashMap<>();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome biome = this.getBiomeProvider().getBiome(worldInfo, worldX + x, 0, worldZ + z);

                if (biomeAmount.containsKey(biome)) {
                    biomeAmount.replace(biome, biomeAmount.get(biome) + 1);
                } else {
                    biomeAmount.put(biome, 1);
                }
            }
        }

        if (biomeAmount.size() == 1) {
            Biome biome = biomeAmount.keySet().iterator().next();
            AetherBiome.spawnStructure(worldInfo, limitedRegion, random, chunkX, chunkZ, biome);
        } else {
            for (Map.Entry<Biome, Integer> entry : biomeAmount.entrySet()) {
                if (entry.getValue() > 128) {
                    AetherBiome.spawnStructure(worldInfo, limitedRegion, random, chunkX, chunkZ, entry.getKey());
                }
            }
        }
    }

    @Override
    public boolean isSurface(Material cover, Material ground, Biome biome) {
        return AetherBiome.canReplaceMaterial(cover, ground, biome);
    }
}
