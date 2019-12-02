package spring.app.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class SongThatPlayNow {
    @EmbeddedId
    private SongThatPlayNowId songThatPlayNowId;

    public SongThatPlayNowId getSongThatPlayNowId() {
        return songThatPlayNowId;
    }

    public void setSongThatPlayNowId(SongThatPlayNowId songThatPlayNowId) {
        this.songThatPlayNowId = songThatPlayNowId;
    }
}
