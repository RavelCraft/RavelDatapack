package com.connexal.raveldatapack;

import com.connexal.raveldatapack.blocks.*;
import com.connexal.raveldatapack.dimensions.aether.AetherDimension;
import com.connexal.raveldatapack.dimensions.love.LoveDimension;
import com.connexal.raveldatapack.enchantments.*;
import com.connexal.raveldatapack.entities.*;
import com.connexal.raveldatapack.items.food.*;
import com.connexal.raveldatapack.items.misc.*;
import com.connexal.raveldatapack.items.enderite.*;
import com.connexal.raveldatapack.items.hats.*;
import com.connexal.raveldatapack.items.plate.*;
import com.connexal.raveldatapack.items.warhammer.*;
import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.types.blocks.CustomBlock;
import com.github.imdabigboss.easydatapack.api.types.dimentions.CustomDimension;
import com.github.imdabigboss.easydatapack.api.types.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.types.entities.CustomEntity;
import com.github.imdabigboss.easydatapack.api.exceptions.*;
import com.github.imdabigboss.easydatapack.api.types.items.CustomBlockPlacerItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
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
            registerEntities(customRegistryAdder);
        } catch (EasyDatapackException e) {
            RavelDatapack.getLog().severe("Something went wrong while adding custom content: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registerBlocks(CustomRegistryAdder event) throws EasyDatapackException {
        TestBlock.register(event, "test_block");
    }

    private static void registerItems(CustomRegistryAdder adder) throws EasyDatapackException {
        BolterItem.register(adder, "bolter");
        BoltItem.register(adder, "bolt");
        BoltPistolItem.register(adder, "bolt_pistol");
        ChopperItem.register(adder, "chopper");
        PowerSwordItem.register(adder, "power_sword");
        StormbreakerItem.register(adder, "stormbreaker");
        ThunderHammerItem.register(adder, "thunder_hammer");
        FireballItem.register(adder, "fireball"); //RESERVED CMD
        PikeItem.register(adder, "pike");
        SuperHammerItem.register(adder, "super_hammer");
        SpeedBoostItem.register(adder, "speed_boost"); //RESERVED CMD
        ScytheItem.register(adder, "scythe");
        GrapplingHookItem.register(adder, "grappling_hook"); //RESERVED CMD

        PlateItem.register(adder, "plate"); //TODO: 3D Model
        TurkeyOnAPlateItem.register(adder, "turkey_on_a_plate"); //TODO: 3D Model

        EnderiteIngotItem.register(adder, "enderite_ingot");
        EnderiteAxeItem.register(adder, "enderite_axe");
        EnderiteHoeItem.register(adder, "enderite_hoe");
        EnderitePickaxeItem.register(adder, "enderite_pickaxe");
        EnderiteShovelItem.register(adder, "enderite_shovel");
        EnderiteSwordItem.register(adder, "enderite_sword");

        WoodenWarHammer.register(adder, "wooden_warhammer");
        StoneWarHammer.register(adder, "stone_warhammer");
        IronWarHammer.register(adder, "iron_warhammer");
        GoldenWarHammer.register(adder, "golden_warhammer");
        DiamondWarHammer.register(adder, "diamond_warhammer");
        NetheriteWarHammer.register(adder, "netherite_warhammer");

        PartyHat.register(adder, "party_hat"); //TODO: 3D Model
        SantaHat.register(adder, "santa_hat"); //TODO: 3D Model
        TopHat.register(adder, "top_hat"); //TODO: 3D Model
        ElfEars.register(adder, "elf_ears"); //TODO: 3D Model
        Antlers.register(adder, "antlers"); //TODO: 3D Model
        BunnyEars.register(adder, "bunny_ears"); //TODO: 3D Model
        EmeraldMask.register(adder, "emerald_mask"); //TODO: 3D Model

        GrilledBrownMushroomItem.register(adder, "grilled_brown_mushroom");
        GrilledRedMushroomItem.register(adder, "grilled_red_mushroom");
        FruitSaladItem.register(adder, "fruit_salad");
        BeefTacoItem.register(adder, "beef_taco");
        BeefPieItem.register(adder, "beef_pie");
        ChickenTacoItem.register(adder, "chicken_taco");
        ChickenPieItem.register(adder, "chicken_pie");
        FishTacoItem.register(adder, "fish_taco");
        FishPieItem.register(adder, "fish_pie");
        PorkTacoItem.register(adder, "pork_taco");
        PorkPieItem.register(adder, "pork_pie");
        ChocolateBarItem.register(adder, "chocolate_bar");
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
        adder.register(new LoveDimension());
    }

    private static void registerEntities(CustomRegistryAdder adder) throws CustomEntityException {
        TestEntity.register(adder, 271847);
        BlockyEntity.register(adder, 156385);
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

        public void register(CustomBlockPlacerItem item, CustomBlock block) throws CustomItemException {
            if (this.cantRegister("block." + block.getNamespaceKey())) {
                return;
            }

            this.adder.register(item);
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

        public void register(CustomEntity entity) throws CustomEntityException {
            if (this.cantRegister("entity." + entity.getNamespaceKey())) {
                return;
            }

            this.adder.register(entity);
        }
    }
}
