package spring.app.service;

import java.util.Random;


public class EmailPasswordGeneration {

    public String generate() {
        String PASSWORD = "";
        String[] data = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };
        int length = randomNum(4, 10);
        String[] p = new String[length];

        for (int i = 0; i < length; i++) {
            p[i] = data[randomNum(0, data.length)];
        }
        for (String s : p) {
            PASSWORD += s;
        }
        return PASSWORD;
    }

    private static int randomNum(int min, int max){
        Random r = new Random();
        return r.nextInt(max) + min;
    }
}
