package com.connexal.raveldatapack.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class StormbreakerItem extends CustomItem implements Listener {
    public StormbreakerItem(int customModelData) {
        super();
        this.customModelData = customModelData;
        this.namespaceKey = "stormbreaker";
    }

    @Override
    public void create() {
        this.itemStack = new ItemStack(Material.NETHERITE_AXE, 1);

        ItemMeta meta = this.createItemMeta();

        this.setItemLore(meta, "Thor's weapon", "- Summon lightning", "- Very strong");

        meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Stormbreaker");
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        this.setAttackDamage(meta, 60, EquipmentSlot.HAND);
        meta.setCustomModelData(customModelData);

        this.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(namespaceKey), itemStack);
        recipe.shape(" NN"," BN", "B  ");
        recipe.setIngredient('N', Material.NETHER_STAR);
        recipe.setIngredient('B', Material.BLAZE_ROD);
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

                Location target = player.getTargetBlock(null, 50).getLocation();
                for (int i = 0; i < 5; i++) {
                    player.getWorld().strikeLightning(getRandomizedLocation(target));
                }
            }
        }
    }

    private Location getRandomizedLocation(Location location) {
        Random random = new Random();
        location.setX(location.getX() + (random.nextDouble() * 1.5) - 1.5 / 2);
        location.setY(location.getY() + (random.nextDouble() * 1.5) - 1.5 / 2);
        return location;
    }
}
