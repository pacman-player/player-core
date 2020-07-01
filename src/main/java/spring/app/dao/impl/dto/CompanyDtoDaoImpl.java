package spring.app.dao.impl.dto;

import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import spring.app.dao.abstraction.dto.CompanyDtoDao;
import spring.app.dto.CompanyDto;
import spring.app.model.Company;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalTime;
import java.util.*;

@Repository
public class CompanyDtoDaoImpl implements CompanyDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<CompanyDto> companyDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(c.id, c.name, c.startTime, c.closeTime, c.orgType.id, " +
                        "c.orgType.name, c.tariff, c.requestSpamCounter, c.user.id, c.user.login, c.address.country, c.address.city, c.address.street, c.address.house) FROM Company c",
                CompanyDto.class
        )
                .getResultList();

        //добавляю в лист, компании, у которых отсутствуют пользователи
        Query query = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(" +
                        "c.id, c.name, c.startTime, c.closeTime, c.orgType.id, c.orgType.name, c.tariff, " +
                        "c.requestSpamCounter,  " +
                        "c.address.country, c.address.city, c.address.street, c.address.house) FROM Company c where c.user.id is null",
                CompanyDto.class
        );
        companyDtos.addAll(query.getResultList());

        //добавляю в лист, компании, у которых отсутствуют адреса
        Query query1 = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(" +
                        "c.id, c.name, c.startTime, c.closeTime, c.orgType.id, c.orgType.name, c.tariff, " +
                        "c.requestSpamCounter,  " +
                        "c.user.id, c.user.login) FROM Company c where c.address.id is null",
                CompanyDto.class
        );
        companyDtos.addAll(query1.getResultList());

        //добавляю в лист, компании, у которых отсутствуют адреса и пользователи
        Query query2 = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(" +
                        "c.id, c.name, c.startTime, c.closeTime, c.orgType.id, c.orgType.name, c.tariff, " +
                        "c.requestSpamCounter) FROM Company c where c.user.id is null and c.address.id is null",
                CompanyDto.class
        );
        companyDtos.addAll(query2.getResultList());

        return companyDtos;

    }

    @Override
    public List<CompanyDto> getCompaniesWithoutUsers() {
        List<CompanyDto> companyDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(" +
                        "c.id, c.name, c.startTime, c.closeTime, c.orgType.id, c.orgType.name, c.tariff, " +
                        "c.requestSpamCounter,  " +
                        "c.address.country, c.address.city, c.address.street, c.address.house) FROM Company c where c.user.id is null",
                CompanyDto.class
        ).getResultList();
        Query query = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(" +
                        "c.id, c.name, c.startTime, c.closeTime, c.orgType.id, c.orgType.name, c.tariff, " +
                        "c.requestSpamCounter) FROM Company c where c.user.id is null and c.address.id is null",
                CompanyDto.class
        );
        companyDtos.addAll(query.getResultList());

        return companyDtos;
    }

    @Override
    public CompanyDto getById(Long id) {
        CompanyDto companyDto = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(c.id, c.name, c.startTime, c.closeTime, c.orgType.id, " +
                        "c.orgType.name, c.tariff, c.requestSpamCounter, c.user.id, c.user.login, c.address.country, c.address.city, c.address.street, c.address.house) FROM Company c WHERE c.id = :companyId",
                CompanyDto.class
        )
                .setParameter("companyId", id)
                .getSingleResult();

        return companyDto;
    }

    @Override
    public long getTimerById(long companyId) {
        return entityManager.createQuery("SELECT c.requestSpamCounter FROM Company c WHERE c.id = :id", Long.class)
                .setParameter("id", companyId)
                .getSingleResult();
    }
}
