/**
 * Code for this item was taken from the following GitHub repository: https://github.com/snowgears/Grappling-Hook
 * Copyright (c) 2013-2023 snowgears
 */

package com.connexal.raveldatapack.items.misc;

import com.connexal.raveldatapack.CustomRegistry;
import com.connexal.raveldatapack.RavelDatapack;
import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class GrapplingHookItem implements Listener {
    private final HashMap<UUID, FishHook> activeHookEntities = new HashMap<>();

    public GrapplingHookItem(CustomItem customItem) {
    }

    public static void register(CustomRegistry.CustomRegistryAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomItem.Builder(customModelData, "grappling_hook", ChatColor.GOLD.toString() + ChatColor.BOLD + "Grappling Hook", Material.FISHING_ROD)
                .eventListener(GrapplingHookItem.class)
                .forbiddenEnchantment(Enchantment.LURE, Enchantment.LUCK)
                .lore("Right click to attach the", "hook to a block and click", "again to pull yourself", "towards it!")
                .build();

        ShapelessRecipe recipe = new ShapelessRecipe(item.getNamespacedKey(), item.getItemStack())
                .addIngredient(Material.DIAMOND)
                .addIngredient(Material.SLIME_BALL)
                .addIngredient(Material.FISHING_ROD);

        adder.register(item, recipe);
    }

    private void onGrapple(Player player, ItemStack hookItem, Entity entity, Location location) {
        if (this.activeHookEntities.remove(player.getUniqueId()) == null) {
            return;
        }

        if (player.equals(entity)) { //The player is pulling themselves
            if (player.getLocation().distance(location) < 6) { //Too close to the hook
                this.pullPlayerSlightly(player, location);
            } else {
                this.pullEntityToLocation(entity, location);
            }
        } else {
            this.pullEntityToLocation(entity, location);
        }

        this.playGrappleSound(location);
        this.addUseToHook(player, hookItem);
    }

    private void playGrappleSound(Location location) {
        location.getWorld().playSound(location, Sound.ENTITY_MAGMA_CUBE_JUMP, 10f, 1f);
    }

    private void addUseToHook(Player player, ItemStack hookItem) {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ItemMeta meta = hookItem.getItemMeta();
        if (!(meta instanceof Damageable damageable)) {
            return;
        }

        damageable.setDamage(damageable.getDamage() - 1);
    }

    private void pullPlayerSlightly(Player player, Location location) {
        if (location.getY() > player.getLocation().getY()) {
            player.setVelocity(new Vector(0,0.25,0));
            return;
        }

        player.setVelocity(location.toVector().subtract(player.getLocation().toVector()));
    }

    private void pullEntityToLocation(Entity entity, Location location) {
        Location entityLocation = entity.getLocation();

        Vector boost = entity.getVelocity();
        boost.setY(0.3f);
        entity.setVelocity(boost);

        RavelDatapack.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(RavelDatapack.getInstance(), () -> {
            double g = -0.08;
            double d = location.distance(entityLocation);
            double v_x = (1.0 + 0.07 * d) * (location.getX() - entityLocation.getX()) / d;
            double v_y = (1.0 + 0.03 * d) * (location.getY() - entityLocation.getY()) / d - 0.5 * g * d;
            double v_z = (1.0 + 0.07 * d) * (location.getZ() - entityLocation.getZ()) / d;

            Vector v = entity.getVelocity();
            v.setX(v_x);
            v.setY(v_y);
            v.setZ(v_z);
            entity.setVelocity(v);
        }, 1L);
    }

    @EventHandler
    public void onHookHitBlock(ProjectileHitEvent event) {
        if (event.getEntity() instanceof FishHook && event.getEntity().getShooter() instanceof Player) {
            if (this.activeHookEntities.containsKey(((Player) event.getEntity().getShooter()).getUniqueId())) {
                FishHook fishHook = (FishHook) event.getEntity();
                Player player = (Player) fishHook.getShooter();

                if (event.getHitBlock() != null && !event.getHitBlock().getLocation().getBlock().isEmpty()) {
                    Location hitblock = event.getHitBlock().getLocation().add(0.5, 0, 0.5);

                    ArmorStand armorStand = player.getWorld().spawn(hitblock, ArmorStand.class);
                    armorStand.addPassenger(fishHook);
                    armorStand.setGravity(false);
                    armorStand.setVisible(false);
                    armorStand.setSmall(true);
                    armorStand.setArms(false);
                    armorStand.setMarker(true);
                    armorStand.setBasePlate(false);
                    fishHook.setGravity(false);
                    fishHook.setMetadata("stuckBlock", new FixedMetadataValue(RavelDatapack.getInstance(), ""));
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onFishEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null || !item.getItemMeta().hasCustomModelData()) {
            return;
        }

        CustomItem customItem = EasyDatapackAPI.getItemManager().getCustomItem(item.getItemMeta().getCustomModelData());
        if (customItem == null || !customItem.getNamespacedKey().getKey().equals("grappling_hook")) {
            return;
        }

        boolean activeStickyHook = event.getHook().hasMetadata("stuckBlock");

        if (event.getState() == PlayerFishEvent.State.IN_GROUND || event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT || activeStickyHook) {
            Location loc = event.getHook().getLocation();

            if (activeStickyHook) {
                event.getHook().removeMetadata("stuckBlock", RavelDatapack.getInstance());
                event.getHook().getVehicle().remove();
            }

            for (Entity entity : event.getHook().getNearbyEntities(1.5f, 1f, 1.5f)){
                if (entity instanceof Item){
                    this.onGrapple(player, item, entity, player.getLocation());
                    return;
                }
            }

            this.onGrapple(player, item, player, loc);
        } else if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            event.setCancelled(true);
            event.getHook().remove();
            this.onGrapple(player, item, event.getCaught(), player.getLocation());
        } else if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            event.setCancelled(true);
            event.getHook().remove();
        } else if (event.getState() == PlayerFishEvent.State.FISHING) {
            this.activeHookEntities.put(player.getUniqueId(), event.getHook());

            //Add more velocity to the hook???
        } else if (event.getState() == PlayerFishEvent.State.REEL_IN) {
            this.onGrapple(player, item, player, event.getHook().getLocation());
        }
    }
}
