package com.connexal.raveldatapack.dimensions.love;

import com.connexal.raveldatapack.dimensions.love.populators.*;
import com.github.imdabigboss.easydatapack.api.dimentions.CustomChunkGenerator;
import org.bukkit.Material;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class LoveChunkGenerator extends CustomChunkGenerator {
    private SimplexOctaveGenerator generator1 = null;
    private SimplexOctaveGenerator generator2 = null;

    public LoveChunkGenerator(BiomeProvider biomeProvider) {
        super(biomeProvider, new LoveTreePopulator(), new LovePlantPopulator());
    }

    private void createGenerator(WorldInfo worldInfo) {
        if (generator1 == null) {
            generator1 = new SimplexOctaveGenerator(worldInfo.getSeed(), 10);
            generator1.setScale(0.008D);
        }
        if (generator2 == null) {
            generator2 = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
            generator2.setScale(0.005D);
        }
    }

    public final Material[] topMaterials = new Material[] { Material.LIGHT_BLUE_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.PINK_TERRACOTTA, Material.MAGENTA_TERRACOTTA };

    @Override
    public void generateNoise(@NonNull WorldInfo worldInfo, @NonNull Random random, int chunkX, int chunkZ, @NonNull ChunkData chunkData) {
        this.createGenerator(worldInfo);

        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int localX = worldX + x;
                int localZ = worldZ + z;

                double noise1 = generator1.noise(localX, localZ, 1D, 0.5D) * 15;
                double noise2 = generator2.noise(localX, localZ, 0.5D, 1D) * 5;

                int height = (int) (noise1 + noise2 + 100);

                if (height > worldInfo.getMinHeight() && height < worldInfo.getMaxHeight()) {
                    for (int y = worldInfo.getMinHeight(); y <= height; y++) {
                        Material selected;

                        if (y < height - 10) {
                            selected = Material.PURPLE_CONCRETE;
                        } else if (y == worldInfo.getMinHeight()) {
                            selected = Material.BEDROCK;
                        } else {
                            selected = topMaterials[random.nextInt(topMaterials.length)];
                        }

                        chunkData.setBlock(x, y, z, selected);
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldGenerateSurface() {
        return false;
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return false;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return false;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return false;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return false;
    }
}
