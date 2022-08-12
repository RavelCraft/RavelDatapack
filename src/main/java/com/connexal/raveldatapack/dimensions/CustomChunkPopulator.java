package com.connexal.raveldatapack.dimensions;

import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class CustomChunkPopulator extends BlockPopulator {
    private BiomeProvider biomeProvider;

    protected BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    public void setBiomeProvider(BiomeProvider biomeProvider) {
        this.biomeProvider = biomeProvider;
    }

    public abstract void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion);
}
