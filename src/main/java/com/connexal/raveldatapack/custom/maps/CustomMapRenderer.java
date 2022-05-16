package com.connexal.raveldatapack.custom.maps;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.net.URL;

public class CustomMapRenderer extends MapRenderer {
    private BufferedImage image;
    private boolean done;

    public CustomMapRenderer() {
        this.image = null;
        this.done = false;
    }

    public CustomMapRenderer(String utl) throws IOException {
        this.image = null;
        this.done = false;

        if (load(utl)) {
            return;
        }
        throw new IOException("Failed to load image from " + utl);
    }

    public boolean load(String url) {
        BufferedImage image;
        try {
            image = ImageIO.read(new URL(url));
            image = MapPalette.resizeImage(image);
        } catch (IOException e) {
            return false;
        }
        this.image = image;
        return true;
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        if (done) {
            return;
        }

        canvas.drawImage(0, 0, this.image);
        view.setTrackingPosition(false);
        this.done = true;
    }
}
