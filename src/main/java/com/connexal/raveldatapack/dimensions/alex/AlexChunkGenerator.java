package com.connexal.raveldatapack.dimensions.alex;

import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.alex.populators.AlexNaturePopulator;
import com.connexal.raveldatapack.dimensions.alex.populators.AlexOrePopulator;
import org.bukkit.Material;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AlexChunkGenerator extends CustomDimension.CustomChunkGenerator {
    private SimplexOctaveGenerator generator = null;
    private int currentHeight;

    public AlexChunkGenerator(AlexBiomeProvider biomeProvider) {
        super(biomeProvider, new AlexNaturePopulator(), new AlexOrePopulator());
    }

    private void createGenerator(WorldInfo worldInfo) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 7);
            generator.setScale(0.005D);
        }
    }

    @Override
    public void generateWorld(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        this.createGenerator(worldInfo);

        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int localX = worldX + x;
                int localZ = worldZ + z;
                currentHeight = (int) ((generator.noise(localX, localZ, AlexConstants.FREQUENCY, AlexConstants.AMPLITUDE, true) + 1) * AlexConstants.HEIGHT);

                chunkData.setBlock(x, currentHeight, z, AlexConstants.COVER_MATERIAL);
                for (int y = 1; y < AlexConstants.COVER_HEIGHT; y++) {
                    chunkData.setBlock(x, currentHeight - y, z, AlexConstants.UNDER_COVER_MATERIAL);
                }

                for (int y = currentHeight - AlexConstants.COVER_HEIGHT; y > worldInfo.getMinHeight(); y--) {
                    chunkData.setBlock(x, y, z, random.nextBoolean() ? AlexConstants.BASE_MATERIAL_1 : AlexConstants.BASE_MATERIAL_2);
                }
                chunkData.setBlock(x, worldInfo.getMinHeight(), z, Material.BEDROCK);
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
