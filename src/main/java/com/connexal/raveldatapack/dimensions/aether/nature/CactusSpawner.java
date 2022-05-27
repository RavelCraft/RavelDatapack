package com.connexal.raveldatapack.dimensions.aether.nature;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class CactusSpawner {
    public static void spawnCactus(Location location, LimitedRegion limitedRegion, Random random) {
        for (int j = 0; j < random.nextInt(4) + 2; j++) {
            limitedRegion.setType(location, Material.CACTUS);
            location.setY(location.getY() + 1);
        }

        if (random.nextInt(2) == 0) {
            location.setY(location.getY() - 1);

            if (random.nextBoolean()) { //X or Z axis
                if (random.nextBoolean()) { //X +
                    Location tmpLocation = location.clone();
                    tmpLocation.setX(location.getX() + 1);

                    spawnCactusInternal(tmpLocation, limitedRegion, random);
                }
                if (random.nextBoolean()) { //X -
                    Location tmpLocation = location.clone();
                    tmpLocation.setX(location.getX() - 1);

                    spawnCactusInternal(tmpLocation, limitedRegion, random);
                }
            } else {
                if (random.nextBoolean()) { //Z +
                    Location tmpLocation = location.clone();
                    tmpLocation.setZ(location.getZ() + 1);

                    spawnCactusInternal(tmpLocation, limitedRegion, random);
                }
                if (random.nextBoolean()) { //Z -
                    Location tmpLocation = location.clone();
                    tmpLocation.setZ(location.getZ() - 1);

                    spawnCactusInternal(tmpLocation, limitedRegion, random);
                }
            }
        }
    }

    private static void spawnCactusInternal(Location location, LimitedRegion limitedRegion, Random random) {
        Location tmpLocation = location.clone();

        for (int i = 0; i < random.nextInt(2) + 3; i++) {
            limitedRegion.setType(location, Material.CACTUS);
            location.setY(location.getY() + 1);
        }
    }
}
