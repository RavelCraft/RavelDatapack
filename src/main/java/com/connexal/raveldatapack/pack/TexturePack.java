package com.connexal.raveldatapack.pack;

import com.connexal.raveldatapack.RavelDatapack;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.geysermc.floodgate.api.FloodgateApi;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TexturePack {
    private static String webServerTexturePackPath = null;
    private static int packVersion = 0;

    public static void init() {
        if (!RavelDatapack.shouldResourcePack()) {
            return;
        }

        if (!RavelDatapack.getInstance().getConfig().contains("pathToExportZip")) {
            RavelDatapack.getLog().severe(String.format("[%s] You need to set an export path for the file.", RavelDatapack.getInstance().getDescription().getName()));
            return;
        }
        if (!RavelDatapack.getInstance().getConfig().contains("webServerTexturePackPath")) {
            RavelDatapack.getLog().severe(String.format("[%s] You need to set a webserver path to host the pack on.", RavelDatapack.getInstance().getDescription().getName()));
            return;
        }

        webServerTexturePackPath = RavelDatapack.getInstance().getConfig().getString("webServerTexturePackPath");
        packVersion = RavelDatapack.getInstance().getConfig().getInt("packVersion", 0);
    }

    public static boolean generatePack() {
        if (!RavelDatapack.shouldResourcePack()) {
            return false;
        }

        if (webServerTexturePackPath == null) {
            return false;
        }

        packVersion += 1;
        RavelDatapack.getInstance().getConfig().set("packVersion", packVersion);
        RavelDatapack.getInstance().saveConfig();
        RavelDatapack.getLog().info(String.format("[%s] Generating texture pack v%d...", RavelDatapack.getInstance().getDescription().getName(), packVersion));

        createIfNotExists("pack");
        createIfNotExists("pack/assets");
        createIfNotExists("pack/assets/minecraft");

        createIfNotExists("pack/assets/minecraft/textures");
        createIfNotExists("pack/assets/minecraft/textures/item");
        createIfNotExists("pack/assets/minecraft/textures/material");

        createIfNotExists("pack/assets/minecraft/models");
        createIfNotExists("pack/assets/minecraft/models/item");

        copyOutOfJarIfNotExists("pack.png", "pack/pack.png");
        copyOutOfJarIfNotExists("pack.mcmeta", "pack/pack.mcmeta");
        copyOutOfJarIfNotExists("LICENSE", "pack/LICENSE");

        File zip = new File(RavelDatapack.getInstance().getDataFolder(), "pack.zip");
        if (zip.exists()) {
            zip.delete();
        }

        try {
            deleteTempPackFolder();
            createTempPackFolder();
            ZipUtil.pack(new File(RavelDatapack.getInstance().getDataFolder(), "tmp"), zip);
        } catch (Exception exception) {
            RavelDatapack.getLog().severe(String.format("[%s] Unable to export zip file: %s", RavelDatapack.getInstance().getDescription().getName(), exception.toString()));
            if (zip.exists()) {
                zip.delete();
            }
            deleteTempPackFolder();
            return false;
        }

        boolean exported = false;
        IOException exception = null;
        if (zip.exists()) {
            try {
                String name = RavelDatapack.getInstance().getConfig().getString("pathToExportZip");
                Files.move(Paths.get(RavelDatapack.getInstance().getDataFolder() + "/pack.zip"), Paths.get(name), StandardCopyOption.REPLACE_EXISTING);
                exported = true;
            } catch (IOException e) {
                exception = e;
            }
        }

        if (exported) {
            RavelDatapack.getLog().info(String.format("[%s] Texture pack generated successfully!", RavelDatapack.getInstance().getDescription().getName()));
        } else {
            String message = exception == null ? "Unknown error" : exception.toString();
            RavelDatapack.getLog().severe(String.format("[%s] Unable to export zip file: %s", RavelDatapack.getInstance().getDescription().getName(), message));
        }
        deleteTempPackFolder();

        return exported;
    }

    private static void createIfNotExists(String path) {
        File file = new File(RavelDatapack.getInstance().getDataFolder(), path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static void copyOutOfJarIfNotExists(String path, String outpath) {
        File file = new File(RavelDatapack.getInstance().getDataFolder(), outpath);
        if (!file.exists()) {
            InputStream in = RavelDatapack.getInstance().getResource(path);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int i = 0;
                while ((i = in.read(buf)) != -1) {
                    out.write(buf, 0, i);
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    private static void copyPackFile(String path, String outpath) throws IOException {
        final String newPath = RavelDatapack.getInstance().getDataFolder() + "/" + path;
        final String newOutpath = RavelDatapack.getInstance().getDataFolder() + "/" + outpath;

        File file = new File(newPath);
        if (file.isDirectory()) {
            Files.walk(Paths.get(newPath)).forEach(source -> {
                Path destination = Paths.get(newOutpath, source.toString().substring(newPath.length()));
                try {
                    Files.copy(source, destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Files.copy(Paths.get(newPath), Paths.get(newOutpath), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void createTempPackFolder() throws IOException {
        createIfNotExists("tmp");
        copyPackFile("pack/pack.png", "tmp/pack.png");
        copyPackFile("pack/pack.mcmeta", "tmp/pack.mcmeta");
        copyPackFile("pack/LICENSE", "tmp/LICENSE");
        copyPackFile("pack/assets", "tmp/assets");
    }

    private static void deleteTempPackFolder() {
        File file = new File(RavelDatapack.getInstance().getDataFolder(), "tmp");
        if (file.exists()) {
            deleteDirectory(file);
        }
    }

    private static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static boolean sendTexturePackToPlayer(Player player) {
        if (!RavelDatapack.shouldResourcePack()) {
            return false;
        }

        if (webServerTexturePackPath == null) {
            return false;
        }

        if (RavelDatapack.isFloodgateAPI()) {
            if (FloodgateApi.getInstance().isFloodgateId(player.getUniqueId())) {
                player.sendMessage(ChatColor.YELLOW + "You are playing on Minecraft Bedrock edition. We are warning you that this server uses a texture pack that is not supported by Bedrock at this point.");
                return false;
            }
        }

        player.setResourcePack(webServerTexturePackPath + "?v=" + packVersion);
        RavelDatapack.getLog().info(String.format("[%s] Sent texture pack v%d to: %s", RavelDatapack.getInstance().getDescription().getName(), packVersion, player.getName()));
        return true;
    }
}
