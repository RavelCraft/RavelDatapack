package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import com.connexal.raveldatapack.utils.AmoUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BolterItem extends CustomItem implements Listener {
    public BolterItem(int customModelData) {
        super(customModelData, "bolter");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Shoots fireballs");
        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolter"));
        this.setAttackDamage(meta, 20, EquipmentSlot.HAND);
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape(" NB", "NNN", "IN ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('I', Material.IRON_INGOT);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);

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
                Player player = event.getPlayer();
                if (!AmoUtil.usePlayerAmo(player, RavelDatapack.getItemManager().getCustomModelData("bolt"))) {
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
    }
}
