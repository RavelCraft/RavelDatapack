package com.connexal.raveldatapack;

import com.connexal.raveldatapack.dimensions.aether.AetherDimension;
import com.connexal.raveldatapack.enchantments.BlazingArmorEnchantment;
import com.connexal.raveldatapack.enchantments.PoisonBladeEnchantment;
import com.connexal.raveldatapack.enchantments.TelekinesisEnchantment;
import com.connexal.raveldatapack.items.FireballItem;
import com.connexal.raveldatapack.items.SpeedBoostItem;
import com.connexal.raveldatapack.items.SuperHammerItem;
import com.connexal.raveldatapack.items.enderite.*;
import com.connexal.raveldatapack.items.hats.*;
import com.connexal.raveldatapack.items.nope.*;
import com.connexal.raveldatapack.items.plate.PlateItem;
import com.connexal.raveldatapack.items.plate.TurkeyOnAPlateItem;
import com.connexal.raveldatapack.items.warhammer.*;
import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.blocks.CustomBlock;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomDimensionException;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEnchantmentException;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomRecipeException;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class CustomRegistry {
    public static void register(CustomAdder adder) {
        try {
            registerBlocks(adder);
            registerItems(adder);
            registerRecipes(adder);
            registerEnchantments(adder);
            registerDimensions(adder);
        } catch (EasyDatapackException e) {
            RavelDatapack.getLog().severe("Something went wrong while adding custom content: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registerBlocks(CustomAdder event) {
        event.register(new CustomBlock.Builder("Test Block", "test_block", 68295, false, false, false, false, false, true, CustomBlock.Parent.MUSHROOM_STEM)
                .build());
    }

    private static void registerItems(CustomAdder adder) throws EasyDatapackException {
        BolterItem.register(adder, 295304);
        BoltItem.register(adder, 450256);
        BoltPistolItem.register(adder, 485103);
        ChopperItem.register(adder, 695205);
        PowerSwordItem.register(adder, 806528);
        StormbreakerItem.register(adder, 952356);
        ThunderHammerItem.register(adder, 426754);
        FireballItem.register(adder, 367026);
        PikeItem.register(adder, 209846);

        PlateItem.register(adder, 256289);
        TurkeyOnAPlateItem.register(adder, 259822);

        EnderiteIngotItem.register(adder, 350687);
        EnderiteAxeItem.register(adder, 246060);
        EnderiteHoeItem.register(adder, 246061);
        EnderitePickaxeItem.register(adder, 246062);
        EnderiteShovelItem.register(adder, 246063);
        EnderiteSwordItem.register(adder, 246064);

        SuperHammerItem.register(adder, 79786);

        SpeedBoostItem.register(adder, 202483);

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

    private static void registerRecipes(CustomAdder adder) throws CustomRecipeException {
        ShapelessRecipe woolToString = new ShapelessRecipe(NamespacedKey.minecraft("wool_to_string"), new ItemStack(Material.STRING, 4));
        RecipeChoice allWool = new RecipeChoice.MaterialChoice(Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL);
        woolToString.addIngredient(allWool);
        adder.register(woolToString);
    }

    private static void registerEnchantments(CustomAdder adder) throws CustomEnchantmentException {
        TelekinesisEnchantment.register(adder);
        PoisonBladeEnchantment.register(adder);
        BlazingArmorEnchantment.register(adder);
    }

    private static void registerDimensions(CustomAdder adder) throws CustomDimensionException {
        adder.register(new AetherDimension());
    }
}
