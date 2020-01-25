package spring.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.app.Main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlayerPaths {

    public static Path getSongsDir(String filename) {
        final Logger LOGGER = LoggerFactory.getLogger(PlayerPaths.class);
        final String separator = File.separator;

        try {
            Path pathProject = (Paths.get(Main.class.getResource(".").toURI())).getParent().getParent();
            Path pathDownload = Paths.get(pathProject.getParent().getParent().toString() + separator + "resources" + separator + "songs");
            if (!Files.exists(pathDownload)) {
                Files.createDirectories(pathDownload);
            }
            return filename == null ? pathDownload : Paths.get(String.format("%s%s%s", pathDownload, separator, filename));
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }
}
