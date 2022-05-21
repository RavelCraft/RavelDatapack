package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.pack.TexturePack;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessageManager implements PluginMessageListener {
    public static final String CHANNEL_ID = "imdabigboss:main";

    public PluginMessageManager() {
        RavelDatapack.getInstance().getServer().getMessenger().registerIncomingPluginChannel(RavelDatapack.getInstance(), PluginMessageManager.CHANNEL_ID, this);
        RavelDatapack.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(RavelDatapack.getInstance(), PluginMessageManager.CHANNEL_ID);
    }

    public void unregister() {
        RavelDatapack.getInstance().getServer().getMessenger().unregisterOutgoingPluginChannel(RavelDatapack.getInstance());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        //EasyCraft.getLog().info("Plugin Message Received " + channel);

        if (channel.equalsIgnoreCase(CHANNEL_ID)) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if (subChannel.equalsIgnoreCase("RavelDatapack")) {
                String cmd = in.readUTF();
                String data = in.readUTF();
                this.runBungeeCmd(cmd, data);
            }
        }
    }

    private void runBungeeCmd(String cmd, String data) {
        if (cmd.equalsIgnoreCase("sendresourcepack")) {
            Player target = RavelDatapack.getInstance().getServer().getPlayer(data);
            if (target != null) {
                TexturePack.sendTexturePackToPlayer(target);
            }
        }
    }

    public void sendCmd(Player player, String cmd, String data){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        try {
            out.writeUTF("RavelDatapack");
            out.writeUTF(cmd);
            out.writeUTF(data);
        } catch (IOException ignored) {}

        player.sendPluginMessage(RavelDatapack.getInstance(), CHANNEL_ID, bytes.toByteArray());
        //EasyCraft.getLog().info("Sent plugin message to " + player.getName() + ": " + cmd + " " + data);

        try {
            out.close();
            bytes.close();
        } catch (IOException ignored) {}
    }
}
