package spring.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс, являющийся композитным первичным ключом для сущности Visits,
 * состоящим из telegramUser, company и timestamp.
 */

@Embeddable
public class VisitPrimaryKey implements Serializable {

    @ManyToOne
    private TelegramUser telegramUser;

    @ManyToOne
    private Company company;

    @Column(name = "date_and_time_of_visit")
    private Timestamp timestamp;

    public VisitPrimaryKey() {
    }

    public VisitPrimaryKey(TelegramUser telegramUser, Company company, Timestamp timestamp) {
        this.telegramUser = telegramUser;
        this.company = company;
        this.timestamp = timestamp;
    }

    public TelegramUser getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitPrimaryKey)) return false;
        VisitPrimaryKey that = (VisitPrimaryKey) o;
        return telegramUser.equals(that.telegramUser) &&
                company.equals(that.company) &&
                timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramUser, company, timestamp);
    }
}
