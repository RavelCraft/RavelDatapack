package com.connexal.raveldatapack.dimensions;

import com.connexal.raveldatapack.RavelDatapack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class CustomDimension {
    public abstract Material getPortalFrameMaterial();

    public abstract String getName();

    public abstract World.Environment getEnvironment();

    public abstract Location dimensionToNormal(Location location);

    public abstract Location normalToDimension(Location location);

    protected abstract BiomeProvider getBiomeProvider();

    protected abstract ChunkGenerator getChunkGenerator();

    public World createWorld() {
        WorldCreator worldCreator = new WorldCreator(this.getName());
        worldCreator.biomeProvider(this.getBiomeProvider());
        worldCreator.generator(this.getChunkGenerator());
        worldCreator.environment(this.getEnvironment());
        worldCreator.generateStructures(false);
        worldCreator.hardcore(false);
        worldCreator.seed(RavelDatapack.getSeed());

        return worldCreator.createWorld();
    }

    public abstract static class CustomChunkPopulator extends BlockPopulator {
        private BiomeProvider biomeProvider;

        protected BiomeProvider getBiomeProvider() {
            return biomeProvider;
        }

        public void setBiomeProvider(BiomeProvider biomeProvider) {
            this.biomeProvider = biomeProvider;
        }

        public abstract void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion);
    }

    public abstract static class CustomChunkGenerator extends ChunkGenerator {
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

        public abstract void generateWorld(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData);

        public abstract void generateStructure(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData);

        @Override
        public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
            this.generateWorld(worldInfo, random, chunkX, chunkZ, chunkData);
            this.generateStructure(worldInfo, random, chunkX, chunkZ, chunkData);
        }

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
}
