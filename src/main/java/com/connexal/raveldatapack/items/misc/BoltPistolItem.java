package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.items.CustomToolItem;
import com.github.imdabigboss.easydatapack.api.utils.AmmunitionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

public class BoltPistolItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomToolItem.Builder(customModelData, "boltpistol", ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt Pistol", Material.CLOCK, 5, 1)
                .itemUseEvent(BoltPistolItem::itemUseEvent)
                .lore("Shoots arrows with no", "recharge time!")
                .hideFlags(true)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.getItemStack());
        recipe.shape(" IB", "III", " I ");
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('I', Material.IRON_INGOT);

        adder.register(item, recipe);
    }

    private static void itemUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!AmmunitionUtil.usePlayerAmo(player, Material.ARROW)) {
            return;
        }

        for (Player tmp : RavelDatapack.getInstance().getServer().getOnlinePlayers()) {
            tmp.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
        }

        Location eye = player.getEyeLocation();
        Location loc = eye.add(eye.getDirection().multiply(1.2));
        Arrow arrow = (Arrow) loc.getWorld().spawnEntity(loc, EntityType.ARROW);
        arrow.setVelocity(loc.getDirection().normalize().multiply(2));
        arrow.setShooter(player);
        arrow.setGravity(true);
    }
}
