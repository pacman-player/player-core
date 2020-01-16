package spring.app.service;


import javazoom.jl.decoder.*;
import java.io.*;

public class CutSongService {

    public static void main(String[] args) {
        try {
            сutSongMy("Bake.mp3", -1, 10);
        } catch (DecoderException | IOException | BitstreamException e) {
            e.printStackTrace();
        }
    }

    private static void сutSongMy(String song, int start, int len) throws IOException, BitstreamException, DecoderException {
        FileInputStream in = new FileInputStream(song);
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
        in.close();

        FileOutputStream output = new FileOutputStream(song);
        output.write(arr, 0, numberOfBytesToCut);
        output.close();
    }
}
