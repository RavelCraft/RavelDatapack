package com.connexal.raveldatapack.items.nope;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerSwordItem extends CustomItem implements Listener {
    public PowerSwordItem(int customModelData) {
        super(customModelData, "powersword");
    }

    @Override
    public void create() {
        this.createItem(Material.NETHERITE_SWORD);

        ItemMeta meta = this.createItemMeta();
        this.setItemLore(meta, "The sword of the gods", "- Summons lighting", "- Gives blindness");
        meta.displayName(Component.text(ChatColor.GOLD.toString() + ChatColor.BOLD + "Power Sword"));
        this.setAttackDamage(meta, 18, EquipmentSlot.HAND);
        meta.addEnchant(Enchantment.SWEEPING_EDGE, 1, false);
        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(this.getNamespacedKey(), this.getItemStack());
        recipe.shape(" N ", " N ", " B ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('B', Material.BLAZE_ROD);
        RavelDatapack.getRecipeManager().registerRecipe(recipe);

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
                player.getWorld().strikeLightning(event.getEntity().getLocation());
                if (event.getEntity() instanceof Player target) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 255, false, false));
                }
            }
        }
    }
}
