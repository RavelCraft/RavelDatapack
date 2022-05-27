package com.connexal.raveldatapack.dimensions.aether;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class AetherBiomeProvider extends BiomeProvider {
    private SimplexOctaveGenerator generator = null;

    private void createGenerator(WorldInfo worldInfo) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 10);
            generator.setScale(0.005D);
        }
    }

    @Override
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        this.createGenerator(worldInfo);

        double noise = generator.noise(x, z, 0.5, 0.8, false) + 1;
        if (noise < 0.5) {
            return Biome.PLAINS;
        } else if (noise < 1.5) {
            return Biome.FOREST;
        } else {
            return Biome.DESERT;
        }
    }

    @Override
    public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return Arrays.asList(Biome.PLAINS, Biome.FOREST, Biome.DESERT);
    }
}
