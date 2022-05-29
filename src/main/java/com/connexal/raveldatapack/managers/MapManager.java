package com.connexal.raveldatapack.managers;

import com.connexal.raveldatapack.RavelDatapack;
import com.connexal.raveldatapack.maps.CustomMapRenderer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapManager implements Listener {
    private final ConfigManager.YmlConfig dataYML = RavelDatapack.getConfig("data");
    private final Map<Integer, String> savedImages = new HashMap<>();

    /**
     * Initialises the data file.
     */
    public void init() {
        RavelDatapack.getInstance().getServer().getPluginManager().registerEvents(this, RavelDatapack.getInstance());
        loadImages();
    }

    @EventHandler
    public void onMapInitEvent(MapInitializeEvent event) {
        if (hasImage(event.getMap().getId())) {
            MapView view = event.getMap();
            view.getRenderers().clear();
            try {
                view.addRenderer(new CustomMapRenderer(getImage(view.getId())));
            } catch (IOException e) {
                RavelDatapack.getLog().severe("Failed to load image for map " + view.getId());
            }
            view.setScale(Scale.FARTHEST);
            view.setTrackingPosition(false);
        }
    }

    /**
     * When a new map is created, save the ID and Image to data file
     *
     * @param id  Map ID
     * @param url Image URL
     */
    public void saveImage(Integer id, String url) {
        dataYML.getConfig().set("ids." + id, url);
        dataYML.saveConfig();
    }

    /**
     * Loads images from data file to HashMap.
     */
    private void loadImages() {
        if (dataYML.getConfig().contains("ids"))
            dataYML.getConfig().getConfigurationSection("ids").getKeys(false).forEach(id -> {
                savedImages.put(Integer.parseInt(id), dataYML.getConfig().getString("ids." + id));
            });
    }

    public boolean hasImage(int id) {
        return savedImages.containsKey(id);
    }

    public String getImage(int id) {
        return savedImages.get(id);
    }
}
