package com.connexal.raveldatapack.dimensions.aether.populators;

import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import com.connexal.raveldatapack.dimensions.aether.nature.CactusSpawner;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherNaturePopulator extends CustomDimension.CustomChunkPopulator {
    private final BlockData coralBlockData;

    public AetherNaturePopulator() {
        coralBlockData = Material.DEAD_FIRE_CORAL.createBlockData();
        ((Waterlogged) coralBlockData).setWaterlogged(false);
    }

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int amount = random.nextInt(AetherConstants.PLANT_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            Location location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);

            if (location == null) {
                continue;
            }

            Biome biome = limitedRegion.getBiome(location);
            if (biome == Biome.DESERT) {
                if (random.nextInt(4) == 0) {
                    CactusSpawner.spawnCactus(location, limitedRegion, random);
                }
            } else if (biome == Biome.FOREST) {
                limitedRegion.generateTree(location, random, random.nextInt(5) == 4 ? TreeType.BIG_TREE : TreeType.TREE);
            }
        }

        amount = random.nextInt(AetherConstants.SMALL_PLANT_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            Location location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);

            if (location == null) {
                continue;
            }

            Biome biome = limitedRegion.getBiome(location);
            if (biome == Biome.DESERT) {
                if (random.nextInt(16) == 0) {
                    if (random.nextBoolean()) {
                        limitedRegion.setType(location, Material.DEAD_BUSH);
                    } else {
                        limitedRegion.setBlockData(location, this.coralBlockData);
                    }
                }
            } else {
                int randomPlant = random.nextInt(101);
                if (biome == Biome.FOREST && randomPlant > 95) {
                    limitedRegion.setType(location, Material.RED_MUSHROOM);
                } else if (biome == Biome.FOREST && randomPlant > 90) {
                    limitedRegion.setType(location, Material.BROWN_MUSHROOM);
                } else if (biome == Biome.PLAINS && randomPlant > 80) {
                    limitedRegion.setType(location, Material.CORNFLOWER);
                } else if (biome == Biome.PLAINS && randomPlant > 70) {
                    limitedRegion.setType(location, Material.LILY_OF_THE_VALLEY);
                } else if (biome == Biome.PLAINS && randomPlant > 60) {
                    limitedRegion.setType(location, Material.ALLIUM);
                } else if (randomPlant > 50) {
                    limitedRegion.setType(location, Material.FERN);
                } else {
                    limitedRegion.setType(location, Material.GRASS);
                }
            }
        }
    }

    private Location getRandomSurfaceXYZ(@NotNull WorldInfo worldInfo, Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int startX = random.nextInt(16) + (chunkX * 16);
        int startY = worldInfo.getMaxHeight() - 1;
        int startZ = random.nextInt(16) + (chunkZ * 16);

        Location location = new Location(null,  startX, startY, startZ);
        if (!limitedRegion.isInRegion(location)) {
            return null;
        }

        while (location.getY() > worldInfo.getMinHeight()) {
            Location tmp = location.clone();
            location.setY(location.getY() - 1);
            if (this.isSurfaceAcceptableReplace(limitedRegion.getType(tmp)) && this.isSurfaceAcceptable(limitedRegion.getType(location))) {
                return tmp;
            }
        }

        return null;
    }

    private boolean isSurfaceAcceptableReplace(Material material) {
        return material == Material.AIR || material == Material.GRASS || material == Material.DEAD_BUSH || material == Material.DEAD_FIRE_CORAL;
    }

    private boolean isSurfaceAcceptable(Material material) {
        return material == Material.DIRT || material == Material.GRASS_BLOCK || material == Material.SAND;
    }
}
