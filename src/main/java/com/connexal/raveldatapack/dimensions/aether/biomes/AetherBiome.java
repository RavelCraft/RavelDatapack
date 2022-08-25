package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.utils.RavelMath;
import com.connexal.raveldatapack.utils.schematics.Schematic;
import com.connexal.raveldatapack.utils.schematics.Schematics;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class AetherBiome {
    private static final Map<Biome, AetherBiome> biomeMap = new HashMap<>();
    private static final Map<String, Schematic> schematicCache = new HashMap<>();

    public static void registerBiome(AetherBiome biome) {
        if (biomeMap.containsKey(biome.getVanillaBiome())) {
            throw new IllegalArgumentException("Biome already registered " + biome.getVanillaBiome().toString());
        }
        biomeMap.put(biome.getVanillaBiome(), biome);
    }

    public abstract Biome getVanillaBiome();

    public abstract String getName();

    public static String getBiomeName(Biome biome) {
        if (biomeMap.containsKey(biome)) {
            return biomeMap.get(biome).getName();
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
        return "Unknown biome";
    }

    public static void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).generateStack(chunkData, x, z, minY, maxY, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
        for (int y = minY; y < maxY; y++) {
            chunkData.setBlock(x, y, z, getRandomGroundMaterial(random));
        }
    }

    public abstract void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random);

    public static boolean canReplaceMaterial(Material replaceable, Material ground, Biome biome) {
        if (biomeMap.containsKey(biome)) {
            return biomeMap.get(biome).canReplaceMaterial(replaceable, ground);
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
        return false;
    }

    public abstract boolean canReplaceMaterial(Material replaceable, Material ground);

    public static void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).spawnTree(limitedRegion, x, y, z, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
    }

    public abstract void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random);

    public static void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Biome biome, Random random) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).spawnPlant(limitedRegion, x, y, z, random);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
    }

    public abstract void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random);

    public static void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ, Biome biome) {
        if (biomeMap.containsKey(biome)) {
            biomeMap.get(biome).spawnStructure(worldInfo, limitedRegion, random, chunkX, chunkZ);
            return;
        }

        RavelDatapack.getLog().warning("Unknown biome " + biome.toString());
    }

    public abstract void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ);

    public static Material getRandomGroundMaterial(Random random) {
        int randomMaterial = random.nextInt(101);
        if (randomMaterial < 60) {
            return Material.STONE;
        } else if (randomMaterial < 90) {
            return Material.COBBLESTONE;
        } else if (randomMaterial < 95) {
            return Material.ANDESITE;
        } else {
            return Material.TUFF;
        }
    }

    public Integer getSurfaceLevel(WorldInfo worldInfo, LimitedRegion limitedRegion, int x, int z) {
        int y = worldInfo.getMaxHeight();

        while (y > worldInfo.getMinHeight() + 1) {
            y -= 1;
            if (this.canReplaceMaterial(limitedRegion.getType(x, y, z), limitedRegion.getType(x, y - 1, z))) {
                return y;
            }
        }

        return null;
    }

    public Schematic getSchematicFromCache(String schematicName) {
        if (schematicCache.containsKey(schematicName)) {
            return schematicCache.get(schematicName);
        }

        Schematic schematic = Schematics.loadSchematic(schematicName);
        if (schematic == null) {
            RavelDatapack.getLog().warning("Could not load schematic " + schematicName);
            return null;
        }

        schematicCache.put(schematicName, schematic);
        return schematic;
    }

    public Location getAcceptableStructureSpawn(WorldInfo worldInfo, LimitedRegion limitedRegion, int worldX, int worldZ, int width, int depth, int heightTolerance) {
        Biome biome = this.getVanillaBiome();

        if (width > 16 || depth > 16) {
            RavelDatapack.getLog().warning("Checking bigger than 16x16 is not possible.");
            return null;
        }

        int endX = worldX + (16 - width);
        if (endX == worldX) {
            endX += 1;
        }

        int endZ = worldZ + (16 - depth);
        if (endZ == worldZ) {
            endZ += 1;
        }

        for (int x = worldX; x < endX; x++) {
            secondLoop:
            for (int z = worldZ; z < endZ; z++) {
                Integer y1 = this.getSurfaceLevel(worldInfo, limitedRegion, x, z);
                Integer y2 = this.getSurfaceLevel(worldInfo, limitedRegion, x, z + depth);
                Integer y3 = this.getSurfaceLevel(worldInfo, limitedRegion, x + width, z);
                Integer y4 = this.getSurfaceLevel(worldInfo, limitedRegion, x + width, z + depth);
                if (y1 == null || y2 == null || y3 == null || y4 == null) {
                    continue;
                }

                int minY = RavelMath.min(y1, y2, y3, y4);
                int maxY = RavelMath.max(y1, y2, y3, y4);

                int heightDiff = maxY - minY;
                if (heightDiff > heightTolerance) {
                    continue;
                }

                for (int x2 = x; x2 < x + width; x2++) {
                    for (int z2 = z; z2 < z + depth; z2++) {
                        Integer tmpY = this.getSurfaceLevel(worldInfo, limitedRegion, x2, z2);
                        if (tmpY == null) {
                            continue secondLoop;
                        }

                        if (tmpY < minY - (heightTolerance - heightDiff)) {
                            continue secondLoop;
                        } else if (tmpY < minY) {
                            minY = tmpY;
                            heightDiff = maxY - minY;
                        }

                        if (tmpY > maxY + (heightTolerance - heightDiff)) {
                            continue secondLoop;
                        } else if (tmpY > maxY) {
                            maxY = tmpY;
                            heightDiff = maxY - minY;
                        }

                        Biome tmpBiome = limitedRegion.getBiome(x2, 0, z2);
                        if (tmpBiome != biome) {
                            continue secondLoop;
                        }
                    }
                }

                return new Location(null, x, maxY, z);
            }
        }

        return null;
    }
}
