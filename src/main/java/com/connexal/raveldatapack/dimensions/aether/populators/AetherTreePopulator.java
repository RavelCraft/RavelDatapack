package com.connexal.raveldatapack.dimensions.aether.populators;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import com.connexal.raveldatapack.dimensions.aether.biomes.AetherBiome;
import com.github.imdabigboss.easydatapack.api.dimentions.CustomChunkPopulator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherTreePopulator extends CustomChunkPopulator {
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int amount = random.nextInt(AetherConstants.TREE_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            LocationData location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);
            if (location != null) {
                AetherBiome.spawnTree(limitedRegion, location.x(), location.y(), location.z(), location.biome(), random);
            }
        }
    }

    @Override
    public boolean isSurface(Material cover, Material ground, Biome biome) {
        return AetherBiome.canReplaceMaterial(cover, ground, biome);
    }
}
