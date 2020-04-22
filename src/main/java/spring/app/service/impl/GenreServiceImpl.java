package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.model.Genre;
import spring.app.service.abstraction.GenreService;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class GenreServiceImpl extends AbstractServiceImpl<Genre, GenreDao, Long> implements GenreService {

    @Autowired
    public GenreServiceImpl(GenreDao dao) {
        super(dao);
    }

    @Override
    public Genre getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Genre> getByCreatedDateRange(Timestamp dateFrom, Timestamp dateTo) {
        return dao.getByCreatedDateRange(dateFrom, dateTo);
    }

    @Override
    public List<Genre> getAllApprovedGenre() {
        return dao.getAllApproved();
    }
}