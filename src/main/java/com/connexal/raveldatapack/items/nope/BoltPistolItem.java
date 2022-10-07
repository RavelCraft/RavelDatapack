package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import com.connexal.raveldatapack.utils.AmoUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class BoltPistolItem extends CustomItem implements Listener {
    public BoltPistolItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "boltpistol";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Shoots arrows");

        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Bolt Pistol"));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 5, AttributeModifier.Operation.ADD_NUMBER));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape(" IB", "III", " I ");
        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('I', Material.IRON_INGOT);
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
