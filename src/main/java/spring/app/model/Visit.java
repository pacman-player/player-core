package spring.app.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * Класс-таблица для сохранения даты и времени посещения telegramUser`ом
 * конкретного заведения (company). В данной таблице первичным ключом служит
 * класс VisitsPrimaryKey, в котором содержится
 * комбинация из 3-х составляющих: id пользователя Telegram (посетителя),
 * id компании (заведения) и дата со временем (Timestamp).
 */

@Entity
@Table(name = "visits")
//@AssociationOverrides({
//        @AssociationOverride(name = "visitPK.telegramUser",
//                joinColumns = @JoinColumn(name = "telegram_user_id")),
//        @AssociationOverride(name = "visitPK.company",
//                joinColumns = @JoinColumn(name = "company_id")) })
public class Visit {

    // Это поле будет являться первичным ключом в нашей таблице
//    @EmbeddedId
//    private VisitPK visitPK = new VisitPK();

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private Timestamp timestamp;

    @ManyToOne
    private TelegramUser telegramUser;

    @ManyToOne
    private Company company;

    /**
     * Здесь можно создавать поля для добавления новых столбцов в таблицу,
     * например заказанных песен, жанров, исполнителей.
     */

    public Visit() {
//        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

//    public Visit(VisitPK visitPK) {
//        this.visitPK = visitPK;
//    }

    public Visit(TelegramUser telegramUser, Company company) {
//        this.visitPK = new VisitPK(telegramUser, company);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.telegramUser = telegramUser;
        this.company = company;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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
}
