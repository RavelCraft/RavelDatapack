package com.connexal.raveldatapack.dimensions.aether.assets;

import com.connexal.raveldatapack.dimensions.aether.AetherConstants;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.generator.LimitedRegion;

import java.util.Random;

public class PalmTreeSpawner {
    public static void spawn(int x, int y, int z, LimitedRegion limitedRegion, Random random) {
        int xDiff = 0;
        int zDiff = 0;

        int randomDirection = random.nextInt(4);
        switch (randomDirection) {
            case 0 -> xDiff = -1;
            case 1 -> xDiff = 1;
            case 2 -> zDiff = -1;
            case 3 -> zDiff = 1;
        }

        int tmpX = x;
        int tmpY = y;
        int tmpZ = z;

        for (int i = 0; i < 3; i++) { //Make the trunk
            for (int j = 0; j < 2; j++) {
                limitedRegion.setType(tmpX, tmpY, tmpZ, Material.JUNGLE_LOG);
                tmpY += 1;
            }
            tmpX += xDiff;
            tmpZ += zDiff;
        }

        tmpX -= xDiff; //Undo the last change
        tmpZ -= zDiff;

        limitedRegion.setBlockData(tmpX, tmpY + 1, tmpZ, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA); //Top bit

        for (int i = -4; i <= 4; i++) { //Sides X
            if (i == -4 || i == 4) {
                limitedRegion.setBlockData(tmpX + i, tmpY - 1, tmpZ, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            } else {
                limitedRegion.setBlockData(tmpX + i, tmpY, tmpZ, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            }
        }
        for (int i = -4; i <= 4; i++) { //Sides Z
            if (i == -4 || i == 4) {
                limitedRegion.setBlockData(tmpX, tmpY - 1, tmpZ + i, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            } else {
                limitedRegion.setBlockData(tmpX, tmpY, tmpZ + i, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            }
        }

        for (int i = -3; i <= 3; i++) { //Corners 1
            if (i == -3 || i == 3) {
                limitedRegion.setBlockData(tmpX + i, tmpY - 1, tmpZ + i, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            } else {
                limitedRegion.setBlockData(tmpX + i, tmpY, tmpZ + i, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            }
        }
        for (int i = -3; i <= 3; i++) { //Corners 2
            if (i == -3 || i == 3) {
                limitedRegion.setBlockData(tmpX + i, tmpY - 1, tmpZ - i, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            } else {
                limitedRegion.setBlockData(tmpX + i, tmpY, tmpZ - i, AetherConstants.JUNGLE_LEAVES_BLOCK_DATA);
            }
        }

        tmpY -= 1; //Go down for the coconuts
        BlockData cocoa1 = Material.COCOA.createBlockData();
        ((Directional) cocoa1).setFacing(BlockFace.WEST);
        ((Ageable) cocoa1).setAge(2);
        BlockData cocoa2 = Material.COCOA.createBlockData();
        ((Directional) cocoa2).setFacing(BlockFace.EAST);
        ((Ageable) cocoa2).setAge(2);
        BlockData cocoa3 = Material.COCOA.createBlockData();
        ((Directional) cocoa3).setFacing(BlockFace.NORTH);
        ((Ageable) cocoa3).setAge(2);
        BlockData cocoa4 = Material.COCOA.createBlockData();
        ((Directional) cocoa4).setFacing(BlockFace.SOUTH);
        ((Ageable) cocoa4).setAge(2);

        limitedRegion.setBlockData(tmpX + 1, tmpY, tmpZ, cocoa1);
        limitedRegion.setBlockData(tmpX - 1, tmpY, tmpZ, cocoa2);
        limitedRegion.setBlockData(tmpX, tmpY, tmpZ + 1, cocoa3);
        limitedRegion.setBlockData(tmpX, tmpY, tmpZ - 1, cocoa4);
    }
}
