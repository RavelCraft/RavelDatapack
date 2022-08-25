package com.connexal.raveldatapack.commands;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.utils.schematics.Schematic;
import com.connexal.raveldatapack.utils.schematics.Schematics;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RavelSchematicCommand implements CommandExecutor, TabExecutor {
    private final Map<Player, SchematicData> playerData = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to do this.");
            return true;
        }
        if (!player.isOp()) {
            sender.sendMessage("You must be an op to do this.");
            return true;
        }

        if (args[0].equalsIgnoreCase("paste")) {
            if (args.length != 2) {
                sendHelp(sender);
                return true;
            }

            Location playerLocation = player.getLocation();

            Schematic schematic = Schematics.loadSchematic(args[1]);
            if (schematic == null) {
                sender.sendMessage(ChatColor.RED + "Schematic not found");
                return true;
            }

            schematic.pasteSchematic(playerLocation);
            sender.sendMessage(ChatColor.AQUA + "Schematic pasted");
            return true;
        }

        if (!playerData.containsKey(player)) {
            playerData.put(player, new SchematicData());
        }

        if (args[0].equalsIgnoreCase("pos1")) {
            playerData.get(player).pos1(player.getLocation());
            sender.sendMessage(ChatColor.AQUA + "Set pos1.");
        } else if (args[0].equalsIgnoreCase("pos2")) {
            playerData.get(player).pos2(player.getLocation());
            sender.sendMessage(ChatColor.AQUA + "Set pos2.");
        } else if (args[0].equalsIgnoreCase("basePos1")) {
            playerData.get(player).basePos1(player.getLocation());
            sender.sendMessage(ChatColor.AQUA + "Set basePos1.");
        } else if (args[0].equalsIgnoreCase("basePos2")) {
            playerData.get(player).basePos2(player.getLocation());
            sender.sendMessage(ChatColor.AQUA + "Set basePos2.");
        } else if (args[0].equalsIgnoreCase("create")) {
            if (args.length < 2) {
                sender.sendMessage("Please specify a schematic name.");
                return true;
            }

            SchematicData data = playerData.get(player);
            if (data.pos1() == null || data.pos2() == null || data.basePos1() == null || data.basePos2() == null) {
                sender.sendMessage("Please select some blocks first.");
                return true;
            }

            File outFile = new File(RavelDatapack.getInstance().getDataFolder(), "schematics/" + args[1] + ".ravelschem");
            outFile.getParentFile().mkdirs();
            if (outFile.exists()) {
                sender.sendMessage(ChatColor.RED + "File already exists.");
                return true;
            }

            boolean done = Schematics.createSchematic(data.pos1(), data.pos2(), data.basePos1(), data.basePos2(), outFile);
            if (done) {
                sender.sendMessage(ChatColor.AQUA + "Schematic created.");
            } else {
                sender.sendMessage(ChatColor.RED + "Failed to create schematic.");
            }

            playerData.remove(player);
        } else {
            sendHelp(sender);
        }

        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("""
                 The correct usage is:
                 - paste <name>
                 - pos1
                 - pos2
                 - basePos1
                 - basePos2
                 - create <name>""");
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> cmd = new ArrayList<>();
        if (args.length == 1) {
            cmd.add("paste");
            cmd.add("pos1");
            cmd.add("pos2");
            cmd.add("basePos1");
            cmd.add("basePos2");
            cmd.add("create");
        }
        return cmd;
    }

    private static class SchematicData {
        private Location pos1;
        private Location pos2;
        private Location basePos1;
        private Location basePos2;

        public SchematicData() {
            this.pos1 = null;
            this.pos2 = null;
            this.basePos1 = null;
            this.basePos2 = null;
        }

        public Location pos1() {
            return pos1;
        }

        public void pos1(Location pos1) {
            this.pos1 = pos1;
        }

        public Location pos2() {
            return pos2;
        }

        public void pos2(Location pos2) {
            this.pos2 = pos2;
        }

        public Location basePos1() {
            return basePos1;
        }

        public void basePos1(Location basePos1) {
            this.basePos1 = basePos1;
        }

        public Location basePos2() {
            return basePos2;
        }

        public void basePos2(Location basePos2) {
            this.basePos2 = basePos2;
        }
    }
}
