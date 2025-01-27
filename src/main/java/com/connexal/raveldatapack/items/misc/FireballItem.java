package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.types.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;

public class FireballItem {
    public static void register(CustomRegistry.CustomRegistryAdder adder, String namespaceKey) throws EasyDatapackException {
        CustomItem item = CustomItem.builder(EasyDatapackAPI.getTexturePackManager().reserveItemCMD(Material.FIRE_CHARGE), namespaceKey, ChatColor.WHITE + "Fireball", Material.FIRE_CHARGE)
                .itemUseEvent(FireballItem::itemUseEvent)
                .lore("Right click to shoot")
                .build();

        ShapedRecipe recipe = new ShapedRecipe(item.getNamespacedKey(), item.createItemStack());
        recipe.shape("FF", "FF");
        recipe.setIngredient('F', Material.FIRE_CHARGE);

        adder.register(item, recipe);
    }

    private static void itemUseEvent(PlayerInteractEvent event) {
        event.getItem().setAmount(event.getItem().getAmount() - 1);

        Player player = event.getPlayer();
        for (Player tmp : RavelDatapack.getInstance().getServer().getOnlinePlayers()) {
            tmp.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, 1);
        }

        Location eye = player.getEyeLocation();
        Location loc = eye.add(eye.getDirection().multiply(1.2));
        Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc, EntityType.FIREBALL);
        fireball.setVelocity(loc.getDirection().normalize().multiply(2));
        fireball.setShooter(player);

        player.swingMainHand();
    }
}
