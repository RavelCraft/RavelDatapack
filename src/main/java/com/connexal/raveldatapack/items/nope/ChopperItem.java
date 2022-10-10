package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ChopperItem extends CustomItem implements Listener {
    public ChopperItem(int customModelData) {
        super(customModelData, "chopper");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Slice up your enemies");
        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Chopper"));
        this.setAttackDamage(meta, 8, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("generic.movementSpeed", 1.3, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("NNN", "III", "III");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('I', Material.IRON_INGOT);
        this.instance.getServer().addRecipe(recipe);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item.getItemMeta() == null) {
                return;
            }
            if (!item.getItemMeta().hasCustomModelData()) {
                return;
            }

            if (item.getItemMeta().getCustomModelData() == this.getCustomModelData()) {
                for (Player tmp : RavelDatapack.getInstance().getServer().getOnlinePlayers()) {
                    tmp.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
                }
            }
        }
    }
}
