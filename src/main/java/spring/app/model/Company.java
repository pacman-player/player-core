package spring.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime closeTime;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OrgType.class)
    @JoinColumn(name = "org_type_id")
    private OrgType orgType;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = PlayList.class)
    @JoinTable(name = "morning_company_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<PlayList> morningPlayList;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = PlayList.class)
    @JoinTable(name = "midday_company_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<PlayList> middayPlayList;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = PlayList.class)
    @JoinTable(name = "evening_company_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<PlayList> eveningPlayList;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Genre.class)
    @JoinTable(name = "company_on_banned_genre",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> bannedGenres;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Song.class)
    @JoinTable(name = "company_on_banned_song",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> bannedSong;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinTable(name = "company_on_banned_author",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private Set<Author> bannedAuthor;

    @OneToMany(mappedBy = "company")
    private Set<SongQueue> songQueues;

    public Company(String name, LocalTime startTime, LocalTime closeTime, User user, OrgType orgType) {
        this.name = name;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.user = user;
        this.orgType = orgType;
    }

    public Company(Long id, String name, LocalTime startTime, LocalTime closeTime, User user, OrgType orgType) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.user = user;
        this.orgType = orgType;
    }

    public Company() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public User getUser() {
        return user;
    }

    public OrgType getOrgType() {
        return orgType;
    }

    public Set<Author> getBannedAuthor() {
        return bannedAuthor;
    }

    public Set<Song> getBannedSong() {
        return bannedSong;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public void setMorningPlayList(Set<PlayList> morningPlayList) {
        this.morningPlayList = morningPlayList;
    }

    public void setMiddayPlayList(Set<PlayList> middayPlayList) {
        this.middayPlayList = middayPlayList;
    }

    public void setEveningPlayList(Set<PlayList> eveningPlayList) {
        this.eveningPlayList = eveningPlayList;
    }

    public void setBannedGenres(Set<Genre> bannedGenres) {
        this.bannedGenres = bannedGenres;
    }

    public void setBannedSong(Set<Song> bannedSong) {
        this.bannedSong = bannedSong;
    }

    public void setBannedAuthor(Set<Author> bannedAuthor) {
        this.bannedAuthor = bannedAuthor;
    }

    public Set<PlayList> getMorningPlayList() {
        return morningPlayList;
    }

    public Set<PlayList> getMiddayPlayList() {
        return middayPlayList;
    }

    public Set<PlayList> getEveningPlayList() {
        return eveningPlayList;
    }

    public Set<Genre> getBannedGenres() {
        return bannedGenres;
    }

    public Set<SongQueue> getSongQueues() {
        return songQueues;
    }

    public void setSongQueues(Set<SongQueue> songQueues) {
        this.songQueues = songQueues;
    }

    public void addBannedAuthor(Author author) {
        this.bannedAuthor.add(author);
    }

    public void addBannedSong(Song song) {
        this.bannedSong.add(song);
    }

    public void addBannedGenre(Genre genre) {
        this.bannedGenres.add(genre);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", closeTime=" + closeTime +
                ", user=" + user +
                ", orgType=" + orgType +
                ", morningPlayList=" + morningPlayList +
                ", middayPlayList=" + middayPlayList +
                ", eveningPlayList=" + eveningPlayList +
                ", bannedGenres=" + bannedGenres +
                ", songQueues=" + songQueues +
                '}';
    }
}
