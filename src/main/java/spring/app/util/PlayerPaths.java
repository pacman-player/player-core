package spring.app.util;

import spring.app.Main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlayerPaths {

    public static Path getSongsDir(String filename) {
        final String separator = File.separator;

        try {
            Path pathProject = (Paths.get(Main.class.getResource(".").toURI())).getParent().getParent();
            Path pathDownload = Paths.get(pathProject.getParent().getParent().toString() + separator + "resources" + separator + "songs");
            if (!Files.exists(pathDownload)) {
                Files.createDirectories(pathDownload);
            }
            return Paths.get(pathDownload + separator + filename);
        } catch (URISyntaxException | IOException ignored) {
            //throw new IOException("Error creating directory.");
        }
        return null;
    }
}
