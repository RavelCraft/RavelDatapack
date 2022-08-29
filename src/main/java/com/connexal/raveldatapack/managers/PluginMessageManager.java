package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class PluginMessageManager implements PluginMessageListener {
    private static final String CHANNEL_ID = "imdabigboss:main";
    private static final String CHANNEL_NAME = "RavelDatapack";

    private final Queue<PluginMessageData> pluginMessageQueue = new LinkedList<>();

    public PluginMessageManager() {
        RavelDatapack.getInstance().getServer().getMessenger().registerIncomingPluginChannel(RavelDatapack.getInstance(), CHANNEL_ID, this);
        RavelDatapack.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(RavelDatapack.getInstance(), CHANNEL_ID);
    }

    public void unregister() {
        RavelDatapack.getInstance().getServer().getMessenger().unregisterOutgoingPluginChannel(RavelDatapack.getInstance());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equalsIgnoreCase(CHANNEL_ID)) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            RavelDatapack.getLog().info("Received subchannel: " + subChannel);
            if (subChannel.equalsIgnoreCase(CHANNEL_NAME)) {
                String cmd = in.readUTF();
                String data = in.readUTF();
                UUID uuid = UUID.fromString(in.readUTF());

                PluginMessageData messageData = new PluginMessageData(cmd, data, uuid);
                Player target = RavelDatapack.getInstance().getServer().getPlayer(uuid);
                if (target == null) {
                    this.pluginMessageQueue.add(messageData);
                } else {
                    this.runVelocityCmd(messageData);
                }
            }
        }
    }

    private void runVelocityCmd(PluginMessageData data) {

    }

    public void readQueuedPluginMessages() {
        while (!this.pluginMessageQueue.isEmpty()) {
            PluginMessageManager.PluginMessageData data = this.pluginMessageQueue.poll();
            this.runVelocityCmd(data);
        }
    }

    private static class PluginMessageData {
        private final String cmd;
        private final String data;
        private final UUID uuid;

        public PluginMessageData(String cmd, String data, UUID uuid) {
            this.cmd = cmd;
            this.data = data;
            this.uuid = uuid;
        }

        public String cmd() {
            return this.cmd;
        }

        public String data() {
            return this.data;
        }

        public UUID uuid() {
            return this.uuid;
        }
    }
}
