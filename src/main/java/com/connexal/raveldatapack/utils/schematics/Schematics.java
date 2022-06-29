package com.connexal.raveldatapack.utils.schematics;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.utils.nbt.*;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schematics {
    public static Schematic loadSchematic(String schematicName) {
        InputStream stream = RavelDatapack.getInstance().getResource("schematics/" + schematicName + ".ravelschem");
        if (stream == null) {
            RavelDatapack.getLog().warning("Schematic not found");
            return null;
        }

        CompoundTag schematicTag;
        try {
            NBTInputStream nbtStream = new NBTInputStream(stream);
            schematicTag = (CompoundTag) nbtStream.readTag();
            if (!schematicTag.getName().equals("Schematic")) {
                RavelDatapack.getLog().warning("Tag \"Schematic\" does not exist or is not first");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Map<String, Tag> schematic = schematicTag.getValue();

        int width = getChildTag(schematic, "Width", IntTag.class).getValue();
        int height = getChildTag(schematic, "Height", IntTag.class).getValue();
        int depth = getChildTag(schematic, "Depth", IntTag.class).getValue();

        List<Tag> blockData = getChildTag(schematic, "Blocks", ListTag.class).getValue();
        BlockData[] blocks = new BlockData[blockData.size()];

        for (int i = 0; i < blockData.size(); i++) {
            String data = ((StringTag) blockData.get(i)).getValue();
            blocks[i] = RavelDatapack.getInstance().getServer().createBlockData(data);
        }

        return new Schematic(blocks, width, height, depth);
    }

    public static boolean createSchematic(Location start, Location end, File file) {
        if (!start.getWorld().getName().equals(end.getWorld().getName())) {
            return false;
        }

        int startX = start.getBlockX();
        int startY = start.getBlockY();
        int startZ = start.getBlockZ();

        int endX = end.getBlockX();
        int endY = end.getBlockY();
        int endZ = end.getBlockZ();

        int width = (Math.max(startX, endX) - Math.min(startX, endX)) + 1;
        int height = (Math.max(startY, endY) - Math.min(startY, endY)) + 1;
        int depth = (Math.max(startZ, endZ) - Math.min(startZ, endZ)) + 1;

        boolean modifyX = startX > endX;
        boolean modifyY = startY > endY;
        boolean modifyZ = startZ > endZ;

        List<Tag> blockDataList = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            int tmpX;
            if (modifyX) {
                tmpX = endX + x;
            } else {
                tmpX = startX + x;
            }

            for (int y = 0; y < height; y++) {
                int tmpY;
                if (modifyY) {
                    tmpY = endY + y;
                } else {
                    tmpY = startY + y;
                }

                for (int z = 0; z < depth; z++) {
                    int tmpZ;
                    if (modifyZ) {
                        tmpZ = endZ + z;
                    } else {
                        tmpZ = startZ + z;
                    }

                    BlockData tmp = start.getWorld().getBlockAt(tmpX, tmpY, tmpZ).getBlockData();
                    blockDataList.add(new StringTag("Blocks", tmp.getAsString()));
                }
            }
        }

        Map<String, Tag> schematicMap = new HashMap<>();
        schematicMap.put("Width", new IntTag("Width", width));
        schematicMap.put("Height", new IntTag("Height", height));
        schematicMap.put("Depth",  new IntTag("Depth", depth));
        schematicMap.put("Blocks", new ListTag("Blocks", StringTag.class, blockDataList));

        CompoundTag schematic = new CompoundTag("Schematic", schematicMap);

        try {
            NBTOutputStream nbtStream = new NBTOutputStream(new FileOutputStream(file));

            nbtStream.writeTag(schematic);
            nbtStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws IllegalArgumentException {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }

        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}
