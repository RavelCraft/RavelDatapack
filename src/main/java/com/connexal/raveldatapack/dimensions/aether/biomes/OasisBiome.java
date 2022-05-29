package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.dimensions.aether.assets.OasisSpawner;
import com.connexal.raveldatapack.dimensions.aether.assets.PalmTreeSpawner;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class OasisBiome extends AetherBiome {
    @Override
    public Biome getBiome() {
        return Biome.BEACH;
    }

    @Override
    public void drawStackInternal(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                chunkData.setBlock(x, y, z, random.nextBoolean() ? Material.SAND : Material.SMOOTH_SANDSTONE);
            } else if (y > maxY - underCoverDepth) {
                chunkData.setBlock(x, y, z, Material.SANDSTONE);
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomBaseMaterial(random));
            }
        }
    }

    @Override
    public boolean isSurfaceMaterialInternal(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR;

        boolean groundOk = ground == Material.SAND ||
                ground == Material.SMOOTH_SANDSTONE;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTreeInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            PalmTreeSpawner.spawnPalmTree(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlantInternal(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        //No plants
    }

    @Override
    public void spawnStructureInternal(ChunkGenerator.ChunkData chunkData, int chunkX, int chunkZ, Random random) {
        int startX = -1;
        int startY = -1;
        int startZ = -1;

        firstLoop:
        for (int x = 0; x < 16 - OasisSpawner.DIAMETER; x++) {
            secondLoop:
            for (int z = 0; z < 16 - OasisSpawner.DIAMETER; z++) {
                Integer y = this.getSurfaceLevel(chunkData, x, z);
                if (y == null) {
                    continue;
                }

                Integer y2 = this.getSurfaceLevel(chunkData, x + OasisSpawner.DIAMETER, z + OasisSpawner.DIAMETER);
                if (y2 == null) {
                    continue;
                }
                if (Math.abs(y - y2) > OasisSpawner.HEIGHT_DIFF_ACCEPTED) {
                    continue;
                }

                for (int x2 = x; x2 < x + OasisSpawner.DIAMETER; x2++) {
                    for (int z2 = z; z2 < z + OasisSpawner.DIAMETER; z2++) {
                        Integer tmpY = this.getSurfaceLevel(chunkData, x2, z2);
                        if (tmpY == null) {
                            continue secondLoop;
                        }
                        if (Math.abs(tmpY - y) > OasisSpawner.HEIGHT_DIFF_ACCEPTED) {
                            continue secondLoop;
                        }
                    }
                }

                startX = x;
                if (y > y2) {
                    startY = y2;
                } else {
                    startY = y;
                }
                if (Math.abs(y2 - y) > 1) {
                    startY += 1;
                }
                startZ = z;
                break firstLoop;
            }
        }

        if (startX == -1) {
            return;
        }

        if (random.nextInt(3) == 0) {
            OasisSpawner.spawnOasis(startX, startY, startZ, chunkData, random);
        }
    }
}
