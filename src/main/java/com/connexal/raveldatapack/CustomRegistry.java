package com.connexal.raveldatapack;

import com.connexal.raveldatapack.dimensions.aether.AetherDimension;
import com.connexal.raveldatapack.enchantments.BlazingArmorEnchantment;
import com.connexal.raveldatapack.enchantments.PoisonBladeEnchantment;
import com.connexal.raveldatapack.enchantments.TelekinesisEnchantment;
import com.connexal.raveldatapack.items.misc.*;
import com.connexal.raveldatapack.items.enderite.*;
import com.connexal.raveldatapack.items.hats.*;
import com.connexal.raveldatapack.items.plate.PlateItem;
import com.connexal.raveldatapack.items.plate.TurkeyOnAPlateItem;
import com.connexal.raveldatapack.items.warhammer.*;
import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.blocks.CustomBlock;
import com.github.imdabigboss.easydatapack.api.dimentions.CustomDimension;
import com.github.imdabigboss.easydatapack.api.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomDimensionException;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEnchantmentException;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomRecipeException;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.utils.YmlConfig;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class CustomRegistry {
    public static void register(CustomAdder adder) {
        CustomRegistryAdder customRegistryAdder = new CustomRegistryAdder(adder, RavelDatapack.getConfig("config"));

        try {
            registerBlocks(customRegistryAdder);
            registerItems(customRegistryAdder);
            registerRecipes(customRegistryAdder);
            registerEnchantments(customRegistryAdder);
            registerDimensions(customRegistryAdder);
        } catch (EasyDatapackException e) {
            RavelDatapack.getLog().severe("Something went wrong while adding custom content: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registerBlocks(CustomRegistryAdder event) {
        event.register(new CustomBlock.Builder("Test Block", "test_block", 68295, false, false, false, false, false, true, CustomBlock.Parent.MUSHROOM_STEM)
                .build());
    }

    private static void registerItems(CustomRegistryAdder adder) throws EasyDatapackException {
        BolterItem.register(adder, 295304);
        BoltItem.register(adder, 450256);
        BoltPistolItem.register(adder, 485103);
        ChopperItem.register(adder, 695205);
        PowerSwordItem.register(adder, 806528);
        StormbreakerItem.register(adder, 952356);
        ThunderHammerItem.register(adder, 426754);
        FireballItem.register(adder, 367026);
        PikeItem.register(adder, 209846);
        SuperHammerItem.register(adder, 79786);
        SpeedBoostItem.register(adder, 202483);
        ScytheItem.register(adder, 843269);
        GrapplingHookItem.register(adder, 456329);

        PlateItem.register(adder, 256289);
        TurkeyOnAPlateItem.register(adder, 259822);

        EnderiteIngotItem.register(adder, 350687);
        EnderiteAxeItem.register(adder, 246060);
        EnderiteHoeItem.register(adder, 246061);
        EnderitePickaxeItem.register(adder, 246062);
        EnderiteShovelItem.register(adder, 246063);
        EnderiteSwordItem.register(adder, 246064);

        WoodenWarHammer.register(adder, 738351);
        StoneWarHammer.register(adder, 738352);
        IronWarHammer.register(adder, 738353);
        GoldenWarHammer.register(adder, 738354);
        DiamondWarHammer.register(adder, 738355);
        NetheriteWarHammer.register(adder, 738356);

        PartyHat.register(adder, 175207);
        SantaHat.register(adder, 528514);
        TopHat.register(adder, 267405);
        ElfEars.register(adder, 845619);
        Antlers.register(adder, 798452);
        BunnyEars.register(adder, 628506);
        EmeraldMask.register(adder, 724045);
    }

    private static void registerRecipes(CustomRegistryAdder adder) throws CustomRecipeException {
        ShapelessRecipe woolToString = new ShapelessRecipe(NamespacedKey.minecraft("wool_to_string"), new ItemStack(Material.STRING, 4));
        RecipeChoice allWool = new RecipeChoice.MaterialChoice(Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL);
        woolToString.addIngredient(allWool);
        adder.register(woolToString);
    }

    private static void registerEnchantments(CustomRegistryAdder adder) throws CustomEnchantmentException {
        TelekinesisEnchantment.register(adder);
        PoisonBladeEnchantment.register(adder);
        BlazingArmorEnchantment.register(adder);
    }

    private static void registerDimensions(CustomRegistryAdder adder) throws CustomDimensionException {
        adder.register(new AetherDimension());
    }

    public static class CustomRegistryAdder {
        private final CustomAdder adder;
        private final YmlConfig config;

        public CustomRegistryAdder(CustomAdder adder, YmlConfig config) {
            this.adder = adder;
            this.config = config;
        }

        private boolean cantRegister(String configPath) {
            if (this.config.getConfig().contains(configPath)) {
                return !this.config.getConfig().getBoolean(configPath);
            } else {
                this.config.getConfig().set(configPath, false);
                this.config.saveConfig();
                return true;
            }
        }

        public void register(CustomBlock block) {
            if (this.cantRegister("block." + block.getNamespaceKey())) {
                return;
            }

            this.adder.register(block);
        }

        public void register(CustomDimension dimension) throws CustomDimensionException {
            if (this.cantRegister("dimension." + dimension.getName())) {
                return;
            }

            this.adder.register(dimension);
        }

        public void register(CustomEnchantment enchantment) throws CustomEnchantmentException {
            if (this.cantRegister("enchantment." + enchantment.getNamespace())) {
                return;
            }

            this.adder.register(enchantment);
        }

        public void register(CustomItem item) throws EasyDatapackException {
            if (this.cantRegister("item." + item.getNamespaceKey())) {
                return;
            }

            this.adder.register(item);
        }

        public void register(CustomItem item, Recipe recipe) throws EasyDatapackException {
            if (this.cantRegister("item." + item.getNamespaceKey())) {
                return;
            }

            this.adder.register(item);
            this.adder.register(recipe);
        }

        public void register(Recipe recipe) throws CustomRecipeException {
            if (this.cantRegister("recipe." + ((Keyed) recipe).getKey().getKey())) {
                return;
            }

            this.adder.register(recipe);
        }
    }
}
