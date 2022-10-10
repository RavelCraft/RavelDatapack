package com.connexal.raveldatapack.items;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpeedBoostItem extends CustomItem implements Listener {
    private static final int COOLDOWN_TIME = 120;
    private final Map<UUID, Long> lastUseEvent = new HashMap<>();

    public SpeedBoostItem(int customModelData) {
        super(customModelData, "speed_boost");
    }

    @Override
    public void create() {
        this.createItem(Material.SUGAR);

        ItemMeta meta = this.createItemMeta(true, true);
        this.setItemLore(meta, "Don't do drugs, they", "aren't good for you.", "Luckily, this is not a", "drug.");
        meta.displayName(Component.text(ChatColor.RED.toString() + ChatColor.BOLD + "Speed Boost"));
        meta.addEnchant(Enchantment.MENDING, 1, false);
        this.setItemMeta(meta);

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
                Long lastUse = this.lastUseEvent.get(player.getUniqueId());
                long currentTime = System.currentTimeMillis();

                if (lastUse != null) {
                    if (lastUse + COOLDOWN_TIME * 1000 > currentTime) {
                        player.sendMessage(ChatColor.RED + "You can't use this item yet! Wait another " + (COOLDOWN_TIME - (currentTime - lastUse) / 1000) + " seconds.");
                        return;
                    } else {
                        this.lastUseEvent.remove(player.getUniqueId());
                    }
                }

                this.lastUseEvent.put(player.getUniqueId(), currentTime);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 2));
                player.sendMessage(ChatColor.AQUA + "You feel a rush of energy!");
            }
        }
    }
}
