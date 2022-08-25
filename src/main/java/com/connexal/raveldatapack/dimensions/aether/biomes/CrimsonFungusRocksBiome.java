package com.connexal.raveldatapack.dimensions.aether.biomes;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.dimensions.aether.assets.CrimsonMushroomSpawner;
import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.*;

public class CrimsonFungusRocksBiome extends AetherBiome {
    @Override
    public Biome getVanillaBiome() {
        return Biome.ERODED_BADLANDS;
    }

    @Override
    public String getName() {
        return "Crimson Fungus Rocks";
    }

    @Override
    public void generateStack(ChunkGenerator.ChunkData chunkData, int x, int z, int minY, int maxY, Random random) {
        int underCoverDepth = random.nextInt(3) + 3;

        for (int y = minY; y < maxY; y++) {
            if (y == maxY - 1) {
                int tmp = random.nextInt(100) + 1;
                if (tmp < 85) {
                    chunkData.setBlock(x, y, z, Material.BLACKSTONE);
                } else if (tmp < 90) {
                    chunkData.setBlock(x, y, z, Material.GILDED_BLACKSTONE);
                } else {
                    chunkData.setBlock(x, y, z, Material.BLACK_CONCRETE_POWDER);
                }
            } else if (y > maxY - underCoverDepth) {
                int tmp = random.nextInt(100) + 1;
                if (tmp < 95) {
                    chunkData.setBlock(x, y, z, Material.BLACKSTONE);
                } else {
                    chunkData.setBlock(x, y, z, Material.GILDED_BLACKSTONE);
                }
            } else {
                chunkData.setBlock(x, y, z, AetherBiome.getRandomGroundMaterial(random));
            }
        }
    }

    @Override
    public boolean canReplaceMaterial(Material replaceable, Material ground) {
        boolean replaceableOk = replaceable == Material.AIR;

        boolean groundOk = ground == Material.BLACKSTONE ||
                ground == Material.GILDED_BLACKSTONE ||
                ground == Material.BLACK_CONCRETE_POWDER;

        return replaceableOk && groundOk;
    }

    @Override
    public void spawnTree(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(4) == 0) {
            CrimsonMushroomSpawner.spawn(x, y, z, limitedRegion, random);
        }
    }

    @Override
    public void spawnPlant(LimitedRegion limitedRegion, int x, int y, int z, Random random) {
        if (random.nextInt(16) == 0) {
            limitedRegion.setType(x, y, z, Material.END_ROD);

            limitedRegion.setType(x, y + 1, z, Material.PLAYER_HEAD);
            BlockState state = limitedRegion.getBlockState(x, y + 1, z);
            Skull skull = (Skull) state;

            try {
                PlayerProfile profile = RavelDatapack.getInstance().getServer().createProfile(new UUID(0, 982357), "heikgtrgpmzyhghd");
                PlayerTextures textures = profile.getTextures();
                textures.setSkin(new URL("https://textures.minecraft.net/texture/8e35f38934c371f5d15e34fa445d698fd141ed7e75293199390b0bd1678ef"));
                profile.setTextures(textures);
                skull.setPlayerProfile(profile);
                skull.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void spawnStructure(WorldInfo worldInfo, LimitedRegion limitedRegion, Random random, int chunkX, int chunkZ) {
        //No structures
    }
}
