package com.connexal.raveldatapack.dimensions.aether.populators;

import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import com.connexal.raveldatapack.dimensions.aether.biomes.AetherBiome;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherNaturePopulator extends CustomDimension.CustomChunkPopulator {
    private BiomeProvider biomeProvider;

    @Override
    public void setBiomeProvider(BiomeProvider biomeProvider) {
        this.biomeProvider = biomeProvider;
    }

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int amount = random.nextInt(AetherConstants.TREE_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            LocationData location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);
            if (location != null) {
                AetherBiome.spawnTree(limitedRegion, location.x(), location.y(), location.z(), location.biome(), random);
            }
        }

        amount = random.nextInt(AetherConstants.PLANT_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            LocationData location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);
            if (location != null) {
                AetherBiome.spawnPlant(limitedRegion, location.x(), location.y(), location.z(), location.biome(), random);
            }
        }
    }

    private LocationData getRandomSurfaceXYZ(@NotNull WorldInfo worldInfo, Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int x = random.nextInt(16) + (chunkX * 16);
        int y = worldInfo.getMaxHeight();
        int z = random.nextInt(16) + (chunkZ * 16);

        Biome biome = this.biomeProvider.getBiome(worldInfo, x, 0, z);

        while (y > worldInfo.getMinHeight() + 1) {
            y -= 1;
            if (AetherBiome.isSurfaceMaterial(limitedRegion.getType(x, y, z), limitedRegion.getType(x, y - 1, z), biome)) {
                return new LocationData(x, y, z, biome);
            }
        }

        return null;
    }

    private record LocationData(int x, int y, int z, Biome biome) {
    }
}
