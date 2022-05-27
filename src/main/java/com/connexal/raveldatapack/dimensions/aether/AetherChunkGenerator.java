package com.connexal.raveldatapack.dimensions.aether;

import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.aether.populators.AetherNaturePopulator;
import com.connexal.raveldatapack.dimensions.aether.populators.AetherOrePopulator;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherChunkGenerator extends CustomDimension.CustomChunkGenerator {
    private SimplexOctaveGenerator generator = null;
    private SimplexOctaveGenerator heightGenerator = null;

    public AetherChunkGenerator(BiomeProvider biomeProvider) {
        super(biomeProvider, new AetherNaturePopulator(), new AetherOrePopulator());
    }

    private void createGenerator(WorldInfo worldInfo) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(worldInfo.getSeed(), 10);
            generator.setScale(AetherConstants.SACALE);
        }
        if (heightGenerator == null) {
            heightGenerator = new SimplexOctaveGenerator(worldInfo.getSeed(), 8);
            heightGenerator.setScale(AetherConstants.HEIGHT_SCALE);
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

                double heightNoise = heightGenerator.noise(localX, localZ, AetherConstants.HEIGHT_FREQUENCY, AetherConstants.HEIGHT_AMPLITUDE);
                double noise = generator.noise(localX, localZ, AetherConstants.FREQUENCY, AetherConstants.AMPLITUDE);
                double noiseAndHeight = noise * AetherConstants.ISLAND_HEIGHT + heightNoise * 15;

                int currentHeight = (int) (noiseAndHeight);
                currentHeight = currentHeight - AetherConstants.START_DRAWING_HEIGHT;

                if (currentHeight > 0) {
                    Biome biome = this.biomeProvider.getBiome(worldInfo, localX, 0, localZ);

                    int bottomHeight = (int) (noiseAndHeight * AetherConstants.ISLAND_BOTTOM_MULTIPLIER);
                    bottomHeight = bottomHeight - AetherConstants.START_DRAWING_HEIGHT * AetherConstants.ISLAND_BOTTOM_MULTIPLIER;

                    int sandstoneRandom = random.nextInt(6) + 3;
                    int dirtRandom = random.nextInt(3) + 1;

                    for (int y = (bottomHeight * -1); y < currentHeight; y++) {
                        Material setMaterial = this.getRandomBaseMaterial(random);
                        if (setMaterial == Material.MOSSY_COBBLESTONE && y != (bottomHeight * -1)) {
                            setMaterial = Material.COBBLESTONE;
                        }

                        if (biome != Biome.BEACH && biome != Biome.DESERT) {
                            if (y == currentHeight - 1) {
                                setMaterial = Material.GRASS_BLOCK;
                            } else if (y > (currentHeight - 1) - dirtRandom) {
                                setMaterial = Material.DIRT;
                            }
                        } else {
                            if (y > (currentHeight - 1) - 2) {
                                setMaterial = Material.SAND;
                            } else if (y > (currentHeight - 1) - sandstoneRandom) {
                                setMaterial = Material.SANDSTONE;
                            }
                        }

                        chunkData.setBlock(x, AetherConstants.ISLAND_LEVEL + y, z, setMaterial);
                    }
                }
            }
        }
    }

    private Material getRandomBaseMaterial(Random random) {
        int randomMaterial = random.nextInt(101);
        if (randomMaterial < 50) {
            return Material.STONE;
        } else if (randomMaterial < 80) {
            return Material.COBBLESTONE;
        } else if (randomMaterial < 90) {
            return Material.MOSSY_COBBLESTONE;
        } else if (randomMaterial < 95) {
            return Material.ANDESITE;
        } else {
            return Material.TUFF;
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
