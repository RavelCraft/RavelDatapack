package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import com.connexal.raveldatapack.utils.AmoUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BoltPistolItem extends CustomItem implements Listener {
    public BoltPistolItem(int customModelData) {
        super(customModelData, "boltpistol");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Shoots arrows");
        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt Pistol"));
        this.setAttackDamage(meta, 5, EquipmentSlot.HAND);
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape(" IB", "III", " I ");
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('I', Material.IRON_INGOT);
        this.instance.getServer().addRecipe(recipe);

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
                if (!AmoUtil.usePlayerAmo(player, Material.ARROW)) {
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
    }
}
