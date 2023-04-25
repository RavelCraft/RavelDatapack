package com.connexal.raveldatapack.dimensions.love.populators;

import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomChunkPopulator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class LovePlantPopulator extends CustomChunkPopulator {
    @Override
    public void populate(@NonNull WorldInfo worldInfo, @NonNull Random random, int i, int i1, @NonNull LimitedRegion limitedRegion) {

    }

    @Override
    public boolean isSurface(@NonNull Material material, @NonNull Material material1, @NonNull Biome biome) {
        return false;
    }
}
