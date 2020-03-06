package spring.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Класс, являющийся составным первичным ключом, состоящим из telegramUser и company
 */

@Embeddable
public class TelegramUserCompaniesVisitsPrimaryKey implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private TelegramUser telegramUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private Company company;

    public TelegramUserCompaniesVisitsPrimaryKey() {
    }

    public TelegramUserCompaniesVisitsPrimaryKey(TelegramUser telegramUser, Company company) {
        this.telegramUser = telegramUser;
        this.company = company;
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
