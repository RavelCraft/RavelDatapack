package com.connexal.raveldatapack;

import com.connexal.raveldatapack.api.CustomAdder;
import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.blocks.CustomBlock;
import com.connexal.raveldatapack.api.exceptions.*;
import com.connexal.raveldatapack.api.managers.*;
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
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class CustomAdderImpl implements CustomAdder {
    @Override
    public void add() {
        try {
            this.registerBlocks();
            this.registerItems();
            this.registerRecipes();
            this.registerEnchantments();
            this.registerDimensions();
        } catch (RavelDatapackException e) {
            RavelDatapack.getLog().severe("Something went wrong while adding custom content: " + e.getMessage());
        }
    }

    private void registerBlocks() {
        BlockManager blockManager = RavelDatapackAPI.getBlockManager();

        blockManager.registerCustomBlock(new CustomBlock("Test Block", 68295, false, false, false, false, false, true, CustomBlock.Parent.MUSHROOM_STEM));
    }

    private void registerItems() throws CustomItemException {
        ItemManager itemManager = RavelDatapackAPI.getItemManager();

        itemManager.registerCustomItem(new BolterItem(295304));
        itemManager.registerCustomItem(new BoltItem(450256));
        itemManager.registerCustomItem(new BoltPistolItem(485103));
        itemManager.registerCustomItem(new ChopperItem(695205));
        itemManager.registerCustomItem(new PowerSwordItem(806528));
        itemManager.registerCustomItem(new StormbreakerItem(952356));
        itemManager.registerCustomItem(new ThunderHammerItem(426754));
        itemManager.registerCustomItem(new FireballItem(367026));
        itemManager.registerCustomItem(new PikeItem(209846));

        itemManager.registerCustomItem(new PlateItem(256289));
        itemManager.registerCustomItem(new TurkeyOnAPlateItem(259822));

        itemManager.registerCustomItem(new EnderiteIngotItem(350687));
        itemManager.registerCustomItem(new EnderiteAxeItem(246060));
        itemManager.registerCustomItem(new EnderiteHoeItem(246061));
        itemManager.registerCustomItem(new EnderitePickaxeItem(246062));
        itemManager.registerCustomItem(new EnderiteShovelItem(246063));
        itemManager.registerCustomItem(new EnderiteSwordItem(246064));

        itemManager.registerCustomItem(new SuperHammerItem(79786));

        itemManager.registerCustomItem(new SpeedBoostItem(202483));

        itemManager.registerCustomItem(new WoodenWarHammer(738351));
        itemManager.registerCustomItem(new StoneWarHammer(738352));
        itemManager.registerCustomItem(new IronWarHammer(738353));
        itemManager.registerCustomItem(new GoldenWarHammer(738354));
        itemManager.registerCustomItem(new DiamondWarHammer(738355));
        itemManager.registerCustomItem(new NetheriteWarHammer(738356));

        itemManager.registerCustomItem(new PartyHat(175207));
        itemManager.registerCustomItem(new SantaHat(528514));
        itemManager.registerCustomItem(new TopHat(267405));
        itemManager.registerCustomItem(new ElfEars(845619));
        itemManager.registerCustomItem(new Antlers(798452));
        itemManager.registerCustomItem(new BunnyEars(628506));
        itemManager.registerCustomItem(new EmeraldMask(724045));
    }

    private void registerRecipes() throws CustomRecipeException {
        RecipeManager recipeManager = RavelDatapackAPI.getRecipeManager();

        ShapelessRecipe woolToString = new ShapelessRecipe(NamespacedKey.minecraft("wool_to_string"), new ItemStack(Material.STRING, 4));
        RecipeChoice allWool = new RecipeChoice.MaterialChoice(Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL);
        woolToString.addIngredient(allWool);
        recipeManager.registerRecipe(woolToString);
    }

    private void registerEnchantments() throws CustomEnchantmentException {
        EnchantmentManager enchantmentManager = RavelDatapackAPI.getEnchantmentManager();

        enchantmentManager.registerCustomEnchantment(new TelekinesisEnchantment());
        enchantmentManager.registerCustomEnchantment(new PoisonBladeEnchantment());
        enchantmentManager.registerCustomEnchantment(new BlazingArmorEnchantment());
    }

    private void registerDimensions() throws CustomDimensionException {
        DimensionManager dimensionManager = RavelDatapackAPI.getDimensionManager();

        dimensionManager.registerDimension(new AetherDimension());
    }
}
