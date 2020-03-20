package spring.app.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс-таблица для сохранения факта посещения заведения (Company)
 * нашим пользователем Telegram-бота (TelegramUser).
 * В данной таблице первичным ключом служит класс VisitPK, в
 * котором содержится комбинация из 3-х составляющих:
 * - дата и время посещения (Timestamp),
 * - id пользователя Telegram-бота (TelegramUser),
 * - id заведения (Company).
 */

@Entity
@Table(name = "visits")
public class Visit {

    // Это поле является композитным первичным ключом в нашей таблице
    @EmbeddedId
    private VisitPK visitPK = new VisitPK();

    public Visit() {}

    public Visit(TelegramUser telegramUser, Company company) {
        this.visitPK = new VisitPK(telegramUser, company);
    }

    public VisitPK getVisitPK() {
        return visitPK;
    }

    public void setVisitPK(VisitPK visitPK) {
        this.visitPK = visitPK;
    }

    public Timestamp getTimestamp() {
        return getVisitPK().getTimestamp();
    }

    public void setTimestamp(Timestamp timestamp) {
        getVisitPK().setTimestamp(timestamp);
    }

    public TelegramUser getTelegramUser() {
        return getVisitPK().getTelegramUser();
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        getVisitPK().setTelegramUser(telegramUser);
    }

    public Company getCompany() {
        return getVisitPK().getCompany();
    }

    public void setCompany(Company company) {
        getVisitPK().setCompany(company);
    }

    /**
     * Класс, являющийся композитным первичным ключом у класса Visit
     */
    @Embeddable
    public static class VisitPK implements Serializable {

        private Timestamp timestamp;

        /**
         * Hibernate-аннотация @OnDelete позволяет при удалении
         * TelegramUser или Company, являющихся "детьми" в связи с
         * Visit ("родитель"), удалять одновременно все записи
         * с ними в таблице visits.
         */
        @ManyToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        private TelegramUser telegramUser;

        @ManyToOne
        @OnDelete(action = OnDeleteAction.CASCADE)
        private Company company;

        public VisitPK() {}

        public VisitPK(TelegramUser telegramUser, Company company) {
            this.timestamp = new Timestamp(System.currentTimeMillis());
            this.telegramUser = telegramUser;
            this.company = company;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof VisitPK)) return false;
            VisitPK visitPK = (VisitPK) o;
            return timestamp.equals(visitPK.timestamp) &&
                    telegramUser.equals(visitPK.telegramUser) &&
                    company.equals(visitPK.company);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timestamp, telegramUser, company);
        }
    }
}
