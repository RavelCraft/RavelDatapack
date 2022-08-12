package com.connexal.raveldatapack.dimensions;

import com.connexal.raveldatapack.RavelDatapack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;

public abstract class CustomDimension {
    public abstract Material getPortalFrameMaterial();

    public abstract String getName();

    public abstract World.Environment getEnvironment();

    public abstract Location dimensionToNormal(Location location);

    public abstract Location normalToDimension(Location location);

    protected abstract BiomeProvider getBiomeProvider();

    protected abstract ChunkGenerator getChunkGenerator();

    public World createWorld() {
        WorldCreator worldCreator = new WorldCreator(this.getName());
        worldCreator.biomeProvider(this.getBiomeProvider());
        worldCreator.generator(this.getChunkGenerator());
        worldCreator.environment(this.getEnvironment());
        worldCreator.generateStructures(false);
        worldCreator.hardcore(false);
        worldCreator.seed(RavelDatapack.getSeed());

        return worldCreator.createWorld();
    }
}
