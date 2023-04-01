package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Random;

public class StormbreakerItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(customModelData, "stormbreaker", ChatColor.RED.toString() + ChatColor.BOLD + "Stormbreaker", Material.NETHERITE_AXE, 60, 1)
                .itemUseEvent(StormbreakerItem::itemUseEvent)
                .lore("Thor's weapon", "- Summon lightning", "- Very strong")
                .enchantment(Enchantment.DURABILITY, 1)
                .unbreakable(true)
                .hideFlags(true)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape(" NN", " BN", "B  ");
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('B', Material.BLAZE_ROD);

        adder.register(item, recipe);
    }

    private static void itemUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Random random = new Random();
        Location target = player.getTargetBlock(null, 50).getLocation();
        for (int i = 0; i < 5; i++) {
            Location strikeLocation = target.clone();
            strikeLocation.setX(strikeLocation.getX() + (random.nextDouble() * 1.5) - 1.5 / 2);
            strikeLocation.setY(strikeLocation.getY() + (random.nextDouble() * 1.5) - 1.5 / 2);

            player.getWorld().strikeLightning(strikeLocation);
        }

        player.swingMainHand();
    }
}
