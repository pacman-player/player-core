package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spring.app.service.abstraction.MusicService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@PropertySource("classpath:uploadedFilesPath.properties")
public class MusicServiceImpl implements MusicService {

    @Value("${uploaded_files_path}")
    private String filePath;
    @Override
    public ServletOutputStream fileToStream(String musicName, HttpServletResponse response) throws ServletException, IOException {
        String file = musicName + ".mp3";



        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        try {

            File mp3 = new File(filePath + file);

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
}
