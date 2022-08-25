package com.connexal.raveldatapack.commands;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.dimensions.CustomDimension;
import com.connexal.raveldatapack.enchantments.CustomEnchantment;
import com.connexal.raveldatapack.items.CustomItem;
import com.connexal.raveldatapack.maps.CustomMapRenderer;
import com.connexal.raveldatapack.pack.TexturePack;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RavelBiomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Too many arguments.");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        CustomDimension dimension = RavelDatapack.getDimensionManager().getDimension(player.getWorld().getName());
        if (dimension == null) {
            sender.sendMessage(ChatColor.RED + "This world is not a Ravel dimension.");
            return true;
        }

        sender.sendMessage(ChatColor.AQUA + "You are in a " + dimension.getBiomeName(player.getLocation().getBlock().getBiome()) + " in the " + dimension.getName() + " dimension.");

        return true;
    }
}
