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
    private final static Logger LOGGER = LoggerFactory.getLogger(PlayerPaths.class);

    public static Path getSongsDir(String filename) {
        final String separator = File.separator;

        try {
            Path pathProject = (Paths.get(Main.class.getResource(".").toURI())).getParent().getParent();
            LOGGER.debug("pathProject = {}", pathProject);
            Path pathDownload = Paths.get(pathProject.getParent().getParent().toString() + separator + "music");
            LOGGER.debug("pathDownload = {}", pathDownload);
            if (!Files.exists(pathDownload)) {
                LOGGER.debug("Creating pathDownload");
                Files.createDirectories(pathDownload);
            }
            return filename == null ? pathDownload : Paths.get(String.format("%s%s%s", pathDownload, separator, filename));
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error("Error! :(");
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }
}
