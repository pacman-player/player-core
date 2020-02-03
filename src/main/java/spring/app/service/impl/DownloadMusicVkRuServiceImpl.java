package spring.app.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.model.SongDownloadRequestInfo;
import spring.app.service.abstraction.DownloadMusicVkRuService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
        String downloadUrl = getLinkFromDB(id);
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

    private String getLinkFromDB(long id) {
        return null;
    }
}