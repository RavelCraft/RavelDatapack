package com.connexal.raveldatapack.dimensions;

import com.connexal.raveldatapack.dimensions.aether.biomes.AetherBiome;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class CustomChunkPopulator {
    private BiomeProvider biomeProvider;

    protected BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    public void setBiomeProvider(BiomeProvider biomeProvider) {
        this.biomeProvider = biomeProvider;
    }

    public abstract void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion);

    protected LocationData getRandomSurfaceXYZ(@NotNull WorldInfo worldInfo, Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int x = random.nextInt(16) + (chunkX * 16);
        int y = worldInfo.getMaxHeight();
        int z = random.nextInt(16) + (chunkZ * 16);

        Biome biome = this.getBiomeProvider().getBiome(worldInfo, x, 0, z);

        while (y > worldInfo.getMinHeight() + 1) {
            y -= 1;
            if (AetherBiome.canReplaceMaterial(limitedRegion.getType(x, y, z), limitedRegion.getType(x, y - 1, z), biome)) {
                return new LocationData(x, y, z, biome);
            }
        }

        return null;
    }

    protected record LocationData(int x, int y, int z, Biome biome) {
    }
}
