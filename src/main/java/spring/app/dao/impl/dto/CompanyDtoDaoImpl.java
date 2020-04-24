package spring.app.dao.impl.dto;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.dto.CompanyDtoDao;
import spring.app.dto.CompanyDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CompanyDtoDaoImpl implements CompanyDtoDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<CompanyDto> companyDtos = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(c.id, c.name, c.startTime, c.closeTime, c.orgType.id, " +
                        "c.orgType.name, c.tariff, c.user.id, c.user.login) FROM Company c",
                CompanyDto.class
        )
                .getResultList();

        return companyDtos;
    }

    @Override
    public CompanyDto getById(Long id) {
        CompanyDto companyDto = entityManager.createQuery(
                "SELECT new spring.app.dto.CompanyDto(c.id, c.name, c.startTime, c.closeTime, c.orgType.id, " +
                        "c.orgType.name, c.tariff, c.user.id, c.user.login) FROM Company c WHERE c.id = :companyId",
                CompanyDto.class
        )
                .setParameter("companyId", id)
                .getSingleResult();

        return companyDto;
    }

}
