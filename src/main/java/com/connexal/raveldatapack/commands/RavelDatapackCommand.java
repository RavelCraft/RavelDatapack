package com.connexal.raveldatapack.commands;

import com.connexal.raveldatapack.RavelDatapack;
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

public class RavelDatapackCommand implements CommandExecutor, TabExecutor {
    private final RavelDatapack plugin;

    public RavelDatapackCommand(RavelDatapack plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender, sender.isOp());
            return true;
        }

        if (args[0].equalsIgnoreCase("send")) {
            if (!RavelDatapack.shouldResourcePack()) {
                sender.sendMessage("This server does not use the resource pack.");
                return true;
            }

            if (sender instanceof Player player) {
                if (TexturePack.sendTexturePackToPlayer(player)) {
                    sender.sendMessage("Texture pack sent.");
                } else {
                    sender.sendMessage("Texture pack was not sent because of an error.");
                }
            } else {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    if (TexturePack.sendTexturePackToPlayer(player)) {
                        sender.sendMessage("Sent texture pack to " + player.getName());
                    } else {
                        sender.sendMessage("An error occurred sending the texture pack to " + player.getName());
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("generate")) {
            if (!RavelDatapack.shouldResourcePack()) {
                sender.sendMessage("This server does not use the resource pack.");
                return true;
            }

            if (sender instanceof Player player) {
                if (!player.isOp()) {
                    sender.sendMessage("You must be an op to do this.");
                    return true;
                }
            }

            sender.sendMessage("Regenerating texture pack...");
            if (TexturePack.generatePack()) {
                sender.sendMessage("Done!");
            } else {
                sender.sendMessage("Failed!");
            }
        } else if (args[0].equalsIgnoreCase("allitems")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to do this.");
                return true;
            }

            if (!player.isOp()) {
                sender.sendMessage("You must be an op to do this.");
                return true;
            }

            for (CustomItem item : RavelDatapack.getItemManager().getItems().values()) {
                player.getWorld().dropItem(player.getEyeLocation(), item.getItemStack());
            }
            for (CustomItem item : RavelDatapack.getHatManager().getItems().values()) {
                player.getWorld().dropItem(player.getEyeLocation(), item.getItemStack());
            }
            for (CustomEnchantment enchantment : RavelDatapack.getEnchantmentManager().getEnchantments()) {
                player.getWorld().dropItem(player.getEyeLocation(), enchantment.getBook(enchantment.getMaxLevel()));
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

            if (args.length != 2 && args.length != 3) {
                sender.sendMessage("Usage: /raveldatapack map <url>");
                return true;
            }

            MapView view = RavelDatapack.getInstance().getServer().createMap(player.getWorld());
            view.getRenderers().clear();

            CustomMapRenderer renderer = new CustomMapRenderer();
            if (!renderer.load(args[1])) {
                sender.sendMessage(ChatColor.RED + "Failed to load map.");
                return true;
            }
            view.addRenderer(renderer);

            ItemStack map = new ItemStack(Material.FILLED_MAP);
            MapMeta meta = (MapMeta) map.getItemMeta();
            if (args.length == 3) {
                meta.displayName(Component.text(ChatColor.AQUA + args[2]));
            } else {
                meta.displayName(Component.text(ChatColor.AQUA + "Custom Map"));
            }
            meta.setMapView(view);
            map.setItemMeta(meta);

            player.getWorld().dropItem(player.getEyeLocation(), map);
            RavelDatapack.getMapManager().saveImage(view.getId(), args[1]);

            sender.sendMessage(ChatColor.AQUA + "You were given the map.");
        } else {
            sendHelp(sender, sender.isOp());
        }

        return true;
    }

    private void sendHelp(CommandSender sender, boolean isOp) {
        String sendgen = "";
        if (RavelDatapack.shouldResourcePack()) {
            sendgen = "\n - send";
            if (isOp) {
                sendgen += "\n - generate";
            }
        }
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
            if (RavelDatapack.shouldResourcePack()) {
                cmd.add("send");
                if (sender.isOp()) {
                    cmd.add("generate");
                }
            }

            if (sender.isOp()) {
                cmd.add("allitems");
                cmd.add("map");
            }
        }
        return cmd;
    }
}
