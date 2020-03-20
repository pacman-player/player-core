package spring.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "songs_that_play_now")
public class SongThatPlayNow {

    @Embeddable
    public static class SongThatPlayNowId implements Serializable {
        @Column(name = "companyId")
        protected long companyId;
        @Column(name = "songId")
        protected long songId;

        public SongThatPlayNowId() {
        }

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
            this.companyId = companyId;
        }

        public long getSongId() {
            return songId;
        }

        public void setSongId(long songId) {
            this.songId = songId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SongThatPlayNowId that = (SongThatPlayNowId) o;

            if (companyId != that.companyId) return false;
            return songId == that.songId;
        }

        @Override
        public int hashCode() {
            int result = (int) (companyId ^ (companyId >>> 32));
            result = 31 * result + (int) (songId ^ (songId >>> 32));
            return result;
        }
    }

    @EmbeddedId
    private SongThatPlayNowId songThatPlayNow;

    @ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", insertable = false, updatable = false)
    private Company company;

    @ManyToOne(targetEntity = Song.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "songId", insertable = false, updatable = false)
    private Song song;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }


    public Company getCompany() {
        return company;
    }

    public SongThatPlayNow() {
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public SongThatPlayNowId getSongThatPlayNow() {
        return songThatPlayNow;
    }

    public void setSongThatPlayNow(SongThatPlayNowId songThatPlayNow) {
        this.songThatPlayNow = songThatPlayNow;
    }
}
