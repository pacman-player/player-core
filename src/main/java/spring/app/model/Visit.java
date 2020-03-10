package spring.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс-таблица для сохранения даты и времени посещения telegramUser`ом
 * конкретного заведения (company). В данной таблице первичным ключом служит
 * класс VisitsPrimaryKey, в котором содержится
 * комбинация из 3-х составляющих: id пользователя Telegram (посетителя),
 * id компании (заведения) и дата со временем (Timestamp).
 */

@Entity
@Table(name = "visits")
@AssociationOverrides({
        @AssociationOverride(name = "visitPrimaryKey.telegramUser",
                joinColumns = @JoinColumn(name = "telegram_user_id")),
        @AssociationOverride(name = "visitPrimaryKey.company",
                joinColumns = @JoinColumn(name = "company_id")) })
public class Visit {

    // Это поле будет являться первичным ключом в нашей таблице
    @EmbeddedId
    private VisitPrimaryKey visitPrimaryKey = new VisitPrimaryKey();

    /**
     * Здесь можно создавать поля для добавления новых столбцов в таблицу,
     * например заказанных песен, жанров, исполнителей.
     */

    public Visit() {
    }

    public Visit(VisitPrimaryKey visitPrimaryKey) {
        this.visitPrimaryKey = visitPrimaryKey;
    }

    public Visit(TelegramUser telegramUser, Company company) {
        this.visitPrimaryKey = new VisitPrimaryKey(
                telegramUser, company, new Timestamp(System.currentTimeMillis())
        );
    }

    public TelegramUser getTelegramUser() {
        return getVisitPrimaryKey().getTelegramUser();
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        getVisitPrimaryKey().setTelegramUser(telegramUser);
    }

    public Company getCompany() {
        return getVisitPrimaryKey().getCompany();
    }

    public void setCompany(Company company) {
        getVisitPrimaryKey().setCompany(company);
    }

    public Timestamp getTimestamp() {
        return getVisitPrimaryKey().getTimestamp();
    }

    public void setTimestamp(Timestamp timestamp) {
        getVisitPrimaryKey().setTimestamp(timestamp);
    }

    public VisitPrimaryKey getVisitPrimaryKey() {
        return visitPrimaryKey;
    }

    public void setVisitPrimaryKey(VisitPrimaryKey primaryKey) {
        this.visitPrimaryKey = primaryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;
        Visit visit = (Visit) o;
        return visitPrimaryKey.equals(visit.visitPrimaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitPrimaryKey);
    }
}
