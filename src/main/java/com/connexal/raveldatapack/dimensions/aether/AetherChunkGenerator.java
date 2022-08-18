package com.connexal.raveldatapack.dimensions.aether;

import com.connexal.raveldatapack.dimensions.CustomChunkGenerator;
import com.connexal.raveldatapack.dimensions.aether.biomes.*;
import com.connexal.raveldatapack.dimensions.aether.populators.*;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherChunkGenerator extends CustomChunkGenerator {
    private SimplexOctaveGenerator generator = null;

    public AetherChunkGenerator(BiomeProvider biomeProvider) {
        super(biomeProvider, new AetherStructurePopulator(), new AetherTreePopulator(), new AetherPlantPopulator(), new AetherOrePopulator());

        AetherBiome.registerBiome(new BirchForestBiome());
        AetherBiome.registerBiome(new DarkForestBiome());
        AetherBiome.registerBiome(new DarkMesaBiome());
        AetherBiome.registerBiome(new DesertBiome());
        AetherBiome.registerBiome(new OakForestBiome());
        AetherBiome.registerBiome(new JungleBiome());
        AetherBiome.registerBiome(new MesaBiome());
        AetherBiome.registerBiome(new OasisBiome());
        AetherBiome.registerBiome(new PlainsBiome());
        AetherBiome.registerBiome(new RedDesertBiome());
        AetherBiome.registerBiome(new RosePlainsBiome());
        AetherBiome.registerBiome(new SnowyPlainsBiome());
        AetherBiome.registerBiome(new SnowyTigaBiome());
        AetherBiome.registerBiome(new TaigaBiome());
        AetherBiome.registerBiome(new WastelandsBiome());
    }

    private void createGenerator(WorldInfo worldInfo) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 10);
            generator.setScale(AetherConstants.SACALE);
        }
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        this.createGenerator(worldInfo);

        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int localX = worldX + x;
                int localZ = worldZ + z;

                double noise = generator.noise(localX, localZ, AetherConstants.FREQUENCY, AetherConstants.AMPLITUDE) * AetherConstants.ISLAND_HEIGHT;

                int currentHeight = (int) (noise);
                currentHeight = currentHeight - AetherConstants.START_DRAWING_HEIGHT;

                if (currentHeight > 0) {
                    Biome biome = this.biomeProvider.getBiome(worldInfo, localX, 0, localZ);

                    int bottomHeight = (int) (noise * AetherConstants.ISLAND_BOTTOM_MULTIPLIER);
                    bottomHeight = (bottomHeight - AetherConstants.START_DRAWING_HEIGHT * AetherConstants.ISLAND_BOTTOM_MULTIPLIER) * -1;

                    currentHeight += AetherConstants.ISLAND_LEVEL;
                    bottomHeight += AetherConstants.ISLAND_LEVEL;
                    bottomHeight -= 1; //Remove the duplicate layer

                    currentHeight = Math.min(currentHeight, worldInfo.getMaxHeight());
                    bottomHeight = Math.min(bottomHeight, worldInfo.getMaxHeight());

                    AetherBiome.generateStack(chunkData, x, z, bottomHeight, currentHeight, biome, random);
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
