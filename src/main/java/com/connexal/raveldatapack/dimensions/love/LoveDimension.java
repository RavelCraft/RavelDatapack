package com.connexal.raveldatapack.dimensions.love;

import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomChunkGenerator;
import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomDimension;

import net.kyori.adventure.text.Component;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BiomeProvider;
import org.checkerframework.checker.nullness.qual.NonNull;

public class LoveDimension extends CustomDimension {
    private final LoveBiomeProvider biomeProvider;
    private final LoveChunkGenerator chunkGenerator;

    public LoveDimension() {
        this.biomeProvider = new LoveBiomeProvider();
        this.chunkGenerator = new LoveChunkGenerator(this.biomeProvider);
    }

    @Override
    public @NonNull Material getPortalFrameMaterial() {
        return Material.PINK_WOOL;
    }

    @Override
    public @NonNull String getName() {
        return "love";
    }

    @Override
    public World.@NonNull Environment getEnvironment() {
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
    protected @NonNull BiomeProvider getBiomeProvider() {
        return this.biomeProvider;
    }

    @Override
    protected @NonNull CustomChunkGenerator getChunkGenerator() {
        return this.chunkGenerator;
    }

    @Override
    public @NonNull String getBiomeName(Biome biome) {
        return "Spread The Love";
    }

    @Override
    public boolean spawnEntity(@NonNull Location location, @NonNull EntityType entityType, @NonNull Entity entity) {
        Entity creeper = location.getWorld().spawnEntity(location, EntityType.CREEPER);
        creeper.customName(Component.text("I love you!"));

        return true;
    }
}
