package com.connexal.raveldatapack.dimensions.aether;

import com.github.imdabigboss.easydatapack.api.utils.VoronoiGenerator;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class AetherBiomeProvider extends BiomeProvider {
    private SimplexOctaveGenerator generator = null;
    private VoronoiGenerator voronoiGenerator = null;

    private void createGenerator(WorldInfo worldInfo) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 10);
            generator.setScale(0.003D);
        }
        if (voronoiGenerator == null) {
            voronoiGenerator = new VoronoiGenerator(worldInfo.getSeed(), (short) 0);
        }
    }

    @Override
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        this.createGenerator(worldInfo);

        double noise = generator.noise(x, z, 0.5, 0.8, false) + 1;
        double voronoi = voronoiGenerator.noise(x, 0, z, 0.001);

        if (noise < 0) { // Snowy
            if (voronoi < 0.3) {
                return Biome.SNOWY_TAIGA;
            } else {
                return Biome.SNOWY_PLAINS;
            }
        } else if (noise < 0.2) { // Plains
            if (voronoi < 0.4) {
                return Biome.MEADOW;
            } else {
                return Biome.PLAINS;
            }
        } else if (noise < 1.5) { // Medium
            if (voronoi < 0.15) {
                return Biome.BIRCH_FOREST;
            } else if (voronoi < 0.3) {
                return Biome.JUNGLE;
            } else if (voronoi < 0.6) {
                return Biome.TAIGA;
            } else {
                return Biome.FOREST;
            }
        } else if (noise < 1.6) { // Tropical
            if (voronoi < 0.3) {
                return Biome.BADLANDS;
            } else if (voronoi < 0.4) {
                return Biome.BEACH;
            } else {
                return Biome.ERODED_BADLANDS;
            }
        } else { // Desert
            if (voronoi < 0.3) {
                return Biome.SAVANNA_PLATEAU;
            } else if (voronoi < 0.6) {
                return Biome.DESERT;
            } else {
                return Biome.WOODED_BADLANDS;
            }
        }
    }

    @Override
    public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return Arrays.asList(Biome.SNOWY_TAIGA, Biome.SNOWY_PLAINS, Biome.MEADOW, Biome.PLAINS, Biome.BIRCH_FOREST, Biome.FOREST, Biome.TAIGA, Biome.JUNGLE, Biome.BADLANDS, Biome.ERODED_BADLANDS, Biome.BEACH, Biome.WOODED_BADLANDS, Biome.SAVANNA_PLATEAU, Biome.DESERT);
    }
}
