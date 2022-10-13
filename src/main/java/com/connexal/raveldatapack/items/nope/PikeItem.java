package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class PikeItem extends CustomItem implements Listener {
    public PikeItem(int customModelData) {
        super(customModelData, "pike");
    }

    @Override
    public void create() {
        this.createItem(Material.CLOCK);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "Pierce your enemies' armor");
        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Pike"));
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape("DDD", "DND", "DDD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);

        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
    }

    @EventHandler
    public void handleEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();

            if (!(event.getEntity() instanceof LivingEntity)) {
                return;
            }
            if (item.getItemMeta() == null) {
                return;
            }
            if (!item.getItemMeta().hasCustomModelData()) {
                return;
            }
            if (item.getItemMeta().getCustomModelData() != this.getCustomModelData()) {
                return;
            }

            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
            event.setDamage(6);
        }
    }
}
