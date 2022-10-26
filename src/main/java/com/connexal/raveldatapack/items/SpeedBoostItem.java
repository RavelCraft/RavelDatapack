package com.connexal.raveldatapack.items;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.exceptions.EasyDatapackException;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpeedBoostItem {
    private static final int COOLDOWN_TIME = 120;
    private static final Map<UUID, Long> lastUseEvent = new HashMap<>();

    public static void register(CustomAdder adder, int customModelData) throws EasyDatapackException {
        CustomItem item = new CustomItem.Builder(customModelData, "speed_boost", ChatColor.RED.toString() + ChatColor.BOLD + "Speed Boost", Material.SUGAR)
                .itemUseEvent(SpeedBoostItem::itemUseEvent)
                .lore("Don't do drugs, they", "aren't good for you.", "Luckily, this is not a", "drug.")
                .hideFlags(true)
                .enchantment(Enchantment.MENDING, 1)
                .build();

        adder.register(item);
    }

    private static void itemUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Long lastUse = lastUseEvent.get(player.getUniqueId());
        long currentTime = System.currentTimeMillis();

        if (lastUse != null) {
            if (lastUse + COOLDOWN_TIME * 1000 > currentTime) {
                player.sendMessage(ChatColor.RED + "You can't use this item yet! Wait another " + (COOLDOWN_TIME - (currentTime - lastUse) / 1000) + " seconds.");
                return;
            } else {
                lastUseEvent.remove(player.getUniqueId());
            }
        }

        lastUseEvent.put(player.getUniqueId(), currentTime);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 2));
        player.sendMessage(ChatColor.AQUA + "You feel a rush of energy!");
    }
}
