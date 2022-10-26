package com.connexal.raveldatapack.commands;

import com.github.imdabigboss.easydatapack.api.EasyDatapackAPI;
import com.github.imdabigboss.easydatapack.api.blocks.CustomBlock;
import com.github.imdabigboss.easydatapack.api.enchantments.CustomEnchantment;
import com.github.imdabigboss.easydatapack.api.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RavelDatapackCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender, sender.isOp());
            return true;
        }

        if (args[0].equalsIgnoreCase("allitems")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to do this.");
                return true;
            }

            if (!player.isOp()) {
                sender.sendMessage("You must be an op to do this.");
                return true;
            }

            for (CustomItem item : EasyDatapackAPI.getItemManager().getCustomItems()) {
                player.getWorld().dropItem(player.getEyeLocation(), item.getItemStack());
            }
            for (CustomEnchantment enchantment : EasyDatapackAPI.getEnchantmentManager().getEnchantments()) {
                player.getWorld().dropItem(player.getEyeLocation(), enchantment.getBook(enchantment.getMaxLevel()));
            }
            for (CustomBlock block : EasyDatapackAPI.getBlockManager().getCustomBlocks()) {
                player.getWorld().dropItem(player.getEyeLocation(), block.createBlockItem());
            }

            sender.sendMessage(ChatColor.AQUA + "You were given all the items.");
        } else if (args[0].equalsIgnoreCase("map")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to do this.");
                return true;
            }

            if (!player.isOp()) {
                sender.sendMessage("You must be an op to do this.");
                return true;
            }

            if (args.length != 2) {
                sender.sendMessage("Usage: /raveldatapack map <url>");
                return true;
            }

            ItemStack map = EasyDatapackAPI.getMapManager().createMap(args[1]);
            if (map == null) {
                sender.sendMessage("Invalid URL.");
                return true;
            }

            player.getWorld().dropItem(player.getEyeLocation(), map);
            sender.sendMessage(ChatColor.AQUA + "You were given the map.");
        } else {
            sendHelp(sender, sender.isOp());
        }

        return true;
    }

    private void sendHelp(CommandSender sender, boolean isOp) {
        String sendgen = "";
        if (isOp) {
            sendgen += "\n - allitems";
            sendgen += "\n - map <url>";
        }

        sender.sendMessage("The correct usage is:" + sendgen);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> cmd = new ArrayList<>();
        if (args.length == 1) {
            if (sender.isOp()) {
                cmd.add("allitems");
                cmd.add("map");
            }
        }
        return cmd;
    }
}
