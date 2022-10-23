package com.connexal.raveldatapack.api.managers;

import com.connexal.raveldatapack.api.RavelDatapackAPI;
import com.connexal.raveldatapack.api.maps.CustomMapRenderer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapManager implements Listener {
    private final Map<Integer, String> savedImages = new HashMap<>();

    public MapManager() {
        this.loadImages();
    }

    @EventHandler
    public void onMapInitEvent(MapInitializeEvent event) {
        if (this.hasImage(event.getMap().getId())) {
            MapView view = event.getMap();
            view.getRenderers().clear();
            try {
                view.addRenderer(new CustomMapRenderer(this.getImage(view.getId())));
            } catch (IOException e) {
                RavelDatapackAPI.getLogger().severe("Failed to load image for map " + view.getId());
            }
            view.setScale(Scale.FARTHEST);
            view.setTrackingPosition(false);
        }
    }

    public void saveImage(Integer id, String url) {
        RavelDatapackAPI.getConfig().set("ids." + id, url);
        RavelDatapackAPI.saveConfig();
    }

    private void loadImages() {
        if (RavelDatapackAPI.getConfig().contains("ids")) {
            RavelDatapackAPI.getConfig().getConfigurationSection("ids").getKeys(false).forEach(id -> {
                savedImages.put(Integer.parseInt(id), RavelDatapackAPI.getConfig().getString("ids." + id));
            });
        }
    }

    public boolean hasImage(int id) {
        return savedImages.containsKey(id);
    }

    public String getImage(int id) {
        return savedImages.get(id);
    }
}
