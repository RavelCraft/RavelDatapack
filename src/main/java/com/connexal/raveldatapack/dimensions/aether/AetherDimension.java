package com.connexal.raveldatapack.dimensions.aether;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.dimensions.CustomDimension;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;

public class AetherDimension extends CustomDimension {
    private final AetherBiomeProvider biomeProvider;
    private final AetherChunkGenerator chunkGenerator;

    public AetherDimension() {
        this.biomeProvider = new AetherBiomeProvider();
        this.chunkGenerator = new AetherChunkGenerator(this.biomeProvider);
    }

    @Override
    public Material getPortalFrameMaterial() {
        return Material.GLOWSTONE;
    }

    @Override
    public String getName() {
        return "aether";
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
