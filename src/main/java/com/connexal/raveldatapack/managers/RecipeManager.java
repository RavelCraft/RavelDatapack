package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
    private final List<NamespacedKey> items = new ArrayList<>();
    private final Server server = RavelDatapack.getInstance().getServer();

    public void init() {
        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft("wool_to_string"), new ItemStack(Material.STRING, 4));
        RecipeChoice wool = new RecipeChoice.MaterialChoice(Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL);
        recipe.addIngredient(wool);
        this.registerRecipe(recipe);
    }

    public void registerRecipe(Recipe recipe) {
        if (recipe instanceof Keyed keyed) {
            if (this.server.addRecipe(recipe)) {
                this.items.add(keyed.getKey());
            } else {
                RavelDatapack.getInstance().getLogger().severe("Failed to register recipe: " + keyed.getKey());
            }
        } else {
            throw new IllegalArgumentException("Recipe must be keyed");
        }
    }

    public void unregisterRecipe(NamespacedKey key) {
        if (this.items.remove(key)) {
            this.server.removeRecipe(key);
        } else {
            RavelDatapack.getInstance().getLogger().severe("Failed to unregister recipe: " + key);
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
