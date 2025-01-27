package com.connexal.raveldatapack.enchantments;

import com.connexal.raveldatapack.CustomRegistry;
import com.github.imdabigboss.easydatapack.api.types.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.exceptions.CustomEnchantmentException;
import com.github.imdabigboss.easydatapack.api.utils.ItemsUtil;
import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.GameMode;
import org.bukkit.block.Container;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TelekinesisEnchantment implements Listener {
    private final CustomEnchantment enchantment;

    public TelekinesisEnchantment(CustomEnchantment enchantment) {
        this.enchantment = enchantment;
    }

    public static void register(CustomRegistry.CustomRegistryAdder adder) throws CustomEnchantmentException {
        CustomEnchantment enchantment = CustomEnchantment.builder("Telekinesis", "telekinesis", ItemsUtil::isItemATool, EnchantmentTarget.TOOL)
                .anvilMergeCost(level -> 10 * level)
                .tradeCost(level -> 10 * level)
                .tradeable(false)
                .discoverable(false)
                .rarity(EnchantmentRarity.VERY_RARE)
                .eventListener(TelekinesisEnchantment.class)
                .build();

        adder.register(enchantment);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getState() instanceof Container) {
            return;
        }

        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (!this.enchantment.hasEnchantment(item)) {
            return;
        }

        event.setDropItems(false);
        for (ItemStack drop : event.getBlock().getDrops(item)) {
            player.getInventory().addItem(drop);
        }
    }
}
