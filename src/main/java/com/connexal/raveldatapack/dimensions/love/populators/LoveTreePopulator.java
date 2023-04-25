package com.connexal.raveldatapack.dimensions.love.populators;

import com.connexal.raveldatapack.dimensions.love.assets.LoveTreeSpawner;
import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomChunkPopulator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class LoveTreePopulator extends CustomChunkPopulator {
    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int chunkX, int chunkZ, @NonNull LimitedRegion limitedRegion) {
        int amount = random.nextInt(12) + 1;
        for (int i = 1; i < amount; i++) {
            LocationData location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);
            if (location != null) {
                LoveTreeSpawner.spawn(location.x(), location.y(), location.z(), limitedRegion, random);
            }
        }
    }

    @Override
    public boolean isSurface(Material cover, Material ground, Biome biome) {
        return cover == Material.AIR && (ground == Material.LIGHT_BLUE_TERRACOTTA || ground == Material.PURPLE_TERRACOTTA || ground == Material.PINK_TERRACOTTA || ground == Material.MAGENTA_TERRACOTTA);
    }
}
