package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.service.abstraction.MusicService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
@Transactional
public class MusicServiceImpl implements MusicService {

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
    public ResponseEntity playMusic(String musicTitle) {
        File file = new File(musicPath + musicTitle + ".mp3");
        long length = file.length();
        InputStreamResource inputStreamResource = null;
        try {
            inputStreamResource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(length);
        httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(inputStreamResource, httpHeaders, HttpStatus.OK);
    }
}
