package spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import spring.app.dto.TelegramUserDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс, описывающий посетителя заведения - пользователя мессенджера Telegram,
 * обратившегося к нашему боту для заказа песни. Эквивалентен объекту User из Telegram API.
 */

@Entity
@Table(name = "telegram_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TelegramUser {

    @Id
    @Column(name = "telegram_user_id")
    private Long id;

    private String firstName;

    private Boolean isBot;

    private String lastName;

    private String userName;

    private String languageCode;

    @OneToMany(mappedBy = "visitPrimaryKey.telegramUser",
            cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

    public TelegramUser() {}

    public TelegramUser(TelegramUserDto telegramUserDto) {
        this.id = telegramUserDto.getId();
        this.firstName = telegramUserDto.getFirstName();
        this.isBot = telegramUserDto.getBot();
        this.lastName = telegramUserDto.getLastName();
        this.userName = telegramUserDto.getUserName();
        this.languageCode = telegramUserDto.getLanguageCode();
    }

    public TelegramUser(Long id, String firstName, Boolean isBot, String lastName, String userName, String languageCode) {
        this.id = id;
        this.firstName = firstName;
        this.isBot = isBot;
        this.lastName = lastName;
        this.userName = userName;
        this.languageCode = languageCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getBot() {
        return isBot;
    }

    public void setBot(Boolean bot) {
        isBot = bot;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @JsonIgnore // Во избежание бесконечной JSON рекурсии
    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUser telegramUser = (TelegramUser) o;
        return Objects.equals(id, telegramUser.id) &&
                Objects.equals(firstName, telegramUser.firstName) &&
                Objects.equals(isBot, telegramUser.isBot) &&
                Objects.equals(lastName, telegramUser.lastName) &&
                Objects.equals(userName, telegramUser.userName) &&
                Objects.equals(languageCode, telegramUser.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, isBot, lastName, userName, languageCode);
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", isBot=" + isBot +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
