package spring.app.service;




import java.io.File;

public class CutSongService {
    static javazoom.jl.player.advanced.PlaybackListener pl;
    static javazoom.jl.player.advanced.AdvancedPlayer AP;

    public void play() {
        pl = new javazoom.jl.player.advanced.PlaybackListener() {
        };
        try {
//            AP = javazoom.jl.player.advanced.jlap.playMp3(new File("C:\\\\Users\\\\Shell26\\\\Desktop\\\\techno epic.mp3"), pl);
            AP = javazoom.jl.player.advanced.jlap.playMp3(new File("C:\\\\Users\\\\Shell26\\\\Desktop\\\\techno epic.mp3"), 100,200, pl);
            System.out.println("Sound. start of play: AP = " + AP);
        } catch (Exception e) {
            System.out.println("Sound. SOME EXCEPTION");
        }
    }

    public void stop() {
        System.out.println("Sound. stop() AP = " + AP);
        try {
            AP.stop();
            AP.close();
            System.out.println("Sound. stop");
        } catch (Exception e) {
            System.out.println("Sound. stop() SOME EXCEPTION");
        }
    }
}

//    public static void main(String[] args) {
//        cutSong("C:/Users/aaq-9/Downloads/heroW.wav", "C:/Users/aaq-9/Downloads/hero-copy.wav", 0, 30);
////        cutSong("C:/Users/aaq-9/Downloads/hero.mp3", "C:/Users/aaq-9/Downloads/hero-copy-m.mp3", 2, 10);
//    }
//​
//    public static void cutSong(String sourceFileName, String destinationFileName, int startSecond, int endSecond) {
//        AudioInputStream inputStream = null;
//        AudioInputStream shortenedStream = null;
//        try {
//            File file = new File(sourceFileName);
//            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
//            AudioFormat format = fileFormat.getFormat();
//            inputStream = AudioSystem.getAudioInputStream(file);
//            int bytesPerSecond = format.getFrameSize() * (int) format.getFrameRate();//получаем байты в секунду
//            inputStream.skip(startSecond * bytesPerSecond); //указываем с какой секунды начать
//            long end = (endSecond - startSecond) * (int) format.getFrameRate(); // указываем конечную секунду песни
//            shortenedStream = new AudioInputStream(inputStream, format, end);
//            File destinationFile = new File(destinationFileName);
//            AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
//        } catch (Exception e) {
//            println(e);
//        } finally {
//            if (inputStream != null) try {
//                inputStream.close();
//            } catch (Exception e) {
//                println(e);
//            }
//            if (shortenedStream != null) try {
//                shortenedStream.close();
//            } catch (Exception e) {
//                println(e);
//            }
//        }
//    }
//}
