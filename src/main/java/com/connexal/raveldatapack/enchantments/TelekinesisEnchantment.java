package com.connexal.raveldatapack.enchantments;

import com.github.imdabigboss.easydatapack.api.CustomAdder;
import com.github.imdabigboss.easydatapack.api.enchantments.CustomEnchantment;
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

    public static void register(CustomAdder adder) throws CustomEnchantmentException {
        CustomEnchantment enchantment = new CustomEnchantment.Builder("Telekinesis", "telekinesis", TelekinesisEnchantment::canEnchantItem, EnchantmentTarget.TOOL)
                .anvilMergeCost(level -> 10 * level)
                .tradeCost(level -> 10 * level)
                .tradeable(false)
                .discoverable(false)
                .rarity(EnchantmentRarity.VERY_RARE)
                .eventListener(TelekinesisEnchantment.class)
                .build();

        adder.register(enchantment);
    }

    private static boolean canEnchantItem(ItemStack item) {
        return ItemsUtil.isItemATool(item);
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
