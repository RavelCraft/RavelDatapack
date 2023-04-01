package com.connexal.raveldatapack.dimensions.aether;

import com.connexal.raveldatapack.dimensions.aether.biomes.*;
import com.connexal.raveldatapack.dimensions.aether.populators.AetherOrePopulator;
import com.connexal.raveldatapack.dimensions.aether.populators.AetherPlantPopulator;
import com.connexal.raveldatapack.dimensions.aether.populators.AetherStructurePopulator;
import com.connexal.raveldatapack.dimensions.aether.populators.AetherTreePopulator;
import com.github.imdabigboss.easydatapack.api.dimentions.CustomChunkGenerator;
import com.github.imdabigboss.easydatapack.api.utils.math.ExtraMath;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherChunkGenerator extends CustomChunkGenerator {
    private SimplexOctaveGenerator generator1 = null;
    private SimplexOctaveGenerator generator2 = null;

    public AetherChunkGenerator(BiomeProvider biomeProvider) {
        super(biomeProvider, new AetherStructurePopulator(), new AetherTreePopulator(), new AetherPlantPopulator(), new AetherOrePopulator());

        AetherBiome.registerBiome(new MangroveForestBiome());
        AetherBiome.registerBiome(new DarkMesaBiome());
        AetherBiome.registerBiome(new DesertBiome());
        AetherBiome.registerBiome(new TaigaBiome());
        AetherBiome.registerBiome(new CrystalFieldsBiome());
        AetherBiome.registerBiome(new CrimsonFungusRocksBiome());
        AetherBiome.registerBiome(new OasisBiome());
        AetherBiome.registerBiome(new PlainsBiome());
        AetherBiome.registerBiome(new RedDesertBiome());
        AetherBiome.registerBiome(new RosePlainsBiome());
        AetherBiome.registerBiome(new IceMushroomForestBiome());
        AetherBiome.registerBiome(new JibstoneBiome());
        AetherBiome.registerBiome(new FrogForestBiome());
        AetherBiome.registerBiome(new WastelandsBiome());
    }

    private void createGenerator(WorldInfo worldInfo) {
        if (generator1 == null) {
            generator1 = new SimplexOctaveGenerator(worldInfo.getSeed(), 10);
            generator1.setScale(AetherConstants.SCALE_1);
        }
        if (generator2 == null) {
            generator2 = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
            generator2.setScale(AetherConstants.SCALE_2);
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

                double noise = generator1.noise(localX, localZ, AetherConstants.FREQUENCY_1, AetherConstants.AMPLITUDE_1) * AetherConstants.ISLAND_HEIGHT;
                double noise2 = generator2.noise(localX, localZ, AetherConstants.FREQUENCY_2, AetherConstants.AMPLITUDE_2) * 1.5;
                if (noise2 >= 1) {
                    noise = noise * noise2;
                }

                int currentHeight = ((int) noise) - AetherConstants.START_DRAWING_HEIGHT;

                if (currentHeight > 0) {
                    Biome biome = this.biomeProvider.getBiome(worldInfo, localX, 0, localZ);

                    int bottomHeight = (int) (noise * AetherConstants.ISLAND_BOTTOM_MULTIPLIER);
                    bottomHeight = (bottomHeight - AetherConstants.START_DRAWING_HEIGHT * AetherConstants.ISLAND_BOTTOM_MULTIPLIER) * -1;

                    currentHeight += AetherConstants.ISLAND_LEVEL;
                    bottomHeight += AetherConstants.ISLAND_LEVEL - 1;

                    currentHeight = ExtraMath.clamp(currentHeight, worldInfo.getMinHeight(), worldInfo.getMaxHeight());
                    bottomHeight = ExtraMath.clamp(bottomHeight, worldInfo.getMinHeight(), worldInfo.getMaxHeight());

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
