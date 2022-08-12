package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.dimensions.aether.AetherDimension;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class DimensionManager {
    private final Map<String, CustomDimension> dimensions = new HashMap<>();

    public int init() {
        this.registerDimension(new AetherDimension());

        return dimensions.size();
    }

    public void registerDimension(CustomDimension dimension) {
        for (CustomDimension registeredDimension : dimensions.values()) {
            if (registeredDimension.getName().equals(dimension.getName())) {
                RavelDatapack.getLog().severe("A dimension with the name " + dimension.getName() + " has already been registered!");
                return;
            } else if (registeredDimension.getPortalFrameMaterial().equals(dimension.getPortalFrameMaterial())) {
                RavelDatapack.getLog().severe("A dimension with the portal frame material " + dimension.getPortalFrameMaterial() + " has already been registered!");
                return;
            }
        }

        if (RavelDatapack.getInstance().getConfig().contains("dimensions." + dimension.getName())) {
            if (!RavelDatapack.getInstance().getConfig().getBoolean("dimensions." + dimension.getName())) {
                return;
            }
        } else {
            RavelDatapack.getInstance().getConfig().set("dimensions." + dimension.getName(), false);
            RavelDatapack.getInstance().saveConfig();
            return;
        }

        dimensions.put(dimension.getName(), dimension);
    }

    public void createWorlds() {
        for (CustomDimension dimension : dimensions.values()) {
            dimension.createWorld();
        }
    }

    public CustomDimension getDimension(String name) {
        return dimensions.get(name);
    }

    public Map<String, CustomDimension> getDimensions() {
        return dimensions;
    }

    public CustomDimension getDimensionFromPortalMaterial(Material material) {
        for (CustomDimension dimension : dimensions.values()) {
            if (dimension.getPortalFrameMaterial().equals(material)) {
                return dimension;
            }
        }
        return null;
    }
}
