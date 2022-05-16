package com.connexal.raveldatapack;

import com.connexal.raveldatapack.commands.RavelDatapackCommand;
import com.connexal.raveldatapack.custom.items.CustomItem;
import com.connexal.raveldatapack.managers.EnchantmentManager;
import com.connexal.raveldatapack.managers.HatManager;
import com.connexal.raveldatapack.managers.ItemManager;
import com.connexal.raveldatapack.managers.MapManager;
import com.connexal.raveldatapack.pack.TexturePack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import org.geysermc.floodgate.api.FloodgateApi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.logging.Logger;

public final class RavelDatapack extends JavaPlugin implements PluginMessageListener {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static RavelDatapack instance = null;

    private static ItemManager itemManager = null;
    private static HatManager hatManager = null;
    private static EnchantmentManager enchantmentManager = null;
    private static MapManager mapManager = null;

    private static boolean useResourcePack = true;
    private static boolean floodgateAPI = false;

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        itemManager = new ItemManager();
        hatManager = new HatManager();
        enchantmentManager = new EnchantmentManager();
        mapManager = new MapManager();

        if (this.getServer().getPluginManager().getPlugin("floodgate") != null) {
            if (FloodgateApi.getInstance() != null) {
                floodgateAPI = true;
            }
        }

        this.saveDefaultConfig();

        if (this.getConfig().contains("useResourcePack")) {
            useResourcePack = this.getConfig().getBoolean("useResourcePack");
        } else {
            this.getConfig().set("useResourcePack", true);
            this.saveConfig();
        }

        log.info(String.format("[%s] Registered %d custom enchantments", getDescription().getName(), enchantmentManager.init()));
        log.info(String.format("[%s] Registered %d custom items", getDescription().getName(), itemManager.init()));
        log.info(String.format("[%s] Registered %d custom hats", getDescription().getName(), hatManager.init()));
        mapManager.init();

        log.info(String.format("[%s] Adding commands", getDescription().getName()));
        this.getCommand("raveldatapack").setExecutor(new RavelDatapackCommand(this));

        log.info(String.format("[%s] Initialising texture pack", getDescription().getName()));
        TexturePack.init();

        log.info(String.format("[%s] Registering events", getDescription().getName()));
        instance.getServer().getPluginManager().registerEvents(new EventListener(), this);

        log.info(String.format("[%s] All done! Enabled Version %s.", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        this.getServer().getScheduler().cancelTasks(this);

        int num = 0;
        for (CustomItem item : itemManager.getItems().values()) {
            this.getServer().removeRecipe(NamespacedKey.minecraft(item.getNamespaceKey()));
            num ++;
        }
        log.info(String.format("[%s] Unregistered %d custom item recipes", getDescription().getName(), num));

        num = 0;
        for (CustomItem item : hatManager.getItems().values()) {
            this.getServer().removeRecipe(NamespacedKey.minecraft(item.getNamespaceKey()));
            num ++;
        }
        log.info(String.format("[%s] Unregistered %d custom hat recipes", getDescription().getName(), num));

        num = 0;
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : enchantmentManager.getEnchantments()){
                byKey.remove(enchantment.getKey());
                num ++;
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : enchantmentManager.getEnchantments()){
                byName.remove(enchantment.getName());
                num ++;
            }
        } catch (Exception ignored) { }
        log.info(String.format("[%s] Unregistered %d custom enchantments", getDescription().getName(), num));
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equalsIgnoreCase("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if (subChannel.equalsIgnoreCase("RavelDatapack")) {
                String cmd = in.readUTF();
                String data = in.readUTF();
                this.runBungeeCmd(player, cmd, data);
            }
        }
    }

    private void runBungeeCmd(Player player, String cmd, String data) {
        if (cmd.equalsIgnoreCase("sendresourcepack")) {
            Player target = this.getServer().getPlayer(data);
            if (target != null) {
                TexturePack.sendTexturePackToPlayer(target);
            }
        }
    }

    /**
     * Send a command to the BungeeCord server
     * @param player The {@link Player} to use to send the packet
     * @param cmd The command to send
     * @param data The command data to send
     */
    public static void sendCmd(Player player, String cmd, String data){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("RavelDatapack");
            out.writeUTF(cmd);
            out.writeUTF(data);
        } catch (IOException ignored) {}

        player.sendPluginMessage(instance, "BungeeCord", b.toByteArray());

        try {
            out.close();
            b.close();
        } catch (IOException ignored) {}
    }

    public static Logger getLog() {
        return log;
    }
    public static RavelDatapack getInstance() {
        return instance;
    }

    public static ItemManager getItemManager() {
        return itemManager;
    }
    public static HatManager getHatManager() {
        return hatManager;
    }
    public static EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }
    public static MapManager getMapManager() {
        return mapManager;
    }

    public static boolean isFloodgateAPI() {
        return floodgateAPI;
    }
    public static boolean shouldResourcePack() {
        return useResourcePack;
    }
}
