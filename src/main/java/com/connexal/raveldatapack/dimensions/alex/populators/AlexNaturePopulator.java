package com.connexal.raveldatapack.dimensions.alex.populators;

import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.alex.AlexConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AlexNaturePopulator extends CustomDimension.CustomChunkPopulator {
    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int amount = random.nextInt(AlexConstants.TREE_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            Location location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);

            if (location != null) {
                limitedRegion.generateTree(location, random, TreeType.TREE);
            }
        }

        amount = random.nextInt(AlexConstants.GRASS_DENSITY) + 1;
        for (int i = 1; i < amount; i++) {
            Location location = this.getRandomSurfaceXYZ(worldInfo, random, chunkX, chunkZ, limitedRegion);

            if (location != null) {
                limitedRegion.setType(location, Material.GRASS);
            }
        }
    }

    private Location getRandomSurfaceXYZ(@NotNull WorldInfo worldInfo, Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        int startX = random.nextInt(16) + (chunkX * 16);
        int startY = worldInfo.getMaxHeight() - 1;
        int startZ = random.nextInt(16) + (chunkZ * 16);

        Location location = new Location(null, startX, startY, startZ);
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
        return material == Material.AIR || material == Material.GRASS;
    }

    private boolean isSurfaceAcceptable(Material material) {
        return material == Material.DIRT || material == Material.GRASS_BLOCK;
    }
}
