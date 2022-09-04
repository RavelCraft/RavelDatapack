package com.connexal.raveldatapack.blocks;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomBlock {
    private final String name;
    private final String namespaceKey;
    private final int customModelData;
    private final boolean up;
    private final boolean down;
    private final boolean north;
    private final boolean south;
    private final boolean east;
    private final boolean west;
    private final Parent parent;

    protected Material dropMaterial = null;
    protected int dropAmount = 1;
    protected int dropExperience = 0;

    public CustomBlock(String name, int customModelData, boolean up, boolean down, boolean north, boolean east, boolean south, boolean west, Parent parent) {
        this.name = name;
        this.namespaceKey = name.toLowerCase().replace(" ", "_");
        this.customModelData = customModelData;
        this.up = up;
        this.down = down;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespaceKey() {
        return this.namespaceKey;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }

    public boolean isUp() {
        return this.up;
    }

    public boolean isDown() {
        return this.down;
    }

    public boolean isNorth() {
        return this.north;
    }

    public boolean isSouth() {
        return this.south;
    }

    public boolean isEast() {
        return this.east;
    }

    public boolean isWest() {
        return this.west;
    }

    public int getDropExperience() {
        return this.dropExperience;
    }

    public enum Parent {
        MUSHROOM_STEM,
        BROWN_MUSHROOM,
        RED_MUSHROOM
    }

    public ItemStack createBlockItem() {
        ItemStack item = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(this.customModelData);
        meta.displayName(Component.text(ChatColor.RESET.toString() + ChatColor.WHITE + this.name));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createDrop() {
        if (this.dropMaterial == null) {
            ItemStack item = this.createBlockItem();
            item.setAmount(this.dropAmount);
            return item;
        } else {
            return new ItemStack(this.dropMaterial, this.dropAmount);
        }
    }

    public Material getMaterial() {
        return switch (this.parent) {
            case MUSHROOM_STEM -> Material.MUSHROOM_STEM;
            case BROWN_MUSHROOM -> Material.BROWN_MUSHROOM;
            case RED_MUSHROOM -> Material.RED_MUSHROOM;
        };
    }
}
