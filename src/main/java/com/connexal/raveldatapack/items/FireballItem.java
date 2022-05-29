package com.connexal.raveldatapack.items;

import com.connexal.raveldatapack.RavelDatapack;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class FireballItem extends CustomItem implements Listener {
    public FireballItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "fireball";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.FIRE_CHARGE, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Right click to shoot");

        meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.WHITE + "Fireball");
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("FF", "FF");
        recipe.setIngredient('F', Material.FIRE_CHARGE);
        instance.getServer().addRecipe(recipe);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void handleEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() == null) {
                return;
            }
            if (event.getItem().getItemMeta() == null) {
                return;
            }
            if (!event.getItem().getItemMeta().hasCustomModelData()) {
                return;
            }

            if (event.getItem().getItemMeta().getCustomModelData() == this.getCustomModelData()) {
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
            }
        }
    }
}
