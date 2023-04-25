package com.connexal.raveldatapack.utils;

import com.connexal.raveldatapack.RavelDatapack;

import java.nio.file.Path;

public class TexturePath {
    public static Path block(String namespaceKey) {
        return RavelDatapack.getInstance().getDataFolder().toPath().resolve("textures").resolve("blocks").resolve(namespaceKey + ".png");
    }

    public static Path item(String namespaceKey) {
        return RavelDatapack.getInstance().getDataFolder().toPath().resolve("textures").resolve("items").resolve(namespaceKey + ".png");
    }

    public static Path blockItem(String namespaceKey) {
        return block(namespaceKey);
    }
}
