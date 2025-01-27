package com.connexal.raveldatapack.dimensions.aether.populators;

import com.connexal.raveldatapack.dimensions.aether.biomes.AetherBiome;
import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomChunkPopulator;
import com.github.imdabigboss.easydatapack.api.utils.BlockFaces;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherOrePopulator extends CustomChunkPopulator {
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        for (int i = 0; i < 20; i++) {
            int startX = random.nextInt(16) + (chunkX * 16);
            int startY = random.nextInt(100);
            int startZ = random.nextInt(16) + (chunkZ * 16);
            Location location = new Location(null, startX, startY, startZ);

            Material startMaterial = limitedRegion.getType(location);
            if (!this.isAcceptable(startMaterial)) {
                continue;
            }

            for (int size = random.nextInt(8); size > 0; size--) {
                if (limitedRegion.isInRegion(location) && this.isAcceptable(limitedRegion.getType(location))) {
                    limitedRegion.setType(location, Material.EMERALD_ORE);
                }

                BlockFace blockFace = BlockFaces.values()[random.nextInt(BlockFaces.values().length)].getFace();
                location.add(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
            }
        }
    }

    private boolean isAcceptable(Material material) {
        return material == Material.STONE || material == Material.COBBLESTONE || material == Material.ANDESITE || material == Material.TUFF;
    }

    @Override
    public boolean isSurface(Material cover, Material ground, Biome biome) {
        return AetherBiome.canReplaceMaterial(cover, ground, biome);
    }
}
