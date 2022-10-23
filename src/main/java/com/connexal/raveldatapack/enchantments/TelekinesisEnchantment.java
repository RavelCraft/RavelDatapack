package com.connexal.raveldatapack.enchantments;

import com.connexal.raveldatapack.utils.ItemsUtil;
import io.papermc.paper.enchantments.EnchantmentRarity;
import org.bukkit.GameMode;
import org.bukkit.block.Container;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TelekinesisEnchantment extends CustomEnchantment implements Listener {
    public TelekinesisEnchantment() {
        super("telekinesis", "Telekinesis");
    }

    @Override
    public void create() {
        this.instance.getServer().getPluginManager().registerEvents(this, this.instance);
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
        if (!this.hasEnchantment(item)) {
            return;
        }

        event.setDropItems(false);
        for (ItemStack drop : event.getBlock().getDrops(item)) {
            player.getInventory().addItem(drop);
        }
    }

    @Override
    public boolean canEnchantItemInternal(ItemStack item) {
        return ItemsUtil.isItemATool(item);
    }

    @Override
    public int getAnvilMergeCost(int level) {
        return 10 * level;
    }

    @Override
    public int getTradeCost(int level) {
        return 10 * level;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.VERY_RARE;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
