package spring.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.GenreDao;
import spring.app.dao.abstraction.dto.GenreDtoDao;
import spring.app.dto.GenreDto;
import spring.app.model.Author;
import spring.app.model.Genre;
import spring.app.model.Song;
import spring.app.model.SongCompilation;
import spring.app.service.abstraction.AuthorService;
import spring.app.service.abstraction.GenreService;
import spring.app.service.abstraction.SongCompilationService;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreServiceImpl extends AbstractServiceImpl<Long, Genre, GenreDao> implements GenreService {

    private final GenreDtoDao genreDtoDao;
    private final SongCompilationService songCompilationService;
    private final AuthorService authorService;

    @Autowired
    public GenreServiceImpl(GenreDao dao, GenreDtoDao genreDtoDao, SongCompilationService songCompilationService, AuthorService authorService) {
        super(dao);
        this.genreDtoDao = genreDtoDao;
        this.songCompilationService = songCompilationService;
        this.authorService = authorService;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        songCompilationService.setDefaultGenre(id);
        if (BigInteger.ONE.equals(dao.countOfGenresInOrgType(id))){
            dao.deleteReferenceFromOrgTypeByGenre(id);
        }
        else {
            dao.setDefaultGenreToOrgType(id, getDefaultGenreId());
        }
        if (BigInteger.ONE.equals(dao.countOfGenresInAuthor(id))) {
            dao.deleteReferenceFromAuthorByGenre(id);
        } else {
            dao.setDefaultGenreToAuthor(id, getDefaultGenreId());
        }
        dao.deleteReferenceFromCompanyByGenre(id);
        dao.deleteById(id);
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
    public List<GenreDto> getAllGenreDto() {
        return genreDtoDao.getAll();
    }

    @Override
    public List<String> getGenreNames(String keywords) {
        return genreDtoDao.getGenresByKeywords(keywords);
    }

    @Override
    public List<GenreDto> getAllApprovedGenreDto() {
        return genreDtoDao.getAllApprovedDto();
    }

    @Override
    public long getDefaultGenreId(){
        return dao.getDefaultGenreId();
    }

    @Override
    @Transactional
    public void saveBatch(List<Genre> genreList) {
        dao.saveBatch(genreList);
    }

    @Override
    public boolean isExistByName(String name) {
        return genreDtoDao.isExistByName(name);
    }

    @Override
    @Transactional
    public void setDefaultGenre(Long id){
        dao.setDefaultGenre(id);
    }

    @Override
    @Transactional
    public void setDefaultGenreToOrgType(Long deleteGenreId, Long defaultGenreId){
        dao.setDefaultGenreToOrgType(deleteGenreId, defaultGenreId);
    }
}