package spring.app.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OrgType.class)
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

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Genre.class)
    @JoinTable(name = "company_on_banned_genre",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> companyGenres;

    @ManyToMany(targetEntity = Song.class)
    @JoinTable(name = "song_that_play_now",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> songThatPayNow;

    @OneToMany(mappedBy = "company")
    private Set<SongQueue> songQueues;


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

    public Set<Song> getSongThatPayNow() {
        return songThatPayNow;
    }

    public void setSongThatPayNow(Set<Song> songThatPayNow) {
        this.songThatPayNow = songThatPayNow;
    }

    public Set<SongQueue> getSongQueues() {
        return songQueues;
    }

    public void setSongQueues(Set<SongQueue> songQueues) {
        this.songQueues = songQueues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null) return false;
        if (name != null ? !name.equals(company.name) : company.name != null) return false;
        if (startTime != null ? !startTime.equals(company.startTime) : company.startTime != null) return false;
        if (closeTime != null ? !closeTime.equals(company.closeTime) : company.closeTime != null) return false;
        if (user != null ? !user.equals(company.user) : company.user != null) return false;
        if (orgType != null ? !orgType.equals(company.orgType) : company.orgType != null) return false;
        if (morningPlayList != null ? !morningPlayList.equals(company.morningPlayList) : company.morningPlayList != null)
            return false;
        if (middayPlayList != null ? !middayPlayList.equals(company.middayPlayList) : company.middayPlayList != null)
            return false;
        if (eveningPlayList != null ? !eveningPlayList.equals(company.eveningPlayList) : company.eveningPlayList != null)
            return false;
        if (companyGenres != null ? !companyGenres.equals(company.companyGenres) : company.companyGenres != null)
            return false;
        if (songThatPayNow != null ? !songThatPayNow.equals(company.songThatPayNow) : company.songThatPayNow != null)
            return false;
        return songQueues != null ? songQueues.equals(company.songQueues) : company.songQueues == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (closeTime != null ? closeTime.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (orgType != null ? orgType.hashCode() : 0);
        result = 31 * result + (morningPlayList != null ? morningPlayList.hashCode() : 0);
        result = 31 * result + (middayPlayList != null ? middayPlayList.hashCode() : 0);
        result = 31 * result + (eveningPlayList != null ? eveningPlayList.hashCode() : 0);
        result = 31 * result + (companyGenres != null ? companyGenres.hashCode() : 0);
        result = 31 * result + (songThatPayNow != null ? songThatPayNow.hashCode() : 0);
        result = 31 * result + (songQueues != null ? songQueues.hashCode() : 0);
        return result;
    }
}
