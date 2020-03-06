package spring.app.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Класс-таблица для сохранения даты и времени посещения telegramUser`ом
 * конкретного заведения (company).
 */

@Entity
@Table(name = "telegram_users_companies_visits")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.telegramUser",
                joinColumns = @JoinColumn(name = "telegram_user_id")),
        @AssociationOverride(name = "primaryKey.company",
                joinColumns = @JoinColumn(name = "company_id")) })
public class TelegramUserCompaniesVisits {

    @EmbeddedId
    private TelegramUserCompaniesVisitsPrimaryKey primaryKey = new TelegramUserCompaniesVisitsPrimaryKey();

    @Column(name = "date_and_time_of_visit")
    private Timestamp timestamp;

    public TelegramUserCompaniesVisits() {
    }

    public TelegramUserCompaniesVisits(TelegramUserCompaniesVisitsPrimaryKey telegramUserCompaniesVisitsPrimaryKey,
                                       Timestamp timestamp) {
        this.primaryKey = telegramUserCompaniesVisitsPrimaryKey;
        this.timestamp = timestamp;
    }

    @Transient
    public TelegramUser getTelegramUser() {
        return getPrimaryKey().getTelegramUser();
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        getPrimaryKey().setTelegramUser(telegramUser);
    }

    @Transient
    public Company getCompany() {
        return getPrimaryKey().getCompany();
    }

    public void setCompany(Company company) {
        getPrimaryKey().setCompany(company);
    }

    public TelegramUserCompaniesVisitsPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(TelegramUserCompaniesVisitsPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
