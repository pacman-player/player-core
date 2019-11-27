package spring.app.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "company")

public class Company {
    @Id
    private Long id;

    private String name;

    private LocalTime startTime;

    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Company.class)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Company.class)
    private OrgType orgType;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
    @JoinTable(name = "morning_company_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<PlayList> morningPlayList;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
    @JoinTable(name = "midday_company_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<PlayList> middayPlayList;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
    @JoinTable(name = "evening_company_on_play_list",
            joinColumns = {@JoinColumn(name = "play_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_id")})
    private Set<PlayList> eveningPlayList;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
    @JoinTable(name = "company_on_banned_genre",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> companyGenres;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Company.class)
    @JoinTable(name = "song_that_play_now",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Song songThatPayNow;

    public Company(String name, LocalTime startTime, LocalTime closeTime, User user, OrgType orgType) {
        this.name = name;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.user = user;
        this.orgType = orgType;
    }

    public Company(){}

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

    public void setCompanyGenres(Set<Genre> companyGenres) {
        this.companyGenres = companyGenres;
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

    public Set<Genre> getCompanyGenres() {
        return companyGenres;
    }

    public void setSongThatPayNow(Song songThatPayNow) {
        this.songThatPayNow = songThatPayNow;
    }

    public Song getSongThatPayNow() {
        return songThatPayNow;
    }
}
