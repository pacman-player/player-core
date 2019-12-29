package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.DownloadMusicVkRuService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadMusicVkRuServiceImpl implements DownloadMusicVkRuService {

    @Override
    public List<SongDownloadRequestInfo> searchSongByAuthorOrSongs(String artist, String track) throws IOException {
        final String SEARCH_BASE_URL = "https://downloadmusicvk.ru/audio/search?q=";
        List<SongDownloadRequestInfo> list = new ArrayList<>();

        if (artist.isEmpty() && track.isEmpty()) {
            return null;
        }
        String searchUrl = SEARCH_BASE_URL + artist.trim().toLowerCase().replaceAll("\\s", "+") +
                "+" + track.trim().toLowerCase().replaceAll("\\s", "+");

        if (track.isEmpty()) {
            searchUrl += "&performer_only=1&search_sort=2";
        }
        if (artist.isEmpty()) {
            searchUrl += "&performer_only=0&search_sort=2";
        }

        Document document = Jsoup.connect(searchUrl).get();
        if (document == null) {
            return null;
        }
        Elements aElements = document.getElementsByAttributeValue("class", "btn btn-primary btn-xs download");

        for (Element aElement : aElements) {
            String[] arr = aElement.attr("href").substring(19).split("&");
            if (arr.length < 6) {
                continue;
            }
            String downloadUrl = "https://downloadmusicvk.ru/audio/download?" +
                    arr[1] + "&" + arr[2] + "&" + arr[5] + "&" + arr[0];
            list.add(new SongDownloadRequestInfo(downloadUrl, arr[1], arr[2]));
        }
        return list;
    }

    @Override
    public byte[] getSong(long id) throws IOException {
        String downloadUrl = temp(id);
        URL obj = new URL(downloadUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream in = con.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            in.close();
            return buffer.toByteArray();
        } else {
            return null;
        }
    }

    private String temp(long id) {
        return null;
    }

    public byte[] getSongForTelegram(String artist, String track) {
        final String SEARCH_BASE_URL = "https://downloadmusicvk.ru/audio/search?q=";
        if (track.isEmpty()) {
            return null;
        }
        String searchUrl = SEARCH_BASE_URL + artist.trim().toLowerCase().replaceAll("\\s", "+") +
                "+" + track.trim().toLowerCase().replaceAll("\\s", "+");

        try {
            Document document = Jsoup.connect(searchUrl).get();
            if (document == null) {
                return null;
            }

            Elements aElements = document.getElementsByAttributeValue("class", "btn btn-primary btn-xs download");
            for (Element aElement : aElements) {
                String[] arr = aElement.attr("href").substring(19).split("&");
                if (arr.length < 6) {
                    continue;
                }

                if (!decode(arr[1].substring(7)).replaceAll("\\+", " ").toLowerCase().contains(artist.trim().toLowerCase()) ||
                        !decode(arr[2].substring(6)).replaceAll("\\+", " ").toLowerCase().contains(track.trim().toLowerCase())) {
                    continue;
                }
                if (artist.isEmpty()) {
                    artist = decode(arr[1].substring(7)).replaceAll("\\+", " ").toLowerCase();
                }
                String downloadUrl = "https://downloadmusicvk.ru/audio/download?" +
                        arr[1] + "&" + arr[2] + "&" + arr[5] + "&" + arr[0];

                URL obj = new URL(downloadUrl);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = con.getInputStream();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = in.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    in.close();
                    return buffer.toByteArray();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String decode(String line) throws UnsupportedEncodingException {
        return URLDecoder.decode(URLDecoder.decode(line, "UTF-8"), "UTF-8");
    }
}