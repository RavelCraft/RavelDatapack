package com.connexal.raveldatapack.api.managers;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
    private final List<NamespacedKey> items = new ArrayList<>();

    public void registerRecipe(Recipe recipe) {
        if (recipe instanceof Keyed keyed) {
            if (RavelDatapackAPI.getServer().addRecipe(recipe)) {
                this.items.add(keyed.getKey());
            } else {
                RavelDatapackAPI.getLogger().severe("Failed to register recipe: " + keyed.getKey());
            }
        } else {
            throw new IllegalArgumentException("Recipe must be keyed");
        }
    }

    public void unregisterRecipe(NamespacedKey key) {
        if (this.items.remove(key)) {
            RavelDatapackAPI.getServer().removeRecipe(key);
        } else {
            RavelDatapackAPI.getLogger().severe("Failed to unregister recipe: " + key);
        }
    }

    public void unregisterAllRecipes() {
        for (NamespacedKey key : new ArrayList<>(this.items)) {
            this.unregisterRecipe(key);
        }
    }

    public List<NamespacedKey> getItems() {
        return this.items;
    }
}
