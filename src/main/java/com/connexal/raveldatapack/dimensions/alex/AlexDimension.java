package com.connexal.raveldatapack.dimensions.alex;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.dimensions.CustomDimension;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;

public class AlexDimension extends CustomDimension {
    private final AlexBiomeProvider biomeProvider = new AlexBiomeProvider();
    private final AlexChunkGenerator chunkGenerator = new AlexChunkGenerator(biomeProvider);

    @Override
    public Material getPortalFrameMaterial() {
        return Material.ANDESITE;
    }

    @Override
    public String getName() {
        return "alex";
    }

    @Override
    public World.Environment getEnvironment() {
        return World.Environment.NORMAL;
    }

    @Override
    public Location dimensionToNormal(Location location) {
        World world = RavelDatapack.getInstance().getServer().getWorld("world");
        if (world == null) {
            return null;
        } else {
            return world.getSpawnLocation();
        }
    }

    @Override
    public Location normalToDimension(Location location) {
        World world = RavelDatapack.getInstance().getServer().getWorld(this.getName());
        if (world == null) {
            return null;
        } else {
            return world.getSpawnLocation();
        }
    }

    @Override
    protected BiomeProvider getBiomeProvider() {
        return this.biomeProvider;
    }

    @Override
    protected ChunkGenerator getChunkGenerator() {
        return this.chunkGenerator;
    }
}
