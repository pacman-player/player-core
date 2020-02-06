package spring.app.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SongThatPlayNowId implements Serializable {
    private long companyId;
    private long songId;

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
