package spring.app.util;

import java.io.*;

import com.mpatric.mp3agic.*;
import org.apache.commons.io.FileUtils;

/**
 * Утилитный класс для чтения ID3-тегов mp3 файлов
 */

public class MP3TagReader {
    public static InputStream readAlbumsCover(File file) throws IOException {
        try {
            Mp3File mp3File = new Mp3File(file);
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            return new ByteArrayInputStream(id3v2Tag.getAlbumImage());
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            return new ByteArrayInputStream(
                    FileUtils.readFileToByteArray(new File("src/main/resources/static/img/defaultAlbumsCover.jpg")));
        }
    }
}