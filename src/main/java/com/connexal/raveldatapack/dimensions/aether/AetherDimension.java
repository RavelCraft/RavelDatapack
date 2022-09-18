package com.connexal.raveldatapack.dimensions.aether;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.aether.biomes.AetherBiome;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class AetherDimension extends CustomDimension {
    private final AetherBiomeProvider biomeProvider;
    private final AetherChunkGenerator chunkGenerator;

    private final Random mobRandom = new Random();

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

    @Override
    public String getBiomeName(Biome biome) {
        return AetherBiome.getBiomeName(biome);
    }

    @Override
    public boolean spawnEntity(Location location, EntityType original, Entity spawned) {
        if ((spawned instanceof Monster) && this.mobRandom.nextInt(4) != 0) {
            return true;
        }

        return AetherBiome.spawnEntity(location, original, spawned, location.getBlock().getBiome());
    }
}
