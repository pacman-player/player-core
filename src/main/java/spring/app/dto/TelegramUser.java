package spring.app.dto;


import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.File;
import java.util.Objects;

public class TelegramUser {

    @Id
    private Long chatId;
    private String songName;
    private Long songId;
    private File track;

    public TelegramUser() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public File getTrack() {
        return track;
    }

    public void setTrack(File track) {
        this.track = track;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUser telegramUser = (TelegramUser) o;

        if (chatId != null ? !chatId.equals(telegramUser.chatId) : telegramUser.chatId != null) return false;
        return songName != null ? !songName.equals(telegramUser.songName) : telegramUser.songName != null;

    }

    @Override
    public int hashCode() {
        int result = chatId != null ? chatId.hashCode() : 0;
        result = 31 * result + (songName != null ? songName.hashCode() : 0);
        return result;
    }
}
