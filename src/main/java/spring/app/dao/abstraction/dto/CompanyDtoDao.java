package spring.app.dao.abstraction.dto;

import spring.app.dto.CompanyDto;

import java.util.List;

public interface CompanyDtoDao {

    List<CompanyDto> getAllCompanies();

    CompanyDto getById(Long id);

}
