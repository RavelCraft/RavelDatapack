package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.utils.TexturePath;
import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import com.github.imdabigboss.easydatapack.api.types.items.CustomToolItem;
import com.github.imdabigboss.easydatapack.api.utils.AmmunitionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

public class BolterItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomToolItem.builder(namespaceKey, ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolter", Material.CLOCK, TexturePath.item(namespaceKey), 20, 1)
                .itemUseEvent(BolterItem::itemUseEvent)
                .hideFlags(true)
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape(" NB", "NNN", "IN ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('I', Material.IRON_INGOT);

        adder.register(item, recipe);
    }

    private static void itemUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!AmmunitionUtil.usePlayerAmo(player, EasyDatapackAPI.getItemManager().getCustomItem("bolt").getCustomModelData())) {
            return;
        }

        for (Player tmp : RavelDatapack.getInstance().getServer().getOnlinePlayers()) {
            tmp.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
        }

        Location eye = player.getEyeLocation();
        Location loc = eye.add(eye.getDirection().multiply(1.2));
        Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);
        fireball.setVelocity(loc.getDirection().normalize().multiply(2));
        fireball.setShooter(player);
        fireball.setYield(20f);
    }
}
