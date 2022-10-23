package com.connexal.raveldatapack.api.maps;

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

    public CustomMapRenderer(URL url) throws IOException {
        this.image = null;
        this.done = false;

        BufferedImage image = ImageIO.read(url);
        image = MapPalette.resizeImage(image);
        this.image = image;
    }

    public CustomMapRenderer(String url) throws IOException {
        this(new URL(url));
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        if (this.done) {
            return;
        }

        canvas.drawImage(0, 0, this.image);
        view.setTrackingPosition(false);
        this.done = true;
    }
}
