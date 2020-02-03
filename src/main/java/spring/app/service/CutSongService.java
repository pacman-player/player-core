package spring.app.service;


import javazoom.jl.decoder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CutSongService {

    @Autowired
    public CutSongService() {
    }

    public byte[] сutSongMy(byte[] song, int start, int len) throws IOException, BitstreamException, DecoderException {
        ByteArrayInputStream in = new ByteArrayInputStream(song);

        Decoder decode = new Decoder();
        Bitstream bStream = new Bitstream(in);
        Header head = bStream.readFrame();
        decode.decodeFrame(head, bStream);

        int timeMS = len * 1000;
        int numberOfFrames = timeMS / (int)head.ms_per_frame();
        int numberOfBytesToCut = numberOfFrames  * head.framesize;
        int numberBytesToSkip = ((start + 1) * 1000) / (int)head.ms_per_frame() * head.framesize;
        byte[] arr = new byte[in.available()];

        in.skip(numberBytesToSkip);
        in.read(arr);
//        in.close();
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(song.length);
        byteOutputStream.write(arr, 0, numberOfBytesToCut);
//        byteOutputStream.close();
        return byteOutputStream.toByteArray();
    }
}
