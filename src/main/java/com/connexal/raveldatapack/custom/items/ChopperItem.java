package com.connexal.raveldatapack.custom.items;

import com.connexal.raveldatapack.RavelDatapack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ChopperItem extends CustomItem implements Listener {
    public ChopperItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "chopper";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.CLOCK, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Slice up your enemies");

        meta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Chopper");
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 8, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier("generic.movementSpeed", 1.3, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape("NNN", "III", "III");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('I', Material.IRON_INGOT);
        this.instance.getServer().addRecipe(recipe);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
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
