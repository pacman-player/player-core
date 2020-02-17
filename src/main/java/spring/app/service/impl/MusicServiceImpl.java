package spring.app.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
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
    @Autowired
    private SongDao songDao;

    @Value("${music.path}")
    private String musicPath;


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


    @Override
    public ResponseEntity playMusic(String musicAuthor, String musicTitle) {
        System.out.println(musicTitle);
        Long id = songDao.getByNameAndAuthor(musicAuthor, musicTitle).getId();
        File file = new File(musicPath + id + ".mp3");
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
    public ResponseEntity<byte[]> albumsCover(String musicAuthor, String musicTitle) {
        File file = new File(musicPath + musicAuthor + "-" + musicTitle + ".mp3");
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
