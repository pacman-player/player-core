package spring.app.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.SongDao;
import spring.app.service.abstraction.MusicService;
import spring.app.util.MP3TagReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
@Transactional
public class MusicServiceImpl implements MusicService {
    private final SongDao songDao;

    @Value("${music.path}")
    private String musicPath;

    public MusicServiceImpl(SongDao songDao) {
        this.songDao = songDao;
    }


    @Override
    public ServletOutputStream fileToStream(String musicName, HttpServletResponse response) throws ServletException, IOException {
        String file = musicName + ".mp3";

        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        try {

            File mp3 = new File(musicPath + file);

            //set response headers
            stream = response.getOutputStream();
            response.setContentType("audio/mpeg");

            response.addHeader("Content-Disposition", "attachment; filename=" + file);

            response.setContentLength((int) mp3.length());

            FileInputStream input = new FileInputStream(mp3);
            buf = new BufferedInputStream(input);
            int readBytes = 0;
            //read from the file; write to the ServletOutputStream
            while ((readBytes = buf.read()) != -1)
                stream.write(readBytes);
            return stream;
        } catch (IOException ioe) {
            throw new ServletException(ioe.getMessage());
        } finally {
            if (stream != null)
                stream.close();
            if (buf != null)
                buf.close();
        }
    }


    /**
     * Метод для воспроизведения mp3 файла на плеер на фронте.
     * Получаем id песни, по нему создаем InputStreamResource из файла.
     *
     * @param musicTitleId
     * @param musicAuthorId
     * @return InputStreamResource из файла, обернутый в ResponseEntity
     */

    @Override
    public ResponseEntity playMusic(String musicAuthorId, String musicTitleId) {

        File file = new File(musicPath + musicAuthorId +"/" + musicTitleId + ".mp3");
        long length = file.length();
        InputStreamResource inputStreamResource = null;
        try {
            inputStreamResource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.set("accept-ranges", "bytes");
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> albumsCover(String musicAuthorId, String musicTitleId) {
        File file = new File(musicPath + musicAuthorId + "/" + musicTitleId + ".mp3");
        try {
            byte[] media = IOUtils.toByteArray(MP3TagReader.readAlbumsCover(file));
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
