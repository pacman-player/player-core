package spring.app.dao.impl;

import com.vk.api.sdk.objects.ads.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.TelegramUserDao;
import spring.app.model.TelegramUser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class TelegramUserDaoImpl extends AbstractDao<Long, TelegramUser> implements TelegramUserDao {

    public TelegramUserDaoImpl() {
        super(TelegramUser.class);
    }

    @Override
    public boolean isTelegramUserExists(Long telegramUserId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<TelegramUser> root = query.from(TelegramUser.class);
        query.select(cb.count(root));
        Long count = entityManager.createQuery(query).getSingleResult();
        return count > 0;
    }
}
