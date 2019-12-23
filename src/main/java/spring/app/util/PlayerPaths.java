package spring.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:uploadedFilesPath.properties")
public class PlayerPaths {

    @Value("${songs_dir}")
    private static String songsDir;

    @Value("${disk_letter}")
    private static String diskLetter;

    @Value("${use_home_dir}")
    private static boolean useHomeDir;

    public static Path getSongsDir(String filename) throws IOException, URISyntaxException {
        final String os = System.getProperty("os.name").toLowerCase();
        final String home = System.getProperty("user.home");
        final String separator = File.separator;
        Path path = Paths.get(PlayerPaths.class.getResource(".").toURI());
        String path1 = path.getParent().getParent().toString() + songsDir + separator + filename;

        return Paths.get(path1);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(getSongsDir("file.mp3"));
    }
}
