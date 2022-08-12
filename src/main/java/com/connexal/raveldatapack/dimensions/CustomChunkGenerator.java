package com.connexal.raveldatapack.dimensions;

import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class CustomChunkGenerator extends ChunkGenerator {
    protected final BiomeProvider biomeProvider;
    private final List<BlockPopulator> chunkPopulators;

    public CustomChunkGenerator(BiomeProvider biomeProvider, CustomChunkPopulator... chunkPopulators) {
        this.biomeProvider = biomeProvider;
        this.chunkPopulators = Arrays.asList(chunkPopulators);

        for (CustomChunkPopulator chunkPopulator : chunkPopulators) {
            chunkPopulator.setBiomeProvider(this.biomeProvider);
        }
    }

    public CustomChunkGenerator(BiomeProvider biomeProvider) {
        this.biomeProvider = biomeProvider;
        this.chunkPopulators = new ArrayList<>();
    }

    @Override
    public abstract void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData);

    @NotNull
    public List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return this.chunkPopulators;
    }

    @Override
    @Nullable
    public BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return this.biomeProvider;
    }

    @Override
    public abstract boolean shouldGenerateSurface();

    @Override
    public abstract boolean shouldGenerateBedrock();

    @Override
    public abstract boolean shouldGenerateCaves();

    @Override
    public abstract boolean shouldGenerateDecorations();

    @Override
    public abstract boolean shouldGenerateMobs();

    @Override
    public abstract boolean shouldGenerateStructures();
}
